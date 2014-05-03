package com.prospectivestiles.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "uploadedFiles")
@NamedQueries(
		@NamedQuery(name = "findUploadedFilesByUserEntityId", 
		query = "FROM UploadedFiles WHERE userEntity.id = :id")
		)
public class UploadedFiles {
	
	private Long id;
	private String fileName;
	private MultipartFile file;
	private byte[] fileToSaveInDb;
	private UserEntity userEntity;
	
	public UploadedFiles() {
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Transient
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Transient
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	@Lob
	public byte[] getFileToSaveInDb() {
		return fileToSaveInDb;
	}
	public void setFileToSaveInDb(byte[] fileToSaveInDb) {
		this.fileToSaveInDb = fileToSaveInDb;
	}
	@OneToOne
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
}
