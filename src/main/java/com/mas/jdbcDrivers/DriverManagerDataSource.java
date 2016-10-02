package com.mas.jdbcDrivers;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DriverManagerDataSource{
	
	private static String driver;
	private static String url;
	private static Connection con;
	
	public DriverManagerDataSource(String driver, String url){
		DriverManagerDataSource.driver = driver;
		DriverManagerDataSource.url = url;
	}
	
	public static void openConnection() {
		try {
			
			@SuppressWarnings("unused")
			Driver driver = (Driver) Class.forName("org.sqlite.JDBC").newInstance();
			
			if(true) { 
				con = DriverManager.getConnection("jdbc:sqlite:C:/Users/админ/workspace/MyMusicCollection/db/sqlite/MusicDB.db");
			}
			} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
				Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void closeConnection() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static String getDriver() {
		return driver;
	}

	public static void setDriver(String driver) {
		DriverManagerDataSource.driver = driver;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		DriverManagerDataSource.url = url;
	}

	public static Connection getCon() {
		return con;
	}

	public static void setCon(Connection con) {
		DriverManagerDataSource.con = con;
	}
	
}
