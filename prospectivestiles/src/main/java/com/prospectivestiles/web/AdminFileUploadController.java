package com.prospectivestiles.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.EmergencyContact;
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.UploadedFiles;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.UploadedFilesService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminFileUploadController {
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private UploadedFilesService uploadedFilesService;
	
	// ======================================
	// =                          =
	// ======================================

	@RequestMapping(value = "/accounts/{userEntityId}/files", method = RequestMethod.GET)
	public String getFiles(@PathVariable("userEntityId") Long userEntityId,
			Model model) {
		
		/**
		 * load all files for a user
		 */
		model.addAttribute("files", uploadedFilesService.getAllUploadedFilesByUserEntityId(userEntityId));
		
		/**
		 * The modelAttribute "file" for the form to add new file
		 */
		
		/**
		 * Do I really need to add the userEntity? 
		 * Maybe, I just need the Full Name of the user or userId
		 */
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		model.addAttribute(userEntity);
//		model.addAttribute("userEntity", userEntityService.getUserEntity(userEntityId));
		
		return "uploadFiles";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/saveFile", method = RequestMethod.POST)
	public String saveUploadedFile(@PathVariable("userEntityId") Long userEntityId,
			@ModelAttribute("uploadedFile") UploadedFiles uploadedFile,
			BindingResult result, Model model) {

		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		String fileName = null;
//		String fileErrMsg = null;
		
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			String fileErrMsg = "##1 Valid files are pdf, jpeg, jpg, png and max file size is 5MB.";
            model.addAttribute("fileErrMsg",fileErrMsg);
			return "uploadFiles";
		}
		
		InputStream inputStream = null;
		OutputStream outputStream = null;

		MultipartFile uFile = uploadedFile.getFile();
		System.out.println("##############: getContentType: " + uFile.getContentType());
		System.out.println("##############: getSize: " + uFile.getSize());
		System.out.println("##############: getOriginalFilename: " + uFile.getOriginalFilename());
		System.out.println("##############: getName: " + uFile.getName());
		
		if (uFile != null) {
			
			fileName = uFile.getOriginalFilename();
			
			if (uFile.getContentType().equals("image/jpeg") || 
					uFile.getContentType().equals("image/jpg") ||
					uFile.getContentType().equals("image/png") ||
					uFile.getContentType().equals("image/gif") ||
					uFile.getContentType().equals("application/pdf")
					) {
//	          throw new RuntimeException("Only JPG images are accepted");
				System.out.println("Is accepted type.");
				
				if (!uFile.isEmpty()) {
					
					// 500 000 = 5MB
					if (uFile.getSize() > 500000) {
		                System.out.println("File Size:::" +uFile.getSize());
		                model.addAttribute(userEntity);
		                // to display error messages on page
		                String fileErrMsg = "###2 [> 5MB] Valid files are pdf, jpeg, jpg, png and max file size is 5MB.";
		                model.addAttribute(userEntity);
		                model.addAttribute("fileErrMsg",fileErrMsg);
		                return "uploadFiles";
					}
					
		            try {
		                byte[] bytes = uFile.getBytes();
		 
		                // Creating the directory to store file
		                // rootPath: "/Library/apache-tomcat-7.0.53"
		                String rootPath = System.getProperty("catalina.home");
		                System.out.println("rootPath:" + rootPath);
		                File dir = new File(rootPath + File.separator + "tmpFiles");
		                if (!dir.exists())
		                    dir.mkdirs();
		 
		                // Create the file on server
		                File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
		                // serverFile.toString(): Eg. "/Library/apache-tomcat-7.0.53/tmpFiles/favicon-2.jpg"
		                System.out.println("serverFile.toString(): " + serverFile.toString());
		                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
		                stream.write(bytes);
		                stream.close();
		                
		                //  TO SAVE IN DB
		                uploadedFile.setFileUploaded(bytes);
		                uploadedFile.setFileName(fileName);
		                uploadedFile.setContentType(uFile.getContentType());
		                uploadedFile.setSize(uFile.getSize());
		                uploadedFile.setUserEntity(userEntity);
		        		uploadedFile.setCreatedBy(currentAdmissionUser);
		                
		                uploadedFilesService.createUploadedFiles(uploadedFile);
		                
		                System.out.println("After uploadedFilesService.createUploadedFiles: " + uploadedFilesService.getAllUploadedFiles().toString());
		 
		               /* logger.info("Server File Location=" + serverFile.getAbsolutePath());*/
		 
		                System.out.println("You successfully uploaded file=" + fileName);
		            } catch (Exception e) {
		            	System.out.println("You failed to upload " + fileName + " => " + e.getMessage());
		            }
		        } else {
		        	String fileErrMsg = "###3 [empty file] Valid files are pdf, jpeg, jpg, png and max file size is 5MB.";
		        	model.addAttribute(userEntity);
		        	model.addAttribute("fileErrMsg",fileErrMsg);
		        	System.out.println("You failed to upload " + fileName + " because the file was empty.");
		        	return "uploadFiles";
		        }
			} else {
				String fileErrMsg = "###4 [not pdf, jpeg, jpg, png] Valid files are pdf, jpeg, jpg, png and max file size is 5MB.";
				model.addAttribute("fileErrMsg",fileErrMsg);
				model.addAttribute(userEntity);
				System.out.println("Is not accepted type.");
				return "uploadFiles";
			}
			
		}
		
		return "redirect:/accounts/{userEntityId}/files";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/files/{fileId}", method = RequestMethod.GET)
	public void downloadFile(@PathVariable("userEntityId") Long userEntityId,
	    @PathVariable("fileId") Long fileId, 
	    HttpServletResponse response) {
		
		UploadedFiles uploadedFile = uploadedFilesService.getUploadedFiles(fileId);
		
		// Reference: http://stackoverflow.com/questions/5673260/downloading-a-file-from-spring-controllers/5673356#5673356
		// https://github.com/LucasES/SpringUploadAndDownload/blob/master/src/main/java/com/ufc/npi/docmanager/controller/DocumentController.java
		
	    try {
	    	
	      // get your file as InputStream
//	      InputStream is = ...;
	      
	      //when you have response.getOutputStream(), you can write anything there.
	      OutputStream out = response.getOutputStream();
	      response.setContentType(uploadedFile.getContentType());
	      response.setHeader("Content-Disposition", "attachment; filename=\""+uploadedFile.getFileName()+"\"");
	      
	      // copy it to response's OutputStream
	      // using Apache's IOUtils; can also use Spring's FileCopyUtils
//	      IOUtils.copy(is, out);
	      
	      FileCopyUtils.copy(uploadedFile.getFileUploaded(), out);
	      
	      response.flushBuffer();
	    } catch (IOException ex) {
//	      log.info("Error writing file to output stream. Filename was '" + fileName + "'");
	      throw new RuntimeException("IOError writing file to output stream");
	    }
	    
	    // i will also try this one
//	    try{
//            String filePathToBeServed = //complete file name with path;
//            File fileToDownload = new File(filePathToBeServed);
//            InputStream inputStream = new FileInputStream(fileToDownload);
//            response.setContentType("application/force-download");
//            response.setHeader("Content-Disposition", "attachment; filename="+fileName+".txt"); 
//            IOUtils.copy(inputStream, response.getOutputStream());
//            response.flushBuffer();
//
//        }catch(Exception e){
//            LOGGER.debug("Request could not be completed at this moment. Please try again.");
//            e.printStackTrace();
//        }
	}
	
	/*
	 * Using a Modal to delete File.
	 * The delete form in the Modal calls this method
	 */
	@RequestMapping(value = "/accounts/{userEntityId}/files/{fileId}/delete", method = RequestMethod.GET)
	public String getDeleteFile(@PathVariable("userEntityId") Long userEntityId,
			@PathVariable("fileId") Long fileId, Model model) {
		
		UploadedFiles uploadedFile = getUploadedFileValidateUserEntityId(userEntityId, fileId);
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		model.addAttribute("file", uploadedFile);
		model.addAttribute(userEntity);
		
		return "deleteFile";
	}
	
	@RequestMapping(value = "/accounts/{userEntityId}/files/{fileId}/delete", method = RequestMethod.POST)
	public String deleteFile(@PathVariable("userEntityId") Long userEntityId,
	    @PathVariable("fileId") Long fileId) 
		throws IOException {
		uploadedFilesService.delete(getUploadedFileValidateUserEntityId(userEntityId, fileId));
		return "redirect:/accounts/{userEntityId}/files"; 
	}
	
	
	// ======================================
	// =                         =
	// ======================================
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity =  (UserEntity) auth.getPrincipal();
		return userEntity;
	}
	
	private UploadedFiles getUploadedFileValidateUserEntityId(Long userEntityId, Long fileId) {
		
		UploadedFiles uploadedFiles = uploadedFilesService.getUploadedFiles(fileId);
		Assert.isTrue(userEntityId.equals(uploadedFiles.getUserEntity().getId()), "uploadedFiles Id mismatch");
		return uploadedFiles;
	}

}

