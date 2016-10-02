package com.mas.entity;

public class Catalog {
	
	private long catalogId;
	private String folderName;
	private int purposeId;                                 //album/singer/playlist
	private int parentFolder;
	private String pathToImage;
	private String purpose;
	
	public Catalog(){
	}
	
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
	public int getPurposeId() {
		return purposeId;
	}
	public void setPurposeId(int purposeId) {
		this.purposeId = purposeId;
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

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	
}
