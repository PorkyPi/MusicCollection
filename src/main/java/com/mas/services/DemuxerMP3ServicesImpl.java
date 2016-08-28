package com.mas.services;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import com.mas.dao.AudioFileDao;
import com.mas.dao.AudioFileDaoImpl;
import com.mas.entity.AudioFile;

@Named
public class DemuxerMP3ServicesImpl implements DemuxerServices {
	@Inject
	private AudioFileDaoImpl audioFileDaoImpl;
	@Inject
	private AudioFile audioFile;
	
	@Override
	public void readAllMetadataFromFile(String filePath) {
		this.audioFile.setByteArr(this.audioFileDaoImpl.readFileFromHardDrive(filePath));        //push ByteArr to entity
		this.audioFile.setFileExtension(this.getFileExtension(new File(filePath)));         //push FileExtension to entity
		byte[] fileByteArray = this.audioFile.getByteArr();
		String versionId3 = "ID3v2.";
		byte[] metadataSize;
		int size;
		int intFrameSize;
		byte[] byteFrameSize = new byte[4];
		String description = new String();
		
		if(fileByteArray[0] == 'I' && fileByteArray[1] == 'D' && fileByteArray[2] == '3'){  //only ID3v2 tags
			for(int i = 3; i < 5; i++){                                                     //read version ID3v2 tags
				versionId3 += fileByteArray[i];
			}
			this.audioFile.setVersionId3Tag(versionId3);                                    //push versionId3tags
			
			if(fileByteArray[5] == 0){                                                      //is ID3v2 contains extended metadata  
				metadataSize = new byte[4];
				for(int i = 6; i < 10; i++){
					metadataSize[i-6] = fileByteArray[i];
				}
				
				size = this.byteArraySizeToInt(metadataSize);                               //size of id3 chunk
				
				for(int i = 10; i < size;){
					if(fileByteArray[i] == 0){
						break;                                                              //if chunk empty
					}
					
					String tagId = "" + (char)fileByteArray[i] + (char)fileByteArray[i+1];
					tagId += "" + (char)fileByteArray[i+2] + (char)fileByteArray[i+3];      //get id3tag id 4 byte
					
					byteFrameSize[0] = fileByteArray[i+4];
					byteFrameSize[1] = fileByteArray[i+5];
					byteFrameSize[2] = fileByteArray[i+6];
					byteFrameSize[3] = fileByteArray[i+7];
					
					intFrameSize = this.byteArrayToInt(byteFrameSize);                      //get frame length
					
					description = "";
					for(int j = 0; j < intFrameSize; j++){
						if(tagId.equals("POPM")){
							description += (char)fileByteArray[i+10+(intFrameSize-1)];
							break;
						}
						if(this.filter(fileByteArray[i+j+10])){                             //filtering all
							description += "" + (char) fileByteArray[i+j+10];               //not need symbols
						}
					}

					this.tagSwither(tagId, description);
					

					i += intFrameSize + 10;
				}
				
			}
		}
	} 
	/**
	*The method convert byte array and return int value.
	*Most significant bit (bit 7) is set to zero in every byte. 
	*@param
	*@return
	*/
	private int byteArraySizeToInt(byte[] b) {
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
			
	/**
	* Return int type after converted byte array.
	* @param b
	* @return
	*/
	private int byteArrayToInt(byte[] b){
		return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
	}
	
	/**
	 * 
	 * @param tag
	 * @param description
	 */
	private void tagSwither(String tag, String description){
		switch(tag){
		case "TIT2": this.audioFile.setTrackName(description);
					break;
		case "TPE1": this.audioFile.setContributingArtist(description);
					break;
		case "TALB": this.audioFile.setAlbum(description);
					break;
		case "TPE2": this.audioFile.setAlbumArtist(description);
					break;
		case "TCOM": this.audioFile.setComposer(description);
					break;
		case "TYER": this.audioFile.setYear(description);
					break;
		case "TRCK": this.audioFile.setTrackNumberAndPositionInSet(description);
					break;
		case "TPOS": this.audioFile.setPartOfSet(description);
					break;
		case "POPM": this.audioFile.setRating(description);
					break;
		case "USLT": this.audioFile.setLyrics(description);
					break;
		}
	}
	
	private boolean filter(byte bt){
		if(bt > 32 && bt < 127){
			return true;
		} else{
			return false;
		}
	}
	/*
	 * getters and setters
	 */
	public AudioFileDao getAudioFileDaoImpl() {
		return audioFileDaoImpl;
	}
	public void setAudioFileDaoImpl(AudioFileDaoImpl audioFileDaoImpl) {
		this.audioFileDaoImpl = audioFileDaoImpl;
	}
	public AudioFile getAudioFile() {
		return audioFile;
	}
	public void setAudioFile(AudioFile audioFile) {
		this.audioFile = audioFile;
	}
}
