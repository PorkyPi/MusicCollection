package com.mas.services;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.ID3v1Tag;

import com.mas.dao.AudioFileDao;
import com.mas.entity.AudioMetadata;

@Named
public class MP3tagger implements DemuxerServices {
	@Inject
	AudioMetadata am;
	@Inject
	AudioFileDao afd;
	
	@Override
	public void readAllMetadataFromFile(String filePath) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
		MP3File f = (MP3File) afd.readTagContainer(filePath);
		MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
		
		am.setFilePath(filePath);
		am.setBitRate(audioHeader.getBitRateAsNumber());
		am.setDuration(audioHeader.getTrackLength());
		am.setFrequencyIndex(audioHeader.getSampleRateAsNumber());
		am.setLayerDescription(audioHeader.getMpegLayer());
		am.setMpegVersion(audioHeader.getMpegVersion());
		am.setChannelMode(audioHeader.getChannels());
		am.setDuration(audioHeader.getTrackLength());
		
		if(f.hasID3v1Tag() && f.hasID3v2Tag()){
			am.setVersionId3Tag("ID3v1/ID3v2");
			Tag tag = f.getTag();
			am.setTrackName(tag.getFirst(FieldKey.TITLE));
			am.setContributingArtist(tag.getFirst(FieldKey.ARTIST));
			am.setAlbum(tag.getFirst(FieldKey.ALBUM));
			am.setAlbumArtist(tag.getFirst(FieldKey.ALBUM_ARTIST));
			am.setComposer(tag.getFirst(FieldKey.COMPOSER));
			am.setYear(tag.getFirst(FieldKey.YEAR));
			am.setTrackNumberAndPositionInSet(tag.getFirst(FieldKey.TRACK));
			am.setPartOfSet(tag.getFirst(FieldKey.DISC_NO));
			am.setRating(tag.getFirst(FieldKey.RATING));
			am.setLyrics(tag.getFirst(FieldKey.LYRICS));
			am.setComments(tag.getFirst(FieldKey.COMMENT));
			am.setContentType(tag.getFirst(FieldKey.GENRE));
			am.setText(tag.getFirst(FieldKey.LYRICIST));
			am.setPublisher(tag.getFirst(FieldKey.RECORD_LABEL));
		}
		if(!f.hasID3v1Tag() && f.hasID3v2Tag()){
			am.setVersionId3Tag("ID3v2");
		}
		
		if(f.hasID3v1Tag() && !f.hasID3v2Tag()){
			am.setVersionId3Tag("ID3v1");
			ID3v1Tag tags = new ID3v1Tag(f.getID3v1Tag());
			am.setTrackName(tags.getFirstTitle());
			am.setContributingArtist(tags.getFirstArtist());
			am.setAlbum(tags.getFirstAlbum());
			am.setYear(tags.getFirstYear());
			am.setComments(tags.getFirstComment());
			am.setContentType(tags.getFirstGenre());
		}
		
		if(!f.hasID3v1Tag() && f.hasID3v2Tag()){
			am.setVersionId3Tag("no");
			System.out.println("File has't any tags");
		}
		
		am.setFileExtension("mp3");
		am.setPlayCount(0);
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
