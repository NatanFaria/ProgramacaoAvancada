package com.unicesumar;

import com.unicesumar.entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaleRepository {
    private final Connection connection;

    public SaleRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Sale sale) {
        String insertSale = "INSERT INTO sales (id, user_id, payment_method) VALUES (?, ?, ?)";
        String insertProduct = "INSERT INTO sale_products (sale_id, product_id) VALUES (?, ?)";
        try {
            PreparedStatement stmtSale = connection.prepareStatement(insertSale);
            stmtSale.setString(1, sale.getId().toString());
            stmtSale.setString(2, sale.getUser().getUuid().toString());
            stmtSale.setString(3, sale.getPaymentType().name());
            stmtSale.executeUpdate();

            for (Product p : sale.getProducts()) {
                PreparedStatement stmtItem = connection.prepareStatement(insertProduct);
                stmtItem.setString(1, sale.getId().toString());
                stmtItem.setString(2, p.getUuid().toString());
                stmtItem.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar venda: " + e.getMessage());
        }
    }
}
