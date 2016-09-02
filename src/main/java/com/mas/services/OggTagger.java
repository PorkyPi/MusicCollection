package com.mas.services;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentTag;

import com.mas.dao.AudioFileDao;
import com.mas.entity.AudioMetadata;

@Named
public class OggTagger implements DemuxerServices {
	@Inject
	AudioMetadata am;
	@Inject
	AudioFileDao afd;
	@Override
	public void readAllMetadataFromFile(String filePath)
			throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		AudioFile af = afd.readTagContainer(filePath);
		@SuppressWarnings("unused")
		VorbisCommentTag ovtag = (VorbisCommentTag)af.getTag();
	}

}
