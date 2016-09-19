package com.mas.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.mas.services.FlacTagger;

public class AudioFileDaoImplTest {
	byte[] testArr;
	
	@Test
	public void test() {
		String str = "" + (char)testArr[0] + (char)testArr[1] + (char)testArr[2];
		assertEquals(str, "ID3");	
	}
	/*
	@Before
	private void testInsertAudioMetadata(){
		ApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/app-context.xml");
		AudioFileDaoImpl audioFileDaoImpl = 
			    (AudioFileDaoImpl) context.getBean("audioFileDao", AudioFileDao.class);
		
		AudioMetadata am = new AudioMetadata();
		am.setTrackName("test");
		am.setAlbum("test");
		audioFileDaoImpl.insertAudioMetadata(am);
	}*/
	
	@SuppressWarnings("resource")
	@Before
	public void setUpBean(){
		@SuppressWarnings("resource")
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("beans.xml");
		ctx.refresh();
		AudioFileDaoImpl afd = ctx.getBean(AudioFileDaoImpl.class);
		byte[] byteArr = afd.readFileFromHardDrive("D:\\Temp\\0_feder_goodbye_feat_lyse_myzuka.org.mp3");
		this.testArr = byteArr;
	}
}
