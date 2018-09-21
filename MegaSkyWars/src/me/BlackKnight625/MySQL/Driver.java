package me.BlackKnight625.MySQL;

import java.sql.*;

public class Driver {
	public static void main(String args[]) {
		try {
			@SuppressWarnings("unused")
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "BlackKnight625", "skywars625");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
