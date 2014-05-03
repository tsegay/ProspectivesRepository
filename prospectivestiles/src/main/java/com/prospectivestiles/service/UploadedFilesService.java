package com.prospectivestiles.service;

import java.util.List;

import com.prospectivestiles.domain.UploadedFiles;

public interface UploadedFilesService {

	UploadedFiles getUploadedFiles(long id);
	List<UploadedFiles> getAllUploadedFiles();
	List<UploadedFiles> getAllUploadedFilesByUserEntityId(long userEntityId);
	void createUploadedFiles(UploadedFiles uploadedFiles);
	void updateUploadedFiles(UploadedFiles uploadedFiles);
	void delete(UploadedFiles address);
}
