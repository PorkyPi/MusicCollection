package com.mas.dao;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import com.mas.entity.AudioMetadata;

public class AudioFileDaoImpl implements AudioFileDao {
	
	private EntityManager em = null;
	
	@Override
	public byte[] readFileFromHardDrive(String filePath) {
		File file = new File(filePath);
		long length = file.length();
		byte[] byteArr = new byte[(int)length];
		int offset = 0;
		int numRead = 0;
		
		try(InputStream is = new BufferedInputStream(new FileInputStream(file))){
			while(offset < file.length() && (numRead = is.read(byteArr, offset, ((int)length-offset))) >= 0){
				
				offset += numRead;
			}
			
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		return byteArr;
	}

	@Override
	public AudioFile readTagContainer(String filePath){
		File f = new File(filePath);
		try {
			AudioFile af = AudioFileIO.read(f);
			return af;
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AudioMetadata> getAllAudioMetadatas() {
		TypedQuery<AudioMetadata> query = em.createQuery("SELECT b FROM AUDIO_METADATA b", AudioMetadata.class);
		return query.getResultList();
	}
	
	
	@Override
	public void updateAudioMetadata(AudioMetadata audioMetadata) {
		if(em.find(AudioMetadata.class, audioMetadata.getAudioFileId()) != null){;
			em.merge(audioMetadata);
		}
	}
}
