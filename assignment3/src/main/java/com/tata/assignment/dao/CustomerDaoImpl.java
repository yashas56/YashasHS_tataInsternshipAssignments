package com.tata.assignment.dao;

import com.tata.assignment.entities.Product;
import com.tata.assignment.helpers.PostgresHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class CustomerDaoImpl implements CustomerDao {

    private Connection conn;
    private ResourceBundle resourceBundle;

    public CustomerDaoImpl(){
        conn = PostgresHelper.getConnection();
        if(conn != null)
            System.out.print("Connection successful");
        else
            System.out.print("Connection Failed");
        resourceBundle = ResourceBundle.getBundle("db");

    }
    @Override
    public void addToCart(Product product) throws SQLException {
        Random random = new Random();
        String query = resourceBundle.getString("addCart");
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1,product.getProductId());
        preparedStatement.setString(2,product.getProductName());
        preparedStatement.setInt(3,product.getPrice());
        preparedStatement.setInt(4,product.getQuantity());
        preparedStatement.setInt(5,random.nextInt(1000));

        preparedStatement.executeUpdate();

        preparedStatement.close();
        conn.close();
    }

    @Override
    public boolean verifyLogin(int userid, String paswd) throws SQLException {
        conn = PostgresHelper.getConnection();
        String verifyQuery = resourceBundle.getString("login");
        PreparedStatement preparedStatement = conn.prepareStatement(verifyQuery);
        preparedStatement.setInt(1,userid);
        ResultSet rs = preparedStatement.executeQuery();

        if(rs.next()){
            String pass = rs.getString(1);
            conn.close();
            return paswd.equals(pass);
        }
        conn.close();
        return false;
    }


}
