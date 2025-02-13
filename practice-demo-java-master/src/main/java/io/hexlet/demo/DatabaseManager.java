package io.hexlet.demo;

import io.hexlet.demo.dao.BaseDao;
import lombok.Getter;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DatabaseManager {
    @Getter
    public static Connection connection;
    private static final String url = "jdbc:postgresql://localhost:5432/family-budget";
    private static final String username = "family-budget-username";
    private static final String password = "family-budget-password";

    private static final Map<String, String> tableNameAndImportFileName = new LinkedHashMap<>();

    public DatabaseManager() {
        tableNameAndImportFileName.put("products_and_services", "Product_import.csv");
        tableNameAndImportFileName.put("members", "Family_members_import.csv");
        tableNameAndImportFileName.put("jobs", "Family_members_job_import.csv");
        tableNameAndImportFileName.put("purchases", "Expence_product_import.csv");
    }

    public DatabaseManager openConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        BaseDao.connection = connection;
        return this;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        createTables();
        importOperation();
    }

    private static void createTables() {
        try (var ps = connection.prepareStatement(loadSqlInit())) {
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String loadSqlInit() {
        try (var inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream("init.sql")) {
            try (var reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void importOperation() {
        try {
            CopyManager copyManager = new CopyManager((BaseConnection) connection);
            tableNameAndImportFileName.forEach((tableName, fileName) -> {
                if (isTableEmpty(tableName)) {
                    try (var inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream(fileName)) {
                        String sql = String.format(
                                "COPY %s %s FROM STDIN WITH DELIMITER ';' HEADER;",
                                tableName,
                                getColumns(tableName)
                        );
                        copyManager.copyIn(sql, inputStream);
                    } catch (IOException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isTableEmpty(String tableName) {
        String sql = String.format("SELECT COUNT(*) FROM %s", tableName);
        try (var ps = connection.prepareStatement(sql)) {
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("COUNT") == 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private static String getColumns(String tableName) {
        List<String> columnNames = new ArrayList<>();
        try {
            var columns = connection.getMetaData().getColumns(
                    null,
                    null,
                    tableName,
                    null
            );
            while (columns.next()) {
                columnNames.add(columns.getString("COLUMN_NAME"));
            }
            columnNames.removeIf(column -> column.equals("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return columnNames.stream().collect(Collectors.joining(",", "(", ")"));
    }
}
