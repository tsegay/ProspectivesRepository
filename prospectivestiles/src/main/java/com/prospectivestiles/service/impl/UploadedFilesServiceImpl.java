package com.prospectivestiles.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prospectivestiles.dao.FileUploadDao;
import com.prospectivestiles.domain.UploadedFiles;
import com.prospectivestiles.service.UploadedFilesService;
@Service
@Transactional
public class UploadedFilesServiceImpl implements UploadedFilesService {
	
	@Inject
	private FileUploadDao fileUploadDao;

	@Override
	public UploadedFiles getUploadedFiles(long id) {
		return fileUploadDao.find(id);
	}

	@Override
	public List<UploadedFiles> getAllUploadedFiles() {
		return fileUploadDao.findAll();
	}

	@Override
	public List<UploadedFiles> getAllUploadedFilesByUserEntityId(
			long userEntityId) {
		return fileUploadDao.getUploadedFilesByUserEntityId(userEntityId);
	}

	@Override
	public void createUploadedFiles(UploadedFiles uploadedFiles) {
		fileUploadDao.create(uploadedFiles);		
	}

	@Override
	public void updateUploadedFiles(UploadedFiles uploadedFiles) {
		UploadedFiles uploadedFilesToUpdate = fileUploadDao.find(uploadedFiles.getId());
		uploadedFilesToUpdate.setFile(uploadedFiles.getFile());
//		uploadedFilesToUpdate.setUserEntity(uploadedFiles.getUserEntity());
		Date now = new Date();
		uploadedFilesToUpdate.setDateLastModified(now);
		uploadedFilesToUpdate.setLastModifiedBy(uploadedFiles.getLastModifiedBy());
		
		fileUploadDao.update(uploadedFilesToUpdate);
	}

	@Override
	public void delete(UploadedFiles uploadedFiles) {
		fileUploadDao.delete(uploadedFiles);
	}

}
