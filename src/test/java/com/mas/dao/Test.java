package com.mas.dao;

import org.springframework.context.support.GenericXmlApplicationContext;

public class Test {
	public static void main(String[] args){
		@SuppressWarnings("resource")
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("beans.xml");
		ctx.refresh();
		AudioFileDaoImpl afd = (AudioFileDaoImpl) ctx.getBean("audioFileDaoImpl");
		byte[] byteArr = afd.readFileFromHardDrive("D:\\Temp\\0_feder_goodbye_feat_lyse_myzuka.org.mp3");
		System.out.println(byteArr.toString());
	}
}
