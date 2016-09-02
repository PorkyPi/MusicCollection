package com.mas.services;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp4.Mp4AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.mp4.Mp4FieldKey;
import org.jaudiotagger.tag.mp4.Mp4Tag;

import com.mas.dao.AudioFileDao;
import com.mas.entity.AudioMetadata;

@Named
public class Mp4tagger implements DemuxerServices{
	@Inject
	AudioMetadata am;
	@Inject
	AudioFileDao afd;
	@Override
	public void readAllMetadataFromFile(String filePath)
			throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		AudioFile f = afd.readTagContainer(filePath);
		Mp4Tag mp4tag = (Mp4Tag) f.getTag();
		
		am.setFilePath(filePath);
		am.setPlayCount(0);
		am.setFileExtension(this.getFileExtension(new File(filePath)));
		am.setTrackName(mp4tag.getFirst(Mp4FieldKey.TITLE));
		am.setContributingArtist(mp4tag.getFirst(Mp4FieldKey.ARTIST));
		am.setAlbum(mp4tag.getFirst(Mp4FieldKey.ALBUM));
		am.setAlbumArtist(mp4tag.getFirst(Mp4FieldKey.ALBUM_ARTIST));
		am.setComposer(mp4tag.getFirst(Mp4FieldKey.COMPOSER));
		am.setYear(mp4tag.getFirst(Mp4FieldKey.MM_ORIGINAL_YEAR));
		am.setTrackNumberAndPositionInSet(mp4tag.getFirst(Mp4FieldKey.TRACK));
		am.setPartOfSet(mp4tag.getFirst(Mp4FieldKey.DISCNUMBER));
		am.setRating(mp4tag.getFirst(Mp4FieldKey.RATING));
		am.setLyrics(mp4tag.getFirst(Mp4FieldKey.LYRICS));
		am.setPlayCount(0);
		
		Mp4AudioHeader mah = (Mp4AudioHeader) f.getAudioHeader();
		am.setDuration(mah.getTrackLength());
		am.setBitRate(mah.getBitRateAsNumber());
		am.setFrequencyIndex(mah.getSampleRateAsNumber());
		am.setChannelMode(mah.getChannels());
		am.setMpegVersion(mah.getEncodingType());
		
		am.setComments(mp4tag.getFirst(Mp4FieldKey.COMMENT));
		am.setContentType(mp4tag.getFirst(Mp4FieldKey.GENRE));
		am.setText(mp4tag.getFirst(Mp4FieldKey.LYRICIST));
		am.setPublisher(mp4tag.getFirst(Mp4FieldKey.MM_PUBLISHER));
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

	public AudioMetadata getAm() {
		return am;
	}

	public void setAm(AudioMetadata am) {
		this.am = am;
	}

	public AudioFileDao getAfd() {
		return afd;
	}

	public void setAfd(AudioFileDao afd) {
		this.afd = afd;
	}
}
