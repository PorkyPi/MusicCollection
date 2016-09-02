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
	private String year;                                  //TYER Year
	private String trackNumberAndPositionInSet;           //TRCK Track number/Position set (7/13)
	private String partOfSet;                             //TPOS Part(CD) of set(CDs) (2/6) 
	private String rating;                                //POPM first
	private String lyrics;                                //USLT lyrics
	private int playCount;                                //Count of listened
	private String fileExtension;                         //extension (.mp3)
	private int duration;                                 //houer:minutes:secunds
	private long fileSize;                                //
	private short bitRate;                                //bit rate                  
	private int frequencyIndex;                           //Sampling rate frequency index 
	private String versionId3Tag;                         //version of Id3Tag           
	private Date dateСhange;                              //dd.MM.yyyy hh:mm
	private byte layerDescription;                        //Layer description
	private byte[] byteArr;                               //byte array the file
	private String comments;                              //COMM
	private String contentType;                           //TCON remix or cover (genre)
	private String text;                                  //TEXT The 'Lyricist(s)/Text writer(s)' frame
	private String publisher;                             //TPUB the name of the label or publisher
	private byte mpegVersion;                             //MPEG version
	private byte channelMode;                             //Stereo/Joint stereo/Dual channel (2 mono channel)/Single channel (Mono)
	

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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String getDurationToString() {
		return "" + duration / 60 + "." + duration % 60 + "min";
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public Short getBitRate() {
		return bitRate;
	}

	public void setBitRate(Short bitRate) {
		this.bitRate = bitRate;
	}

	public int getFrequencyIndex() {
		return frequencyIndex;
	}

	public void setFrequencyIndex(int frequencyIndex) {
		this.frequencyIndex = frequencyIndex;
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
	
	public String getLayerDescriptionAsString(){
		switch(this.layerDescription){
		case 0: return "reserved";
		case 3: return "Layer 3";
		case 2: return "Layer 2";
		case 1: return "Layer 1";
		}
		return "Empty";
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
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public byte getMpegVersion() {
		return mpegVersion;
	}

	public void setMpegVersion(byte mpegVersion) {
		this.mpegVersion = mpegVersion;
	}
	
	public String getMpegVersionAsString(){
		switch(this.mpegVersion){
		case 0: return "MPEG Version 2.5 (later extension of MPEG 2)";
		case 1: return "reserved";
		case 2: return "MPEG Version 2(ISO/IEC 13818-3)";
		case 3: return "MPEG Version 1(ISO/IEC 11172-3)";
		}
		return "Empty";
	}

	public byte getChannelMode() {
		return channelMode;
	}

	public void setChannelMode(byte chenalMode) {
		this.channelMode = chenalMode;
	}
	
	public String getChannelModeAsString(){
		switch(this.channelMode){
		case 0: return "Stereo";
		case 1: return "Join stereo (Stereo)";
		case 2: return "Dual channel (2 mono channels)";
		case 3: return "Single channel (Mono)";
		}
		return "Empty";
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
