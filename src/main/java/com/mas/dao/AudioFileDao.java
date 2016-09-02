package com.mas.dao;

import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import com.mas.entity.AudioMetadata;

public interface AudioFileDao {
	public AudioFile readTagContainer(String filePath) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException;
	public byte[] readFileFromHardDrive(String filePath);
	public void saveMetadateToDatabase(AudioMetadata audioFile);
}
