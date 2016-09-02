package com.mas.services;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import com.mas.dao.AudioFileDao;
import com.mas.dao.AudioFileDaoImpl;
import com.mas.entity.AudioFile;

@Named
public class DemuxerMP3ServiceImpl implements DemuxerServices {
	@Inject
	private AudioFileDaoImpl audioFileDaoImpl;
	@Inject
	private AudioFile audioFile;
	
	/**
	 * Grabbing all metadata from file
	 * @param
	 */
	@Override
	public void readAllMetadataFromFile(String filePath) {

		this.audioFile.setFilePath(filePath);
		this.audioFile.setByteArr(this.audioFileDaoImpl.readFileFromHardDrive(filePath));   //push ByteArr to entity
		this.audioFile.setFileExtension(this.getFileExtension(new File(filePath)));         //push FileExtension to entity
		byte[] fileByteArray = this.audioFile.getByteArr();
		this.audioFile.setFileSize(fileByteArray.length);
		String versionId3 = "ID3v2.";
		byte[] metadataSize;
		int size;
		int intFrameSize;
		byte[] byteFrameSize = new byte[4];
		String description = new String();
		
		System.out.println();
		
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
				
				size = this.id3v2TagSize(metadataSize);                               //size of id3 chunk
				
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

					this.tagSwitcher(tagId, description);
					

					i += intFrameSize + 10;
				}


				this.mpegVersionSwitcher(fileByteArray[size+11]);
				this.layerDescriptionSwitcher(fileByteArray[size+11]);
				this.bitrateSwitcher(fileByteArray[size+12]);
				this.frequencyIndexSwitcher(fileByteArray[size+12]);
				this.channelModeSwitcher(fileByteArray[size+13]);
				
				for(int i = size+11; i <= size+256; i++){                             //Xing/VBR
					if(fileByteArray[i] == 'I' && fileByteArray[i+1] == 'n' && fileByteArray[i+2] == 'f'){
						byte[] b = new byte[4];                                       //Durationg for xing
						b[0] = fileByteArray[i+8];
						b[1] = fileByteArray[i+9];
						b[2] = fileByteArray[i+10];
						b[3] = fileByteArray[i+11];
						System.out.println("File is Info");
						this.audioFile.setDuration(this.musicDurationIfVBR(b));
						break;
 					} else if(fileByteArray[i] == 'X' && fileByteArray[i+1] == 'i' && fileByteArray[i+2] == 'n'){
						byte[] b = new byte[4];                                       //Durationg for xing
						b[0] = fileByteArray[i+8];
						b[1] = fileByteArray[i+9];
						b[2] = fileByteArray[i+10];
						b[3] = fileByteArray[i+11];
						System.out.println("File is Xing");
						this.audioFile.setDuration(this.musicDurationIfVBR(b));
						break;
					} else if(fileByteArray[i] == 'V' && fileByteArray[i+1] == 'B' && fileByteArray[i+2] == 'R'){
						byte[] b = new byte[4];                                       //Durationg for xing
						b[0] = fileByteArray[i+14];
						b[1] = fileByteArray[i+15];
						b[2] = fileByteArray[i+16];
						b[3] = fileByteArray[i+17];
						System.out.println("File is VBR");
						this.audioFile.setDuration(this.musicDurationIfVBR(b));
						break;
					} else if(i == size + 256){
						System.out.println("File is CBR");
						this.audioFile.setDuration(this.musicDurationIfCBR(size + 10));
						break;
					}
				}
				
			}
		} else {
			System.out.println("File is ID3v1" + filePath);
		}
	} 
	
	private int musicDurationIfCBR(int tagsSize){
		long fileSize = this.audioFile.getFileSize() - 128 - tagsSize;
		int bitRate = this.audioFile.getBitRate() * 1000;
		return (int) (fileSize / bitRate * 8);
	}
	
	private int musicDurationIfVBR(byte[] b){
		int numberOfFrame = this.byteArrayToInt(b);
		int samplesPerFrame = this.samplesPerFrame();
		int samplingRate = this.audioFile.getFrequencyIndex();
		return numberOfFrame * samplesPerFrame / samplingRate;
	}
	
	private int samplesPerFrame(){
		switch(this.audioFile.getLayerDescription()){
		case 1: return 384;
		case 2: return 1152;
		case 3: if(this.audioFile.getMpegVersion() == 3){
					return 1152;
				} else {
					return 576;
				}
		}
		return 0;                                                   //it will newer happen 
	}
	
	/**
	*The ID3v2 tag size is encoded with four bytes where the most significant bit (bit 7) 
	*is set to zero in every byte, making a total of 28 bits.
	*@param
	*@return
	*/
	private int id3v2TagSize(byte[] b) {
		return   b[3] & 0xFF | (b[2] & 0xFF) << 7 | (b[1] & 0xFF) << 14 | (b[0] & 0xFF) << 21;
	}
			
	/**
	* Return extension of a income file.
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
	* Convert 4 byte array to integer 
	* @param b
	* @return
	*/
	private int byteArrayToInt(byte[] b){
		return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
	}
	
	/**
	 * Select declared ID3v2 frames
	 * @param tag
	 * @param description
	 */
	private void tagSwitcher(String tag, String description){
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
		case "COMM": this.audioFile.setComments(description);
					break;
		case "TCON": this.audioFile.setContentType(description);
					break;
		case "TEXT": this.audioFile.setText(description);
					break;
		case "TPUB": this.audioFile.setPublisher(description);
					break;
		default: System.out.println(tag);                                             //debug
		}
	}
	
	/**
	 * MPEG Audio version ID
	 * 0 - MPEG Version 2.5 (later extension of MPEG 2)
	 * 8 - reserved
     * 16 - MPEG Version 2 (ISO/IEC 13818-3)
     * 24 - MPEG Version 1 (ISO/IEC 11172-3)
	 * @param b
	 */
	private void mpegVersionSwitcher(byte b){
		b = (byte) (b & 24);
		switch(b){
		case 0: this.audioFile.setMpegVersion((byte)0);
				break;
		case 8: this.audioFile.setMpegVersion((byte)1);
				break;
		case 16: this.audioFile.setMpegVersion((byte)2);
				break;
		case 24: this.audioFile.setMpegVersion((byte)3);
		}
	}
	
	/**
	 * Layer description
	 * 0 - reserved
	 * 2 - Layer III
	 * 4 - Layer II
	 * 6 - Layer I
	 * @param b
	 */
	private void layerDescriptionSwitcher(byte b){
		b = (byte) (b & 6);
		switch(b){
		case 0: this.audioFile.setLayerDescription((byte)0);
				break;
		case 2: this.audioFile.setLayerDescription((byte)3);
				break;
		case 4: this.audioFile.setLayerDescription((byte)2);
				break;
		case 6: this.audioFile.setLayerDescription((byte)1);
				break;
		}
	}
	
	/**
	 * Bitrate index
	 * @param b
	 */
	private void bitrateSwitcher(byte b){
		byte mpgver;
		if(this.audioFile.getMpegVersion() == 0 || this.audioFile.getMpegVersion() == 2){
			mpgver = 2;
		} else {
			mpgver = this.audioFile.getMpegVersion();
		}
		
		byte layer = this.audioFile.getLayerDescription();
		b = (byte) (b & -16);
		
		switch(b){
		case 0: break;
		
		case 16: if(mpgver == 3 && layer == 2){
					this.audioFile.setBitRate((short)8);
				} else if(mpgver == 3 && layer == 3){
					this.audioFile.setBitRate((short)8);
				} else {
					this.audioFile.setBitRate((short)8);
				}
				break;
				
		case 32: if(mpgver == 3 && layer == 1){
					this.audioFile.setBitRate((short)64);
		        } else if(mpgver == 3 && layer == 2){
		        	this.audioFile.setBitRate((short)48);
		        } else if(mpgver == 3 && layer == 3){
		        	this.audioFile.setBitRate((short)40);
		        } else if(mpgver == 2 && layer == 1){
		        	this.audioFile.setBitRate((short)48);
		        } else if(mpgver == 2  && layer == 2){
		        	this.audioFile.setBitRate((short)16);
		        } else if(mpgver == 2 && layer == 3){
		        	this.audioFile.setBitRate((short)16);
		        }
				break;
		case 48: if(mpgver == 3 && layer == 1){
					this.audioFile.setBitRate((short)96);
		        } else if(mpgver == 3  && layer == 2){
		        	this.audioFile.setBitRate((short)56);
		        } else if(mpgver == 3 && layer == 3){
		        	this.audioFile.setBitRate((short)48);
		        } else if(mpgver == 2 && layer == 1){
		        	this.audioFile.setBitRate((short)56);
		        } else if(mpgver == 2 && layer == 2){
		        	this.audioFile.setBitRate((short)24);
		        } else if(mpgver == 2 && layer == 3){
		        	this.audioFile.setBitRate((short)24);
		        }
				break;
		case 64: if(mpgver == 3 && layer == 1){
					this.audioFile.setBitRate((short)128);
		        } else if(mpgver == 3 && layer == 2){
		        	this.audioFile.setBitRate((short)64);
		        } else if(mpgver == 3 && layer == 3){
		        	this.audioFile.setBitRate((short)56);
		        } else if(mpgver == 2 && layer == 1){
		        	this.audioFile.setBitRate((short)64);
		        } else if(mpgver == 2 && layer == 2){
		        	this.audioFile.setBitRate((short)32);
		        } else if(mpgver == 2 && layer == 3){
		        	this.audioFile.setBitRate((short)32);
		        }
				break;
		case 80: if(mpgver == 3 && layer == 1){
					this.audioFile.setBitRate((short)160);
		        } else if(mpgver == 3 && layer == 2){
		        	this.audioFile.setBitRate((short)80);
		        } else if(mpgver == 3 && layer == 3){
		        	this.audioFile.setBitRate((short)64);
		        } else if(mpgver == 2 && layer == 1){
		        	this.audioFile.setBitRate((short)80);
		        } else if(mpgver == 2 && layer == 2){
		        	this.audioFile.setBitRate((short)40);
		        } else if(mpgver == 2 && layer == 3){
		        	this.audioFile.setBitRate((short)40);
		        }
				break;
		case 96: if(mpgver == 3 && layer == 1){
					this.audioFile.setBitRate((short)192);
		        } else if(mpgver == 3 && layer == 2){
		        	this.audioFile.setBitRate((short)96);
		        } else if(mpgver == 3 && layer == 3){
		        	this.audioFile.setBitRate((short)80);
		        } else if(mpgver == 2 && layer == 1){
		        	this.audioFile.setBitRate((short)96);
		        } else if(mpgver == 2 && layer == 2){
		        	this.audioFile.setBitRate((short)48);
		        } else if(mpgver == 2 && layer == 3){
		        	this.audioFile.setBitRate((short)48);
		        }
				break;		
		case 112: if(mpgver == 3 && layer == 1){
					this.audioFile.setBitRate((short)224);
		        } else if(mpgver == 3 && layer == 2){
		        	this.audioFile.setBitRate((short)112);
		        } else if(mpgver == 3 && layer == 3){
		        	this.audioFile.setBitRate((short)96);
		        } else if(mpgver == 2 && layer == 1){
		        	this.audioFile.setBitRate((short)112);
		        } else if(mpgver == 2 && layer == 2){
		        	this.audioFile.setBitRate((short)56);
		        } else if(mpgver == 2 && layer == 3){
		        	this.audioFile.setBitRate((short)56);
		        }
				break;
		case -128: if(mpgver == 3 && layer == 1){
					this.audioFile.setBitRate((short)256);
		        } else if(mpgver == 3 && layer == 2){
		        	this.audioFile.setBitRate((short)128);
		        } else if(mpgver == 3 && layer == 3){
		        	this.audioFile.setBitRate((short)112);
		        } else if(mpgver == 2 && layer == 1){
		        	this.audioFile.setBitRate((short)128);
		        } else if(mpgver == 2 && layer == 2){
		        	this.audioFile.setBitRate((short)64);
		        } else if(mpgver == 2 && layer == 3){
		        	this.audioFile.setBitRate((short)64);
		        }
				break;
		case -112: if(mpgver == 3 && layer == 1){
					this.audioFile.setBitRate((short)288);
		        } else if(mpgver == 3 && layer == 2){
		        	this.audioFile.setBitRate((short)160);
		        } else if(mpgver == 3 && layer == 3){
		        	this.audioFile.setBitRate((short)128);
		        } else if(mpgver == 2 && layer == 1){
		        	this.audioFile.setBitRate((short)144);
		        } else if(mpgver == 2 && layer == 2){
		        	this.audioFile.setBitRate((short)80);
		        } else if(mpgver == 2 && layer == 3){
		        	this.audioFile.setBitRate((short)80);
		        }
				break;
		case -96: if(mpgver == 3 && layer == 1){
					this.audioFile.setBitRate((short)320);
		        } else if(mpgver == 3 && layer == 2){
		        	this.audioFile.setBitRate((short)192);
		        } else if(mpgver == 3 && layer == 3){
		        	this.audioFile.setBitRate((short)160);
		        } else if(mpgver == 2 && layer == 1){
		        	this.audioFile.setBitRate((short)160);
		        } else if(mpgver == 2 && layer == 2){
		        	this.audioFile.setBitRate((short)96);
		        } else if(mpgver == 2 && layer == 3){
		        	this.audioFile.setBitRate((short)96);
		        }
				break;
		case -80: if(mpgver == 3 && layer == 1){
					this.audioFile.setBitRate((short)352);
		        } else if(mpgver == 3 && layer == 2){
		        	this.audioFile.setBitRate((short)224);
		        } else if(mpgver == 3 && layer == 3){
		        	this.audioFile.setBitRate((short)192);
		        } else if(mpgver == 2 && layer == 1){
		        	this.audioFile.setBitRate((short)176);
		        } else if(mpgver == 2 && layer == 2){
		        	this.audioFile.setBitRate((short)112);
		        } else if(mpgver == 2 && layer == 3){
		        	this.audioFile.setBitRate((short)112);
		        }
				break;
		case -64: if(mpgver == 3 && layer == 1){
					this.audioFile.setBitRate((short)384);
		        } else if(mpgver == 3 && layer == 2){
		        	this.audioFile.setBitRate((short)256);
		        } else if(mpgver == 3 && layer == 3){
		        	this.audioFile.setBitRate((short)224);
		        } else if(mpgver == 2 && layer == 1){
		        	this.audioFile.setBitRate((short)192);
		        } else if(mpgver == 2 && layer == 2){
		        	this.audioFile.setBitRate((short)128);
		        } else if(mpgver == 2 && layer == 3){
		        	this.audioFile.setBitRate((short)128);
		        }
				break;
		case -48: if(mpgver == 3 && layer == 1){
					this.audioFile.setBitRate((short)416);
		        } else if(mpgver == 3 && layer == 2){
		        	this.audioFile.setBitRate((short)320);
		        } else if(mpgver == 3 && layer == 3){
		        	this.audioFile.setBitRate((short)256);
		        } else if(mpgver == 2 && layer == 1){
		        	this.audioFile.setBitRate((short)224);
		        } else if(mpgver == 2 && layer == 2){
		        	this.audioFile.setBitRate((short)144);
		        } else if(mpgver == 2 && layer == 3){
		        	this.audioFile.setBitRate((short)144);
		        }
				break;
		case -32: if(mpgver == 3 && layer == 1){
					this.audioFile.setBitRate((short)448);
		        } else if(mpgver == 3 && layer == 2){
		        	this.audioFile.setBitRate((short)384);
		        } else if(mpgver == 3 && layer == 3){
		        	this.audioFile.setBitRate((short)320);
		        } else if(mpgver == 2 && layer == 1){
		        	this.audioFile.setBitRate((short)256);
		        } else if(mpgver == 2 && layer == 2){
		        	this.audioFile.setBitRate((short)160);
		        } else if(mpgver == 2 && layer == 3){
		        	this.audioFile.setBitRate((short)160);
		        }
				break;
		}
	}
	
	/**
	 * Sampling rate frequency index
	 * @param b
	 */
	private void frequencyIndexSwitcher(byte b){
		byte mpeg = this.audioFile.getMpegVersion();
		b = (byte) (b & 12);
		switch(b){
		case 0: if(mpeg == 3){
					this.audioFile.setFrequencyIndex(44100);
				} else if(mpeg == 2){
					this.audioFile.setFrequencyIndex(22050);
				}else if(mpeg == 0){
					this.audioFile.setFrequencyIndex(11025);
				}
				break;
		case 4: if(mpeg == 3){
					this.audioFile.setFrequencyIndex(48000);
				} else if(mpeg == 2){
					this.audioFile.setFrequencyIndex(24000);
				}else if(mpeg == 0){
					this.audioFile.setFrequencyIndex(12000);
				}
				break;
		case 8: if(mpeg == 3){
					this.audioFile.setFrequencyIndex(32000);
				} else if(mpeg == 2){
					this.audioFile.setFrequencyIndex(16000);
				}else if(mpeg == 0){
					this.audioFile.setFrequencyIndex(8000);
				}
				break;
		}
	}
	
	/**
	 * Channel Mode
	 * 0 - Stereo
	 * 64 - Joint stereo (Stereo)
	 * -128 - Dual channel (2 mono channels)
	 * -64 - Single channel (Mono)
	 * @param b
	 */
	private void channelModeSwitcher(byte b){
		b = (byte) (b & -64);
		switch(b){
		case 0: this.audioFile.setChannelMode((byte)0);
				break;
		case 64: this.audioFile.setChannelMode((byte)1);
				break;
		case -128: this.audioFile.setChannelMode((byte)2);
				break;
		case -64: this.audioFile.setChannelMode((byte)3);
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