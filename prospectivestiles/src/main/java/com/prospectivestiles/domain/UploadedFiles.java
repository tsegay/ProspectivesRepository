package com.prospectivestiles.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
@NamedQueries(
		@NamedQuery(name = "findUploadedFilesByUserEntityId", 
		query = "FROM UploadedFiles WHERE userEntity.id = :id")
		)
public class UploadedFiles extends BaseEntity implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -5094090988464565522L;
	/**
	 * 
	 */

	// ======================================
    // =             Attributes             =
    // ======================================
	
	// file name, actual name of the file
	private String fileName;
	// file size
	private Long size;
	private String contentType;
	
	// file description provided by user
	private String description;
	
	// 
	private MultipartFile file;
	// content - file To  Save In Db
	private byte[] fileUploaded;
	
	private UserEntity userEntity;
	
	
	// ======================================
    // =            Constructors            =
    // ======================================
	
	public UploadedFiles() {
	}
	
	// ======================================
    // =          Getters & Setters         =
    // ======================================
	

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}

	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	@Lob
	public byte[] getFileUploaded() {
		return fileUploaded;
	}
	public void setFileUploaded(byte[] fileUploaded) {
		this.fileUploaded = fileUploaded;
	}
	
	@ManyToOne
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


}
