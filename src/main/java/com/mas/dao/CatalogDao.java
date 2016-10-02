package com.mas.dao;

import com.mas.entity.Catalog;

public interface CatalogDao {
	public Catalog getCatalogById(int id);
	public void saveCatalog(Catalog catalog);
}
