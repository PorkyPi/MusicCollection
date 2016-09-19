package com.mas.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionToDb {
	
	public static void main(String[] args) {
		
		
		Connection con = null;
		Statement stmt = null;
		ResultSet res = null;
		
		
		try {
			
			@SuppressWarnings("unused")
			Driver driver = (Driver) Class.forName("org.sqlite.JDBC").newInstance();
			
			String url = "jdbc:sqlite:C:\\Users\\админ\\workspace\\MyMusicCollection\\db\\sqlite\\MusicDB.db";
			con = DriverManager.getConnection(url);
			
			String sql = "SELECT folder_name, path_to_image FROM folder";
			stmt = con.createStatement();
			
			res = stmt.executeQuery(sql);
			
			while (res.next()){
				System.out.println(res.getString("folder_name") + " - " + res.getString("path_to_image"));
			}
			
			
		} catch (SQLException e ){
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				if (res!=null) res.close();
				if (stmt!=null) stmt.close();
				if (con!=null) con.close();
			} catch (Exception ex){
				
			}
		}
		
	}
}
