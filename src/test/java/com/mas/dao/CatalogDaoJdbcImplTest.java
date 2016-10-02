package com.mas.dao;

import com.mas.entity.Catalog;

public class CatalogDaoJdbcImplTest {
	
	public static void main(String[] args){
		CatalogDao catDao = new CatalogDaoJdbcImpl();
		Catalog catalog = catDao.getCatalogById(1);
		System.out.println(catalog.getFolderName() + " - " + catalog.getPurpose());
		catalog = catDao.getCatalogById(2);
		
	}
}
