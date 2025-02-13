package io.hexlet.demo.dao;

import io.hexlet.demo.model.ProductOrService;

import java.sql.SQLException;

public class ProductsAndServicesDao extends BaseDao {
    public static ProductOrService getByName(String productOrServiceName) {
        String sql = "SELECT * FROM products_and_services WHERE name = ?";
        try (var ps = connection.prepareStatement(sql)) {
            ps.setString(1, productOrServiceName);
            var rs = ps.executeQuery();
            ProductOrService productOrService = null;
            if (rs.next()) {
                String name = rs.getString("name");
                String category = rs.getString("category");
                Integer price = rs.getInt("price");
                productOrService = new ProductOrService(name, category, price);
            }
            return productOrService;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
