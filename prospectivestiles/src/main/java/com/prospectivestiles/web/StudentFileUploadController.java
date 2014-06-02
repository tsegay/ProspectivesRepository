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
import com.prospectivestiles.domain.UploadedFiles;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.UploadedFilesService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class StudentFileUploadController {
	
	@Autowired
	private UserEntityService userEntityService;
	
	@Inject
	private UploadedFilesService uploadedFilesService;
	
	// ======================================
	// =                          =
	// ======================================

	@RequestMapping(value = "/myAccount/files", method = RequestMethod.GET)
	public String getFiles(Model model) {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		/**
		 * load all files for a user
		 */
		model.addAttribute("files", uploadedFilesService.getAllUploadedFilesByUserEntityId(userEntity.getId()));
		
		/**
		 * The modelAttribute "file" for the form to add new file
		 */
		
		/**
		 * Do I really need to add the userEntity? 
		 * Maybe, I just need the Full Name of the user or userId
		 */
		model.addAttribute("userEntity", userEntity);
		
		return "uploadFiles";
	}
	
	@RequestMapping(value = "/myAccount/saveFile", method = RequestMethod.POST)
	public String saveUploadedFile(@ModelAttribute("uploadedFile") UploadedFiles uploadedFile,
			BindingResult result, Model model) {

		UserEntity userEntity = getUserEntityFromSecurityContext();
		
		InputStream inputStream = null;
		OutputStream outputStream = null;

		MultipartFile uFile = uploadedFile.getFile();
		System.out.println("##############: getContentType: " + uFile.getContentType());
		System.out.println("##############: getSize: " + uFile.getSize());
		System.out.println("##############: getOriginalFilename: " + uFile.getOriginalFilename());
		System.out.println("##############: getName: " + uFile.getName());
		
		
		String fileName = null;
		if (uFile != null) {
			fileName = uFile.getOriginalFilename();
		}
		if (result.hasErrors()) {
			model.addAttribute(userEntity);
			return "uploadFiles";
		}
		
		///////
		
		if (!uFile.isEmpty()) {
            try {
                byte[] bytes = uFile.getBytes();
 
                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                System.out.println("rootPath:" + rootPath);
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();
 
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
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
                
                uploadedFilesService.createUploadedFiles(uploadedFile);
                
                System.out.println("After uploadedFilesService.createUploadedFiles: " + uploadedFilesService.getAllUploadedFiles().toString());
 
               /* logger.info("Server File Location=" + serverFile.getAbsolutePath());*/
 
                System.out.println("You successfully uploaded file=" + fileName);
            } catch (Exception e) {
            	System.out.println("You failed to upload " + fileName + " => " + e.getMessage());
            }
        } else {
        	System.out.println("You failed to upload " + fileName + " because the file was empty.");
        }
		/**
		 * When file uploaded successfully user will be redirected to success page and
		 * that page will be loaded with whatever the model has here. 
		 * so success page can say 'fileName is uploaded...'
		 * I may not need it now, as i am reiecting to the getFiles, that will get all files from db and display it.
		 */
//		model.addAttribute("fileUploaded", fileName);
//		return "file_upload_success";
		return "redirect:/myAccount/files";
	}
	
	@RequestMapping(value = "/myAccount/files/{fileId}", method = RequestMethod.GET)
	public void downloadFile(@PathVariable("fileId") Long fileId, 
	    HttpServletResponse response) {
		
		UserEntity userEntity = getUserEntityFromSecurityContext();
		
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
	
	
	@RequestMapping(value = "/myAccount/files/{fileId}/delete", method = RequestMethod.POST)
	public String deleteFile(@PathVariable("fileId") Long fileId) 
		throws IOException {
		UserEntity userEntity = getUserEntityFromSecurityContext();
		uploadedFilesService.delete(getUploadedFileValidateUserEntityId(userEntity.getId(), fileId));
		return "redirect:/myAccount/files"; 
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
