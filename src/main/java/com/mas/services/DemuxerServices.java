package com.mas.services;

import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public interface DemuxerServices {
	public void readAllMetadataFromFile(String filePath) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException;
}
