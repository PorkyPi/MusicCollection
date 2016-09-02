package com.mas.services;

import javax.inject.Inject;
import javax.inject.Named;

import com.mas.dao.AudioFileDaoImpl;
import com.mas.entity.AudioFile;

@Named
public class DemuxerFLACServiceImpl implements DemuxerServices {
	@Inject
	private AudioFileDaoImpl audioFileDaoImpl;
	@Inject
	private AudioFile audioFile;
	
	@Override
	public void readAllMetadataFromFile(String filePath) {
		// TODO Auto-generated method stub

	}

}
