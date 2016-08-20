package com.mas.entity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioFile {
	private String fileFormat;
	private File file;
	
	public AudioFile(File file) throws IOException{
		this.file = file;
		this.fileFormat = this.getFileExtension(file);
		
		if(this.getFileFormat().equals("mp3")){
			
		}
	}
	
	/**
	 * method parsing all mp3 file for ID3 Tags
	 * @throws IOException
	 */
	public void getID3v2Tags() throws IOException{
		byte[] header = new byte[10];
		String version = new String();
		int size;
		
		
		try(InputStream is = new BufferedInputStream(new FileInputStream(this.file))){
			is.read(header, 0, 10);
			
			if(header[0] == 'I' && header[1] == 'D' && header[2] == '3'){  //only ID3v2 tags
				for(int i = 3; i < 5; i++){                                //read version ID3v2 tags
					version += header[i];
				}
				
				if(header[5] == 0){                                        //is flag empty
					byte[] s = new byte[4];
					for(int i = 6; i < header.length; i++){
						s[i-6] = header[i];
					}
					
					size = this.byteArraySizeToInt(s);
					byte[] tags = new byte[size+10];
					
					System.out.println(size);
					
					
				}
				
			}
			
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		/*
		System.out.print("Signature: ");
		for(int i = 0; i < 3; i++){
			System.out.print((char) header[i]);
		}
		System.out.println();
		
		System.out.print("Version: ");
		for(int i = 3; i < 5; i++){
			System.out.print(header[i]); 
		}
		System.out.println();
		
		System.out.print("Flags: ");
		System.out.println((char) header[5]);
		
		System.out.print("Size: ");
		for(int i = 6; i < header.length; i++){
			System.out.print(header[i] + " ");
			size[i-6] = header[i];
		}
		System.out.println(this.byteArrayToInt(size));*/
	}
	/*
	 *The method convert byte array and return int value.
	 *Most significant bit (bit 7) is set to zero in every byte. 
	 */
	private int byteArraySizeToInt(byte[] b) 
	{
	    return   b[3] & 0xFF | (b[2] & 0xFF) << 7 | (b[1] & 0xFF) << 14 | (b[0] & 0xFF) << 21;
	}
	
	/**
	 * Return a extension of a income file.
	 * @param file
	 * @return
	 */
	private String getFileExtension(File file){
		String fileName = file.getName();
		if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0){
			return fileName.substring(fileName.lastIndexOf(".")+1);
		} else {
			return "";
		}
	}
	
	/*
	 * getters and setters
	 */
	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
