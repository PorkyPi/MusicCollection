package com.mas.services;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.mas.entity.AudioFile;

public class DemuxerMP3ServicsImplTest {
	public static void main(String[] args){
		@SuppressWarnings("resource")
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("beans.xml");
		ctx.refresh();
		
		DemuxerMP3ServicesImpl dmsi = ctx.getBean(DemuxerMP3ServicesImpl.class);
		
		dmsi.readAllMetadataFromFile("D:\\Temp\\0_feder_goodbye_feat_lyse_myzuka.org.mp3");
		AudioFile af = dmsi.getAudioFile();
		System.out.println("File Path: " + af.getFilePath());
		System.out.println("Track Name: " + af.getTrackName());
		System.out.println("Contributing Artist: " + af.getContributingArtist());
		System.out.println("Album: " + af.getAlbum());
		System.out.println("Album artist:" + af.getAlbumArtist());
		System.out.println("Composer: " + af.getComposer());
		System.out.println("Year: " + af.getYear());
		System.out.println("Track and position set: " + af.getTrackNumberAndPositionInSet());
		System.out.println("Part of set: " + af.getPartOfSet());
		System.out.println("Rating: " + af.getRating());
		System.out.println("Lyrics: " + af.getLyrics());
		System.out.println("File extension: " + af.getFileExtension());
		System.out.println("ID3 version: " + af.getVersionId3Tag());
	}
}
