package com.mas.etity;

import java.io.File;
import java.io.IOException;

import com.mas.entity.AudioFile;

public class AudioFileTest {
	public static void main(String[] args) throws IOException{
		File file = new File("D:\\Temp", "01_feder_goodbye_feat_lyse_myzuka.org.mp3");
		System.out.println(file.getName());
		AudioFile audioFile = new AudioFile(file);
		System.out.println(audioFile.getFileFormat());
		System.out.println(Integer.valueOf('"'));
		audioFile.getID3v2Tags();
	}
}
