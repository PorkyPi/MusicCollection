package com.mas.services;

import java.io.File;
import java.util.LinkedList;

public class MediaLibraryServiceImpl implements MediaLibraryService  {
	private StackFiles sk;
	
	@Override
	public void bypassCatalogTree(String nodePath){
		sk = new StackFiles(nodePath);
		File f = new File(nodePath);
		
		while(sk.getElement() != null){
			if(f.isDirectory()){
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
	
	public class StackFiles {
		private LinkedList<String> list;
		
		public StackFiles(String startPath){
			list = new LinkedList<String>();
			list.addLast(startPath);
		}
		
		public void push(String path){
			this.list.addLast(path);
		}
		
		public String pop(){
			if(this.list.isEmpty()){
				return null;
			}
			String f = this.list.getLast();
			this.list.removeLast();
			return f;
		}
		
		public String getElement(){
			if(this.list.isEmpty()){
				return null;
			}
			return list.getLast();
		}
	}
}
