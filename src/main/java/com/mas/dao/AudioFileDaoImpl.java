package com.mas.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.stereotype.Repository;

import com.mas.entity.AudioMetadata;

@Repository
public class AudioFileDaoImpl implements AudioFileDao {

	
	@Override
	public byte[] readFileFromHardDrive(String filePath) {
		File file = new File(filePath);
		long length = file.length();
		byte[] byteArr = new byte[(int)length];
		int offset = 0;
		int numRead = 0;
		
		try(InputStream is = new BufferedInputStream(new FileInputStream(file))){
			while(offset < file.length() && (numRead = is.read(byteArr, offset, ((int)length-offset))) >= 0){
				
				offset += numRead;
			}
			
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		return byteArr;
	}

	@Override
	public void saveMetadateToDatabase(AudioMetadata audioFile) {
		// TODO Auto-generated method stub	
	}

	@Override
	public AudioFile readTagContainer(String filePath){
		File f = new File(filePath);
		try {
			AudioFile af = AudioFileIO.read(f);
			return af;
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		return null;
	}}
