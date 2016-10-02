package com.mas.dao;

import static org.junit.Assert.*;
import org.junit.Test;

import com.mas.entity.Catalog;

public class CatalogDaoJdbcTest {

	@Test
	public void testGetCatalogById1() {
		CatalogDao catDao = new CatalogDaoJdbcImpl();
		Catalog catalog = catDao.getCatalogById(1);
		assertTrue(catalog.getFolderName().equals("Music"));
	}
	
	@Test
	public void testGetCatalogById2() {
		CatalogDao catDao = new CatalogDaoJdbcImpl();
		Catalog catalog = catDao.getCatalogById(2);
		assertTrue(catalog.getFolderName().equals("Feder"));
	}
	
	@Test
	public void testExistSaveCatalog(){
		CatalogDao catDao = new CatalogDaoJdbcImpl();
		Catalog catalog = catDao.getCatalogById(3);
		catalog.setFolderName("Muse");
		catDao.saveCatalog(catalog);
		Catalog catalog1 = catDao.getCatalogById(3);
		assertTrue(catalog1.getFolderName().equals("Muse"));
	}
	
	@Test
	public void testNewSaveCatalog(){
		CatalogDao catDao = new CatalogDaoJdbcImpl();
		Catalog catalog = new Catalog();
		catalog.setFolderName("Sia");
		catalog.setParentFolder(1);
		catalog.setPurposeId(2);
		catalog.setPathToImage("test");
		catDao.saveCatalog(catalog);
	}
}
