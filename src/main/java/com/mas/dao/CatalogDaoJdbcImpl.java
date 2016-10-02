package com.mas.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mas.entity.Catalog;
import com.mas.jdbcDrivers.DBUtils;
import com.mas.jdbcDrivers.DriverManagerDataSource;

public class CatalogDaoJdbcImpl implements CatalogDao{
	
	Statement statement = null;
	ResultSet resultSet = null;
	
	@Override
	public Catalog getCatalogById(int id) {
		DriverManagerDataSource.openConnection();
		
		String sql = "SELECT f.id, f.folder_name, f.purpose_id, f.parent_folder, f.path_to_image, c.purpose "
				+ "FROM folder f " +
				"LEFT OUTER JOIN catalog_folder c on c.id = f.purpose_id " +
				"WHERE f.id = " + String.valueOf(id);
		
		Catalog catalog = new Catalog();
		
		try{
			statement = DriverManagerDataSource.getCon().createStatement();
			
			resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()){
				catalog.setCatalogId(resultSet.getInt("id"));
				catalog.setFolderName(resultSet.getString("folder_name"));
				catalog.setPurposeId(resultSet.getInt("purpose_id"));
				catalog.setParentFolder(resultSet.getInt("parent_folder"));
				catalog.setPathToImage(resultSet.getString("path_to_image"));
				catalog.setPurpose(resultSet.getString("purpose"));
			}
		} catch (SQLException ex) {
			Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		
		
		DriverManagerDataSource.closeConnection();
		return catalog;
	}

	@Override
	public void saveCatalog(Catalog catalog) {
		DriverManagerDataSource.openConnection();
		
		if(catalog.getCatalogId() == 0){
			String sql = "INSERT INTO folder " + 
							"(folder_name, purpose_id, parent_folder, path_to_image) " +
							"Values (" +
								"'" + catalog.getFolderName() + "', " +
								catalog.getPurposeId() + ", " +
								catalog.getParentFolder() + ", " +
								"'" + catalog.getPathToImage() + "')";
			
			try{
				statement = DriverManagerDataSource.getCon().createStatement();
				statement.execute(sql);
				
			} catch (SQLException ex) {
				Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
			} catch (Exception ex) {
				Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			String sql = "UPDATE folder SET " + 
					"folder_name = '" + catalog.getFolderName() + "', " +
					"purpose_id = " + catalog.getPurposeId() + ", " +
					"parent_folder = " + catalog.getParentFolder() + ", " +
					"path_to_image = '" + catalog.getPathToImage() + "' " +
					"WHERE id = " + catalog.getCatalogId();
			try{
				statement = DriverManagerDataSource.getCon().createStatement();
				statement.execute(sql);
				
			} catch (SQLException ex) {
				Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
			} catch (Exception ex) {
				Logger.getLogger(DBUtils.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		DriverManagerDataSource.closeConnection();
	}

}
