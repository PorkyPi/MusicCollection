package com.mas.services;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.mas.entity.AudioFile;

public class DemuxerMP3ServicsImplTest {
	public static void main(String[] args){
		@SuppressWarnings("resource")
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("beans.xml");
		ctx.refresh();
		
		DemuxerMP3ServiceImpl dmsi = ctx.getBean(DemuxerMP3ServiceImpl.class);
		
		dmsi.readAllMetadataFromFile("D:\\Temp\\01_imagine_dragons_battle_cry_myzuka.ru.mp3");
		
		AudioFile af = dmsi.getAudioFile();
		System.out.println();
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
		System.out.println("Genre: " + af.getContentType());
		System.out.println("Comments: " + af.getComments());
		System.out.println("Text: " + af.getText());
		System.out.println("Publisher: " + af.getPublisher());
		System.out.println("MPEG version: " + af.getMpegVersionAsString());
		System.out.println("Layer description: " + af.getLayerDescriptionAsString());
		System.out.println("Bit rate: " + af.getBitRate() + "kbps");
		System.out.println("Frequency: " + af.getFrequencyIndex() + "Hz");
		System.out.println("Channel mode: " + af.getChannelModeAsString());
		System.out.println("Duration: " + af.getDurationToString());
	}
}
