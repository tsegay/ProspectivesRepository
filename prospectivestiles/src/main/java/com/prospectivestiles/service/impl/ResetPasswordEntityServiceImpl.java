package com.prospectivestiles.service.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import com.prospectivestiles.dao.ResetPasswordEntityDao;
import com.prospectivestiles.dao.UserEntityDao;
import com.prospectivestiles.domain.Employer;
import com.prospectivestiles.domain.Message;
import com.prospectivestiles.domain.Notification;
import com.prospectivestiles.domain.ResetPasswordEntity;
import com.prospectivestiles.domain.Role;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.NotificationService;
import com.prospectivestiles.service.ResetPasswordEntityService;
import com.prospectivestiles.service.UserEntityService;

@Service
@Transactional
public class ResetPasswordEntityServiceImpl implements ResetPasswordEntityService {

	private static final Logger log = LoggerFactory.getLogger(UserEntityServiceImpl.class);
//	public static final String EMAIL_SENDER = "test.prospectives@acct2day.org";
//	public static final String EMAIL_CC = "test.prospectives.backup@acct2day.org";
	
//	@Inject private UserEntityDao userEntityDao;
	
	@Inject 
	private UserEntityService userEntityService;
	@Inject 
	private ResetPasswordEntityDao resetPasswordEntityDao;
	@Inject
	private MailSender mailSender;
	@Inject
	private NotificationService notificationService;
	
	@Override
	public ResetPasswordEntity getResetPasswordEntity(long id) {
		return resetPasswordEntityDao.find(id);
	}

	@Override
	public List<ResetPasswordEntity> getAllResetPasswordEntitys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResetPasswordEntity getResetPasswordEntityByUserEntityId(
			long userEntityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createResetPasswordEntity(
			ResetPasswordEntity resetPasswordEntity) {
		resetPasswordEntityDao.create(resetPasswordEntity);
	}

	@Override
	public void updateResetPasswordEntity(
			ResetPasswordEntity resetPasswordEntity) {
		// TODO Auto-generated method stub
		ResetPasswordEntity resetPasswordEntityToUpdate = resetPasswordEntityDao.find(resetPasswordEntity.getId());
		
		resetPasswordEntityToUpdate.setPassword(resetPasswordEntity.getPassword());
		resetPasswordEntityToUpdate.setConfirmPassword(resetPasswordEntity.getConfirmPassword());
		
		resetPasswordEntityDao.update(resetPasswordEntityToUpdate);
	}

	@Override
	public void deleteResetPasswordEntity(
			ResetPasswordEntity resetPasswordEntity) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * First check if there is an account by the entered email address
	 * if no user exist with that email take no action
	 * if user exist, create resetKey, expiration date, then call createResetPasswordEntity() and sendEmail()
	 * 
	 * When a user inputs an invalid email in the reset password page, an email should not be sent out.
	 */
	@Override
	public void saveResetPasswordEntityAndSendEmail(ResetPasswordEntity resetPasswordEntity, String url){
		String email = null;
		/**
		 * This method returns list of email because email is not a unique field
		 * Make email a unique field using the method validateEmail in UserEntityServiceImpl
		 * 
		 * When a user inputs an invalid email in the reset password page, an email should not be sent out. 	
		 */
		List<UserEntity> users = userEntityService.findByEmail(resetPasswordEntity.getEmail());
//		if (users != null) {
		if (!users.isEmpty()) {
			System.out.println("########## inside !users.isEmpty()");
			for (UserEntity userEntity : users) {
				if (userEntity.getEmail() != null) {
					email = userEntity.getEmail();
				}
			}
			
			Date now = new Date();
			Date expirationDate = new Date();
			// Set the expiration date
			expirationDate.setTime(now.getTime() + 1 * 24 * 60 * 60 * 1000);
			
			
			String resetKey = randomString();
			
//			String emailBody = "Click here /" + resetKey;
			
		    
			resetPasswordEntity.setExpireDate(expirationDate);
			resetPasswordEntity.setResetKey(resetKey);
			
			createResetPasswordEntity(resetPasswordEntity);
			
			sendEmail(resetPasswordEntity, url);
			
		    
		} else {
			System.out.println("########## users.isEmpty(). no user found by email: " + resetPasswordEntity.getEmail());
		}
	}
	
	private void sendEmail(ResetPasswordEntity resetPasswordEntity,String url){
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(Message.EMAIL_SENDER);
		mail.setBcc(Message.EMAIL_BCC);
		mail.setTo(resetPasswordEntity.getEmail());
		mail.setSubject("Reset password");
		String emailBody = "You're receiving this e-mail because you requested a password reset "
				+ "for your online application user account at the American College of Commerce and Technology. "
				+ "Please go to the following page and choose a new password: " 
				+ url + "/" + resetPasswordEntity.getId() 
				+ "/" + resetPasswordEntity.getResetKey();
		mail.setText(emailBody);
		
		try {
			mailSender.send(mail);
		} catch (MailException e) {
			e.printStackTrace();
			System.out.println("########## Failed to send email");
		}
	}
	
	
	/*
	 * needs to go to the utils library
	 */
	private String randomString(){
		
//		int MAXIMUM_BIT_LENGTH = 100;
//		int RADIX = 32;
		
//		// cryptographically strong random number generator
////        SecureRandom random = new SecureRandom();
// 
//        // randomly generated BigInteger
//        BigInteger bigInteger = new BigInteger(MAXIMUM_BIT_LENGTH, random);
// 
//        // String representation of this BigInteger in the given radix.
//        String randomText = bigInteger.toString(RADIX);
//         
//        return randomText;
		
		
		SecureRandom ranGen = new SecureRandom();
	    byte[] aesKey = new byte[16]; // 16 bytes = 128 bits
	    ranGen.nextBytes(aesKey);
	    String rand = new Base64().encodeAsString(aesKey).replace('/','a');
	    return rand;
	}

	/**
	 * Resetting Password via ForgotPassword. When user forgot password.
	 */
	@Override
	@Transactional(readOnly = false)	
	public void updatePassword(ResetPasswordEntity origResetPasswordEntity,
			Errors errors) {
		
		validatePassword(origResetPasswordEntity.getPassword(), origResetPasswordEntity.getConfirmPassword(), errors);
		
		System.out.println("########## userEntity.getPassword(): " + origResetPasswordEntity.getPassword());
		System.out.println("########## userEntity.getConfirmPassword(): " + origResetPasswordEntity.getConfirmPassword());
		System.out.println("########## userEntity.getEmail(): " + origResetPasswordEntity.getEmail());
		
		boolean valid = !errors.hasErrors();
		
		if (valid) {
			/**
			 * MUST RETURN ONE USER ONLY; CHANGE METHOD TO VALIDATE USER IN UE SERVICE. one email in DB
			 */
			List<UserEntity> testuserEntities = userEntityService.findByEmail(origResetPasswordEntity.getEmail());
			Long userEntityId = testuserEntities.get(0).getId();
			userEntityService.updateUserEntityPassword(userEntityId, origResetPasswordEntity.getPassword());
//			Notification notification = new Notification("########## password resetted", testuserEntities.get(0).getFullName() + " reset password", testuserEntities.get(0));
//			notificationService.createNotification(notification);
		}
//		return valid;
		
	}
	
	/**
	 * Changing Password after logged in. If anyone want to change password.
	 */
	@Override
	public void changeMyPassword(Long userEntityId, ResetPasswordEntity origResetPasswordEntity, Errors errors) {
		
		validatePassword(origResetPasswordEntity.getPassword(), origResetPasswordEntity.getConfirmPassword(), errors);
		
		System.out.println("########## userEntity.getPassword(): " + origResetPasswordEntity.getPassword());
		System.out.println("########## userEntity.getConfirmPassword(): " + origResetPasswordEntity.getConfirmPassword());
		System.out.println("########## userEntity.getEmail(): " + origResetPasswordEntity.getEmail());
		
		boolean valid = !errors.hasErrors();
		
		if (valid) {
			userEntityService.updateUserEntityPassword(userEntityId, origResetPasswordEntity.getPassword());
		}
		
	}
	
	
	
	private void validatePassword(String password, String confirmPassword, Errors errors) {
		if (!password.equals(confirmPassword)) {
			log.info("Validation failed: password doesn't match confirmPassword");
			errors.rejectValue("password", "error.mismatch.resetPasswordEntity.password", new String[] { password }, null);
		}
	}



}
