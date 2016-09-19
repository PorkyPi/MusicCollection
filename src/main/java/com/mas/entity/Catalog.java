package com.mas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CATALOGS")
public class Catalog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CATALOG_ID")
	private long catalogId;
	
	@Column(name="FOLDER_NAME")
	private String folderName;
	
	@Column(name="PURPOSE")
	private int purpose;                                 //album/singer/playlist
	
	@Column(name="PARENT_FOLDER")
	private int parentFolder;
	
	@Column(name="PATH_TO_IMAGE")
	private String pathToImage;
	
	
	public long getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(long catalogId) {
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
