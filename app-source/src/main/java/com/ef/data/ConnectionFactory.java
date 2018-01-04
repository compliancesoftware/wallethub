package com.ef.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/wallethub", "root", "admin");
        } catch (SQLException e) {
            System.out.println("Connection failure:\n");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
			System.out.println("Database driver not found.");
			return null;
		}
	}
}
