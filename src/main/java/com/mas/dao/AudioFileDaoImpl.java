package com.mas.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.mas.entity.AudioFile;

@Service
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
	public void writeFileToHardDrive(AudioFile audioFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveMetadateToDatabase(AudioFile af) {
		// TODO Auto-generated method stub
		
	}}
