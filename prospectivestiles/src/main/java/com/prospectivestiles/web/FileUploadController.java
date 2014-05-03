package com.prospectivestiles.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.prospectivestiles.domain.UploadedFiles;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.UploadedFilesService;

@Controller
public class FileUploadController {
	
	@Inject
	private UploadedFilesService uploadedFilesService;

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String displayForm() {
		return "file_upload_form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(
			@ModelAttribute("uploadedFile") UploadedFiles uploadedFile,
			BindingResult result, Model map) {

		InputStream inputStream = null;
		OutputStream outputStream = null;

		MultipartFile uFile = uploadedFile.getFile();
		String fileName = null;
		if (uFile != null) {
			fileName = uFile.getOriginalFilename();
		}
		if (result.hasErrors()) {
			return "file_upload_form";
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
                uploadedFile.setFileToSaveInDb(bytes);
//                uploadedFile.setUserEntity(getUserEntityFromSecurityContext());
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
		
		/////
		/*try {
			inputStream = uFile.getInputStream();

			File newFile = new File("C:\\Test\\files\\" + fileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		map.addAttribute("fileUploaded", fileName);
		return "file_upload_success";
	}
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity =  (UserEntity) auth.getPrincipal();
		return userEntity;
	}
	
}