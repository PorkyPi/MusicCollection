package com.mas.entity;

import javax.inject.Named;

@Named
public class Catalog {
	private int catalogId;
	private String folderName;
	private int purpose;
	private int parentFolder;
	private String pathToImage;
	
	public int getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public int getPurpose() {
		return purpose;
	}
	public void setPurpose(int purpose) {
		this.purpose = purpose;
	}
	public int getParentFolder() {
		return parentFolder;
	}
	public void setParentFolder(int parentFolder) {
		this.parentFolder = parentFolder;
	}
	public String getPathToImage() {
		return pathToImage;
	}
	public void setPathToImage(String pathToImage) {
		this.pathToImage = pathToImage;
	}
}
