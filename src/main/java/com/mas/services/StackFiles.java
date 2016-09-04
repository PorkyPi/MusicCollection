package com.mas.services;

import java.util.LinkedList;

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
