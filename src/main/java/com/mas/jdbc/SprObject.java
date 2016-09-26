package com.mas.jdbc;

public class SprObject {
	
	private int id;
	private String folderName;
	private String pathToImage;
	
	public SprObject(){
	}
	
	public SprObject(int id, String folderName, String pathToImage) {
		this.id = id;
		this.folderName = folderName;
		this.pathToImage = pathToImage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getPathToImage() {
		return pathToImage;
	}

	public void setPathToImage(String pathToImage) {
		this.pathToImage = pathToImage;
	}
	
	
}
