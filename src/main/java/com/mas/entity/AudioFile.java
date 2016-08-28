package com.mas.entity;

import java.sql.Date;

import javax.inject.Named;

@Named
public class AudioFile {
	private int audioFileId;
	private String filePath;                              //Path to the file
	private String trackName;                             //TIT2 Title/songname/content description
	private String contributingArtist;                    //TPE1 Lead performer(s)/Soloist(s)
	private String album;                                 //TALB Album/Movie/Show title
	private String albumArtist;                           //TPE2 Band/orchestra/accompaniment
	private String composer;                              //TCOM Composer
	private byte genre;                                   //Appendix from ID3v1
	private String year;                                   //TYER Year
	private String trackNumberAndPositionInSet;           //TRCK Track number/Position set (7/13)
	private String partOfSet;                             //TPOS Part(CD) of set(CDs) (2/6) 
	private String rating;                                //POPM first
	private String lyrics;                                //USLT lyrics
	private int playCount;                                //Count of listened
	private String fileExtension;                         //extension (.mp3)
	private Date musicLength;                             //houer:minutes:secunds
	private float fileSize;                               //mb.kb
	private short bitRate;                                //bit rate                  
	private byte samplingFrequency;                       //Sampling rate frequency index 
	private String versionId3Tag;                         //version of Id3Tag           
	private Date dateСhange;                              //dd.MM.yyyy hh:mm
	private byte layerDescription;                        //Layer description
	private byte[] byteArr;                               //byte array the file

	public AudioFile(){
	}
	/*
	 * Getters and setters
	 */
	
	public int getAudioFileId() {
		return audioFileId;
	}

	public void setAudioFileId(int audioFileId) {
		this.audioFileId = audioFileId;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public String getContributingArtist() {
		return contributingArtist;
	}

	public void setContributingArtist(String contributingArtist) {
		this.contributingArtist = contributingArtist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAlbumArtist() {
		return albumArtist;
	}

	public void setAlbumArtist(String albumArtist) {
		this.albumArtist = albumArtist;
	}

	public String getComposer() {
		return composer;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public byte getGenre() {
		return genre;
	}

	public void setGenre(byte genre) {
		this.genre = genre;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTrackNumberAndPositionInSet() {
		return trackNumberAndPositionInSet;
	}

	public void setTrackNumberAndPositionInSet(String trackNumberAndPositionInSet) {
		this.trackNumberAndPositionInSet = trackNumberAndPositionInSet;
	}

	public String getPartOfSet() {
		return partOfSet;
	}

	public void setPartOfSet(String partOfSet) {
		this.partOfSet = partOfSet;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public int getPlayCount() {
		return playCount;
	}

	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public Date getMusicLength() {
		return musicLength;
	}

	public void setMusicLength(Date musicLength) {
		this.musicLength = musicLength;
	}

	public float getFileSize() {
		return fileSize;
	}

	public void setFileSize(float fileSize) {
		this.fileSize = fileSize;
	}

	public short getBitRate() {
		return bitRate;
	}

	public void setBitRate(short bitRate) {
		this.bitRate = bitRate;
	}

	public byte getSamplingFrequency() {
		return samplingFrequency;
	}

	public void setSamplingFrequency(byte samplingFrequency) {
		this.samplingFrequency = samplingFrequency;
	}

	public String getVersionId3Tag() {
		return versionId3Tag;
	}

	public void setVersionId3Tag(String versionId3Tag) {
		this.versionId3Tag = versionId3Tag;
	}

	public Date getDateСhange() {
		return dateСhange;
	}

	public void setDateСhange(Date dateСhange) {
		this.dateСhange = dateСhange;
	}

	public byte getLayerDescription() {
		return layerDescription;
	}

	public void setLayerDescription(byte layerDescription) {
		this.layerDescription = layerDescription;
	}
	
	public byte[] getByteArr() {
		return byteArr;
	}

	public void setByteArr(byte[] byteArr) {
		this.byteArr = byteArr;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}
	
	public String toString(){
		String txt = new String();
		txt += "Id file: " + this.audioFileId + ". ";
		txt += "TrackName: " + this.trackName + ". ";
		txt += "Album: " + this.album + ". ";
		txt += "AlbumArtist: " + this.albumArtist + ". ";
		return txt;
	}
}
