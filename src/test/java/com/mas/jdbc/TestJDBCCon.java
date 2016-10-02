package com.mas.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mas.jdbcDrivers.DBUtils;
import com.mas.jdbcDrivers.SprObject;


public class TestJDBCCon {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			
			Scanner scanner = new Scanner(new FileInputStream("sql.txt"), "UTF-8");
			
			String dbPath = scanner.nextLine();
			
			System.out.println("DataBase: " + dbPath);
			
			if (!new File(dbPath).exists()) {
				System.out.println("File database not found");
				return;
			}
			
			DBUtils.openConnection(dbPath);
			
			StringBuilder builder = new StringBuilder();
			
			try {
				while (scanner.hasNextLine()) {
					builder.append(scanner.nextLine());
				}
			} finally {
				scanner.close();
			}
			
			String[] sql = builder.toString().split(";");
			
			StringBuilder resultBuilder = new StringBuilder();
			
			for (String sqlStr : sql) {
				resultBuilder.append("Запрос: " + sqlStr + "\n");
				resultBuilder.append("Результат: " + "\n");
				
				ArrayList<SprObject> list = DBUtils.getResultList(sqlStr);
				
				for (SprObject obj : list) {
					resultBuilder.append(obj.getId() + ", " + obj.getFolderName() + ", " + obj.getPathToImage() + "\n");
				}
				resultBuilder.append("\n");
			}
			
			System.out.println(resultBuilder.toString());
			
			DBUtils.closeConnection();
		} catch (IOException ex) {
			Logger.getLogger(TestJDBCCon.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}
