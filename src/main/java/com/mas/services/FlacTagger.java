package com.mas.services;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.flac.FlacAudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.flac.FlacTag;

import com.mas.dao.AudioFileDao;
import com.mas.entity.AudioMetadata;

@Named
public class FlacTagger implements DemuxerServices{
	@Inject
	AudioMetadata am;
	@Inject
	AudioFileDao afd;
	
	@Override
	public void readAllMetadataFromFile(String filePath)
			throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		AudioFile af = afd.readTagContainer(filePath);
		FlacTag tag = (FlacTag) af.getTag();
		FlacAudioHeader fah = (FlacAudioHeader) af.getAudioHeader();
		
		am.setFilePath(filePath);
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
		am.setPlayCount(0);
		am.setFileExtension("flac");
		am.setDuration(fah.getTrackLength());
		am.setBitRate(fah.getBitRateAsNumber());
		am.setFrequencyIndex(fah.getSampleRateAsNumber());
		am.setVersionId3Tag("vorbis");
		am.setComments(tag.getFirst(FieldKey.COMMENT));
		am.setContentType(tag.getFirst(FieldKey.GENRE));
		am.setText(tag.getFirst(FieldKey.LYRICIST));
		am.setPublisher(tag.getFirst(FieldKey.PRODUCER));
		am.setMpegVersion(fah.getFormat());
		am.setChannelMode(fah.getChannels());
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
