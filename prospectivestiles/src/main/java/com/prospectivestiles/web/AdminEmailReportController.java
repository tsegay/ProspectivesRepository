package com.prospectivestiles.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.AddressType;
import com.prospectivestiles.domain.AssociatedUser;
import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.Message;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.AssociatedUserService;
import com.prospectivestiles.service.ChecklistService;
import com.prospectivestiles.service.EvaluationService;
import com.prospectivestiles.service.MessageService;
import com.prospectivestiles.service.NotificationService;
import com.prospectivestiles.service.UserEntityService;
import com.prospectivestiles.util.TableHeader;

@Controller
public class AdminEmailReportController {

	@Autowired
	private UserEntityService userEntityService;
	@Inject
	private MessageService messageService;
	@Inject
	private NotificationService notificationService;

	@Inject
	private EvaluationService evaluationService;
	@Inject
	private ChecklistService checklistService;
	@Inject
	private AssociatedUserService associatedUserService;
	@Inject
	private AddressService addressService;
	
	@Inject
	private JavaMailSender javaMailSender;
	
//	public static final String EMAIL_SENDER = "test.prospectives@acct2day.org";
//	public static final String EMAIL_CC = "test.prospectives.backup@acct2day.org";
	
	private static String FILE = "path-to-file";
	private static Font h1Font = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font h2Font = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font h3Font = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	private static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	private static Font normalBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font normalUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL | Font.UNDERLINE);
	private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	
	// ======================================
	// =               EMAIL missingDocuments PDF Form to Prospective Student        =
	// ======================================
	
	@RequestMapping(value = "/admin/report/{userEntityId}/missingDocuments/email", method = RequestMethod.POST)
	public String emailMissingDocuments(@PathVariable("userEntityId") Long userEntityId) {

		System.out.println("############# sendTestAttachment called");
		
		UserEntity student = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		
		/**
		 * When email is sent to the student, save it to the messages entity
		 * this will be displayed in the messages page, so users can track all email sent
		 */
		Message message = new Message();
		message.setAdmissionOfficer(currentAdmissionUser);
		message.setStudent(student);
		message.setSubject("missingDocuments");
		message.setText("Find attached the missingDocuments...");
		message.setVisible(true);
		message.setCreatedBy(currentAdmissionUser);
		messageService.createMessage(message);
		
		/**
		 * Get the missingDocuments pdf file in the server
		 * Then attach the file created in server to the mail
		 * TODO: delete file in server when no longer needed
		 */
		String fileName = createMissingDocumentsPDF(userEntityId);
				
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try{
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	 
//			helper.setFrom("daniel2advance@gmail.com");
			helper.setFrom(Message.EMAIL_SENDER);
			helper.setTo(student.getEmail());
			helper.setBcc(Message.EMAIL_BCC);
			helper.setSubject("missingDocuments");
			helper.setText("Find attached the missingDocuments...");
	 
			String rootPath = System.getProperty("catalina.home");
			System.out.println("rootPath:" + rootPath);
			File dir = new File(rootPath + File.separator + "tmpFiles");
			// Create the file on server
			File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
			System.out.println("serverFile.toString(): " + serverFile.toString());
			
			helper.addAttachment(serverFile.getName(), serverFile);
			
		     }catch (MessagingException e) {
		    	 throw new MailParseException(e);
		     }
		     
		javaMailSender.send(mimeMessage);
		
		return "redirect:/accounts/{userEntityId}/messages";
	}
	
	/**
	 * Create the missing documents PDF file in the server
	 * Then this file will be attached to the mail in the emailMissingDocuments()
	 * Same content as AdminPDFReportGenerator.missingDocumentsReport()
	 * @param userEntityId
	 * @return
	 */
	public String createMissingDocumentsPDF(Long userEntityId) {

		// ##################
		/**
		 * get the missing documents list for the applicant
		 */
		Evaluation evaluation = evaluationService.getEvaluationByUserEntityId(userEntityId);
//		Checklist checklist = checklistService.getChecklistByUserEntityId(userEntityId);
		ArrayList<String> missingDocuments = new ArrayList<String>();
		UserEntity currentAdmissionOfficer = getUserEntityFromSecurityContext();
		AssociatedUser associatedUser = associatedUserService.getAssociatedUserByUserEntityId(userEntityId);
		String admissionOfficerName = null;
		if (associatedUser != null) {
			if (associatedUser.getAdmissionOfficer() != null) {
				admissionOfficerName = associatedUser.getAdmissionOfficer().getFullName();
			} else {
				admissionOfficerName = associatedUser.getAdmissionOfficer().getFullName();
			}
		}
		
		/**
		 * if user has no checklist created, you can't generate missing documents report
		 */
		if (evaluation != null) {
			
			if (evaluation.getF1Visa().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("F1 Visa");
			}
			if (evaluation.getBankStmt().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("BankStmt");
			}
			if (evaluation.getI20().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("I20");
			}
			if (evaluation.getPassport().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Passport");
			}
			if (evaluation.getFinancialAffidavit().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Financial Affidavit");
			}
			if (evaluation.getApplicationFee().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Application Fee");
			}
			if (evaluation.getApplicationForm().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Application Form");
			}
			if (evaluation.getEnrollmentAgreement().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Enrollment Agreement");
			}
			if (evaluation.getGrievancePolicy().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Grievance Policy");
			}
			if (evaluation.getRecommendationLetter().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Recommendation Letter");
			}
			if (evaluation.getTranscript().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Transcript");
			}
			if (evaluation.getDiplome().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Diplome");
			}
			
		}
		
		// ##################
		
		Document document = new Document();

		String rootPath = System.getProperty("catalina.home");
//		System.out.println("rootPath:" + rootPath);
		File dir = new File(rootPath + File.separator + "tmpFiles");
		if (!dir.exists())
			dir.mkdirs();
		String fileName = random();
		/**
		 * To create the file on server - get its path
		 */
		File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName + ".pdf");
//		System.out.println("serverFile.toString(): " + serverFile.toString());

		
		try {
			/**
			 * Create the file on server
			 */
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(serverFile));
			/**
			 * add header and footer before document.open()
			 */
		    TableHeader event = new TableHeader();
	        writer.setPageEvent(event);
			document.open();
			/**
			 * addMetaData(document, title, subject);
			 */
			addMetaData(document, 
					"Missing Documents", 
					"American College of Commerce and Technology prospective student missing documentc report");
			
			// ##################
			/**
			 * Here comes the body part of the document
			 */
			Paragraph paragraph = new Paragraph(50);
			paragraph.setSpacingBefore(20);
			paragraph.setSpacingAfter(20);
			
			paragraph.add(new Paragraph("Missing Documents Report", h1Font));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			paragraph.add(new Phrase("Dear " + userEntityService.getUserEntity(userEntityId).getFullName() + ":"));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			paragraph.add(new Paragraph("The admission office is processing your application. The office has conducted initial review on your files to process you application but you have some missing documents. Please submit the missing documetns listed below. Upon completion of your required files the admission officer will evaluate your documents inorder to grant you admission."));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			paragraph.add(new Paragraph("The documents missing from your file are:"));
			document.add(paragraph);
			
			List orderedList = new List(List.ORDERED);
			orderedList.setIndentationLeft(30);
		    
			for (int i = 0; i < missingDocuments.size(); i++) {
				orderedList.add(new ListItem(missingDocuments.get(i)));
			}
			document.add(orderedList);
			
			paragraph = new Paragraph();
			paragraph.setSpacingBefore(20);
			paragraph.setSpacingAfter(20);
			paragraph.add(new Phrase("Admissions Couselor: ", normalBoldFont));
			paragraph.add(new Phrase(admissionOfficerName, normalUnderline));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			paragraph.setFont(normalFont);
			
			Date now = new Date();
			String nowString = new String("");
			SimpleDateFormat format = new SimpleDateFormat("MM-dd-YYYY");
			nowString = format.format(now);
			
			paragraph.add(new Paragraph("Report generated by: " + currentAdmissionOfficer.getFullName() + " on " + nowString, smallFont));
			document.add(paragraph);
			// ##################

			document.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return fileName + ".pdf";

	}
	
	// ======================================
	// =               EMAIL acceptanceLetter PDF Form to Prospective Student        =
	// ======================================
		
	@RequestMapping(value = "/admin/report/{userEntityId}/acceptanceLetter/email", method = RequestMethod.POST)
	public String emailAcceptanceLetter(@PathVariable("userEntityId") Long userEntityId) {

		System.out.println("############# emailAcceptanceLetter called");
		
		UserEntity student = userEntityService.getUserEntity(userEntityId);
		UserEntity currentAdmissionUser = getUserEntityFromSecurityContext();
		
		/**
		 * When email is sent to the student, save it to the messages entity
		 * this will be displayed in the messages page, so users can track all email sent
		 */
		Message message = new Message();
		message.setAdmissionOfficer(currentAdmissionUser);
		message.setStudent(student);
		message.setSubject("Acceptance Letter");
		message.setText("Find attached a copy of your acceptance letter to ACCT...");
		message.setVisible(true);
		message.setCreatedBy(currentAdmissionUser);
		messageService.createMessage(message);
		
		/**
		 * Get the AcceptanceLetter pdf file in the server
		 * Then attach the file created in server to the mail
		 * TODO: delete file in server when no longer needed
		 */
		String fileName = createAcceptanceLetterPDF(userEntityId);
				
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try{
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	 
//			helper.setFrom("daniel2advance@gmail.com");
			helper.setFrom(Message.EMAIL_SENDER);
			helper.setTo(student.getEmail());
			helper.setBcc(Message.EMAIL_BCC);
			helper.setSubject("Acceptance Letter");
			helper.setText("Find attached a copy of your acceptance letter to ACCT...");
	 
			String rootPath = System.getProperty("catalina.home");
			System.out.println("rootPath:" + rootPath);
			File dir = new File(rootPath + File.separator + "tmpFiles");
			// Create the file on server
			File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
			System.out.println("serverFile.toString(): " + serverFile.toString());
			
			helper.addAttachment(serverFile.getName(), serverFile);
			
		     }catch (MessagingException e) {
		    	 throw new MailParseException(e);
		     }
		     
		javaMailSender.send(mimeMessage);
		
		return "redirect:/accounts/{userEntityId}/messages";
	}
	
	/**
	 * Create the AcceptanceLetter PDF file in the server
	 * Then this file will be attached to the mail in the emailMissingDocuments()
	 * Same content as AdminPDFReportGenerator.missingDocumentsReport()
	 * @param userEntityId
	 * @return
	 */
	public String createAcceptanceLetterPDF(Long userEntityId) {

		
		// ##################
		/**
		 * Get the acceptance letter for the applicant
		 */
		Evaluation evaluation = evaluationService.getEvaluationByUserEntityId(userEntityId);
		AssociatedUser associatedUser = associatedUserService.getAssociatedUserByUserEntityId(userEntityId);
		String admissionOfficerName = null;
		if (associatedUser != null) {
			
			if (associatedUser.getAdmissionOfficer() != null) {
				admissionOfficerName = associatedUser.getAdmissionOfficer().getFullName();
			} else {
				admissionOfficerName = associatedUser.getAdmissionOfficer().getFullName();
			}
			
		}

		// ##################
		
		Document document = new Document();
		// document.setPageSize(PageSize.A4);

		String rootPath = System.getProperty("catalina.home");
		// System.out.println("rootPath:" + rootPath);
		File dir = new File(rootPath + File.separator + "tmpFiles");
		if (!dir.exists())
			dir.mkdirs();
		String fileName = random();
		/**
		 * To create the file on server - get its path
		 */
		File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName + ".pdf");
		System.out.println("serverFile.toString(): " + serverFile.toString());

		
		try {
			/**
			 * Create the file on server
			 */
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(serverFile));
			/**
			 * add header and footer before document.open()
			 */
		    TableHeader event = new TableHeader();
	        writer.setPageEvent(event);
			document.open();
			
			/**
			 * addMetaData(document, title, subject);
			 */
			addMetaData(document, "Acceptance Letter", 
					"American College of Commerce and Technology prospective student acceptance letter");
			
			// ##################
			/**
			 * Here comes the body part of the document
			 */
			Paragraph paragraph = new Paragraph(" ");
			paragraph.setSpacingAfter(35);
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			paragraph.add(new Paragraph("Acceptance Letter", h1Font));
			document.add(paragraph);
			
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			String dateAdmittedString = new String("");
			SimpleDateFormat formatDateAdmitted = new SimpleDateFormat("MMM d, yyyy");
			dateAdmittedString = formatDateAdmitted.format(evaluation.getDateAdmitted());
			paragraph.add(new Paragraph(dateAdmittedString));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.add(new Paragraph(userEntityService.getUserEntity(userEntityId).getFullName(), normalFont));
			document.add(paragraph);
			
			/**
			 * Display home address only
			 */
			paragraph = new Paragraph();
			Address address = addressService.getAddressByUserEntityIdAndAddressType(userEntityId, AddressType.HOME_ADDRESS);
			if (address != null) {
				if (address.getAddress1() != null) {
					paragraph.add(new Chunk(address.getAddress1(), normalFont));
				}
				if (address.getAddress2() != null) {
					paragraph.add(new Chunk(", " + address.getAddress2(), normalFont));
				}
				document.add(paragraph);
				paragraph = new Paragraph();
				if (address.getCity() != null) {
					paragraph.add(new Chunk(address.getCity(), normalFont) + ", ");
				}
				if (address.getState() != null) {
					paragraph.add(new Chunk(address.getState(), normalFont) + " ");
				}
				if (address.getZipcode() != null) {
					paragraph.add(new Chunk(address.getZipcode(), normalFont) + ", ");
				}
				if (address.getCountry() != null) {
					paragraph.add(new Chunk(address.getCountry(),  normalFont));
				}
				document.add(paragraph);
			}
			
			paragraph = new Paragraph();
			paragraph.setSpacingBefore(20);
			paragraph.setSpacingAfter(20);
			paragraph.add(new Paragraph("Dear " + userEntityService.getUserEntity(userEntityId).getFullName() + ","));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			paragraph.add(new Paragraph("Congratulations!!!"));
			document.add(paragraph);
			/**
			 * move the text to messages.properties
			 */
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(10);
			paragraph.add(new Chunk("I am pleased to inform you that your application for admission to "));
			paragraph.add(userEntityService.getUserEntity(userEntityId).getProgramOfStudy().getName());
			paragraph.add(new Chunk(" at the American College of Commerce and Technology has been approved. "));
			paragraph.add(userEntityService.getUserEntity(userEntityId).getTerm().getName());
			paragraph.add(new Chunk(" classes start on "));
			String startDateString = "";
			SimpleDateFormat startDateFormat = new SimpleDateFormat("EEE, MMM dd, YYYY");
			startDateString = startDateFormat.format(userEntityService.getUserEntity(userEntityId).getTerm().getStartDate());
			paragraph.add(startDateString);
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(10);
			paragraph.add(new Paragraph(
					"I would like to congratulate you on your decision to continue your education with the "
					+ "American College of Commerce and Technology. We are committed to providing each student "
					+ "a personalized, high quality education that will prepare you for success in your chosen "
					+ "profession and throughout your educational journey with the American College of Commerce "
					+ "and Technology."
					));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			paragraph.add(new Paragraph("Sincerely,"));
			document.add(paragraph);
			
			
			paragraph = new Paragraph();
			Chunk signatureUnderline = new Chunk("                                            ");
			signatureUnderline.setUnderline(1f, 1f);
//			signatureUnderline.setUnderline(0.1f, -2f);
			paragraph.add(signatureUnderline);
			document.add(paragraph);
			
			
			paragraph = new Paragraph();
			paragraph.add(new Paragraph("Maria Victoria Sunga, MBA"));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.add(new Paragraph("Executive Director"));
			document.add(paragraph);
			
			// ##################
			

			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return fileName + ".pdf";

	}
	
	// ======================================
	// =               MetaData         =
	// ======================================
	
	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(Document document, String title, String subject) {
		document.addTitle(title);
		document.addSubject(subject);
		document.addKeywords("ACCT, Application Form, International");
		document.addAuthor("ACCT");
		document.addCreator("Daniel Anenia");
	}
	
	public String random() {
		SecureRandom rand = new SecureRandom();
		return new BigInteger(130, rand).toString(32);
	}
	
	// ======================================
	// =              SecurityContext          =
	// ======================================
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		return userEntity;
	}
	
}
