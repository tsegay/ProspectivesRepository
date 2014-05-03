package com.prospectivestiles.dao.hbn;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.prospectivestiles.dao.FileUploadDao;
import com.prospectivestiles.domain.UploadedFiles;
@Repository
public class HbnFileUploadDao extends AbstractHbnDao<UploadedFiles> implements FileUploadDao {

	@Override
	public List<UploadedFiles> getUploadedFilesByUserEntityId(long userEntityId) {
		Session session = getSession();
		Query query = session.getNamedQuery("findUploadedFilesByUserEntityId");
		query.setParameter("id", userEntityId);
		
		@SuppressWarnings("unchecked")
		List<UploadedFiles> uploadedFilesList = query.list();
		
		return uploadedFilesList;
	}


	
}
