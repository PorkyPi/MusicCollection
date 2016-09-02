package com.mas.services;

import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.mas.entity.AudioMetadata;

public class Mp4taggerTest {
	public static void main(String[] args) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
		@SuppressWarnings("resource")
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("beans.xml");
		ctx.refresh();
		Mp4tagger tagger = ctx.getBean(Mp4tagger.class);
		
		tagger.readAllMetadataFromFile("D:\\Temp\\01 Moon.m4a");
		AudioMetadata am = tagger.getAm();
		
		
		System.out.println("File path: " + am.getFilePath());
		System.out.println("Track name: " + am.getTrackName());
		System.out.println("Contributing artist: " + am.getContributingArtist());
		System.out.println("Album: " + am.getAlbum());
		System.out.println("Album artist: " + am.getAlbumArtist());
		System.out.println("Composer: " + am.getComposer());
		System.out.println("Year: " + am.getYear());
		System.out.println("Track number: " + am.getTrackNumberAndPositionInSet());
		System.out.println("Disck no: " + am.getPartOfSet());
		System.out.println("Rating: " + am.getRating());
		System.out.println("Lyrics: " + am.getLyrics());
		System.out.println("Play count: " + am.getPlayCount());
		System.out.println("File extension: " + am.getFileExtension());
		System.out.println("Duration: " + am.getDuration());
		System.out.println("File size: " + am.getFileSize());
		System.out.println("Bitrate: " + am.getBitRate() + "kbps");
		System.out.println("Frequency: " + am.getFrequencyIndex());
		System.out.println("ID3 version: " + am.getVersionId3Tag());
		System.out.println("File changed: " + am.getDateСhange());
		System.out.println("Layer: " + am.getLayerDescription());
		System.out.println("Comments: " + am.getComments());
		System.out.println("Genre: " + am.getContentType());
		System.out.println("Text: " + am.getText());
		System.out.println("Publisher: " + am.getPublisher());
		System.out.println("MPEG version: " + am.getMpegVersion());
		System.out.println("Channel mode: " + am.getChannelMode());
		}
	}

