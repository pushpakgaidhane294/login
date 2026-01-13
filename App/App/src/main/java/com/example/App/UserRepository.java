package com.example.App;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UserRepository {

    private static final String URL =
            "jdbc:mysql://localhost:3306/formdb";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";

    public static void saveUser(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "INSERT INTO users(name) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);

            ps.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
