package com.mas.dao;

import com.mas.entity.AudioFile;

public interface AudioFileDao {
	public byte[] readFileFromHardDrive(String filePath);
	public void writeFileToHardDrive(AudioFile audioFile);
	public void saveMetadateToDatabase(AudioFile audioFile);
}
