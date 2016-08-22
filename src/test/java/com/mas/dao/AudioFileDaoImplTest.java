package com.mas.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AudioFileDaoImplTest {
	
	@Test
	public void test() {
		byte[] testArr = this.setUpBean();
		String str = "" + (char)testArr[0] + (char)testArr[1] + (char)testArr[2];
		
		assertEquals(str, "ID3");
		
	}
	
	@Test
	public void test1(){
		byte[] testArr = this.setUpBean();
		
		assertEquals(testArr.length, 11627828);
	}
	
	private byte[] setUpBean(){
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("beans.xml");
		ctx.refresh();
		AudioFileDao afd = ctx.getBean(AudioFileDaoImpl.class);
		byte[] byteArr = afd.readFileFromHardDrive("D:\\Temp\\01_feder_goodbye_feat_lyse_myzuka.org.mp3");
		return byteArr;
	}
}
