package com.prospectivestiles.domain;

import java.io.Serializable;
import java.util.Date;

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
public class UploadedFiles implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ======================================
    // =             Attributes             =
    // ======================================
	
	private Long id;
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
	
	private Date dateCreated;
	
	// ======================================
    // =            Constructors            =
    // ======================================
	
	public UploadedFiles() {
	}
	
	// ======================================
    // =          Getters & Setters         =
    // ======================================
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

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
	
	@OneToOne
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


	public Date getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
}
