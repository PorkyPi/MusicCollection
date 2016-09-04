package com.mas.services;

import java.io.File;

public class MediaLibraryServiceImpl implements MediaLibraryService  {
	private StackFiles sk; 
	
	@Override
	public void bypassCatalogTree(String nodePath){
		sk = new StackFiles(nodePath);
		File f = new File(nodePath);
		
		while(sk.getElement() != null){
			if(f.isDirectory()){
				//System.out.println("Directory: " + f.getPath());
				for(File file: f.listFiles()){
					sk.push(file.getPath());
				}
				f = new File(sk.pop());
			} else {
				System.out.println("File: " + f.getPath());
				f = new File(sk.pop());
			}
		}
	}
}
