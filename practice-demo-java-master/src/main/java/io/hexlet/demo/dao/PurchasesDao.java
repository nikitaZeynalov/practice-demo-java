package io.hexlet.demo.dao;

import io.hexlet.demo.model.Member;
import io.hexlet.demo.model.Purchase;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchasesDao extends BaseDao {
    public static List<Purchase> getAllBetween(Member member, Date from, Date to) {
        String sql = "SELECT * FROM purchases WHERE full_name = ? AND purchase_at BETWEEN ? AND ?";
        List<Purchase> purchases = new ArrayList<>();
        try (var ps = connection.prepareStatement(sql)) {
            ps.setString(1, member.getFullName());
            ps.setDate(2, from);
            ps.setDate(3, to);
            var rs = ps.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Date purchaseAt = rs.getDate("purchase_at");
                String productOrServiceName = rs.getString("product_or_service_name");
                Integer quantity = rs.getInt("quantity");
                Purchase purchase = new Purchase(id, purchaseAt, member, productOrServiceName, quantity);
                purchases.add(purchase);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return purchases;
    }
}
