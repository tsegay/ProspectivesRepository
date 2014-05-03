package com.prospectivestiles.dao;

import java.util.List;

import com.prospectivestiles.domain.UploadedFiles;

public interface FileUploadDao extends Dao<UploadedFiles> {

	List<UploadedFiles> getUploadedFilesByUserEntityId(long userEntityId);

}