package com.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cloudstorage.mappers.FileMapper;
import com.cloudstorage.models.Files;

@Service
public class FileService {
	private FileMapper fileMapper;

	public FileService(FileMapper fileMapper) {
		super();
		this.fileMapper = fileMapper;
	}

	public boolean isFileNameAvailable(String filename) {
		return fileMapper.getFileByName(filename) == null;

	}

	public Files getFile(Integer fileid) {
		return fileMapper.getFileById(fileid);
	}

	public Files getFile(String filename) {
		return fileMapper.getFileByName(filename);
	}

	public Integer addFile(Files file) {
		return fileMapper.insert(file);
	}

	public void deleteFile(Integer fileid) {
		fileMapper.delete(fileid);
	}

	public List<Files> listAllFiles(Integer userid) {
		return fileMapper.getAllFiles(userid);
	}
}
