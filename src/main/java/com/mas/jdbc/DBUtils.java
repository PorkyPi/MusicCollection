package com.mas.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBUtils {
	
	private static Connection con;
	
	public static void openConnection(String path) {
		try {
			
			@SuppressWarnings("unused")
			Driver driver = (Driver) Class.forName("org.sqlite.JDBC").newInstance();
			
			String url = "jdbc:sqlite:" + path;
			
			if(con == null) {
				con = DriverManager.getConnection(url);
			}
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public static ArrayList<SprObject> getResultList(String sql){
		
		ArrayList<SprObject> list = new ArrayList<>();
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				
				SprObject obj = new SprObject();
				obj.setId(rs.getInt("id"));
				obj.setFolderName(rs.getString("folder_name"));
				obj.setPathToImage(rs.getString("path_to_image"));
				
				list.add(obj);
			}
		} catch (SQLException ex) {
			Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		try {
			rs.close();
			statement.close();
		} catch (SQLException ex) {
			Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return list;
	}
	
	public static void closeConnection(){
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
