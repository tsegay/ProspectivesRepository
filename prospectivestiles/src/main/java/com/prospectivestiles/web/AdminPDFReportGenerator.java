package com.prospectivestiles.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;
import com.prospectivestiles.domain.AssociatedUser;
import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AssociatedUserService;
import com.prospectivestiles.service.ChecklistService;
import com.prospectivestiles.service.EvaluationService;
import com.prospectivestiles.service.UserEntityService;

@Controller
public class AdminPDFReportGenerator {
	
	@Autowired
	private UserEntityService userEntityService;
	@Inject
	private EvaluationService evaluationService;
	@Inject
	private ChecklistService checklistService;
	@Inject
	private AssociatedUserService associatedUserService;
	
	private static String FILE = "path-to-file";
	private static Font h1Font = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font h2Font = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static Font normalBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font noramlFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	
	
	@RequestMapping(value = "/admin/report/{userEntityId}/evaluation", method = RequestMethod.GET)
	public void evaluationReport(@PathVariable("userEntityId") Long userEntityId,
			HttpServletResponse response, 
			HttpServletRequest request,
			Model model) {
		/**
		 * use if stmt to check if eval = null
		 */
		Evaluation eval = evaluationService.getEvaluationByUserEntityId(userEntityId);
		AssociatedUser associatedUser = associatedUserService.getAssociatedUserByUserEntityId(userEntityId);
		String admissionOfficerName = null;
		if (associatedUser != null) {
			
			if (associatedUser.getAdmissionOfficer() != null) {
				admissionOfficerName = associatedUser.getAdmissionOfficer().getFullName();
			} else {
				admissionOfficerName = associatedUser.getAdmissionOfficer().getFullName();
			}
			
		}
		
		Document document = new Document();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, out);
			document.open();

			addMetaData(document);
//			addContent(document);
			
			Paragraph paragraph1 = new Paragraph();
			
			paragraph1.add(new Paragraph("Admissions Counselor Evaluation Report", h1Font));
			paragraph1.setSpacingBefore(20);
			paragraph1.setSpacingAfter(20);
			
			Paragraph paragraph2 = new Paragraph();
			paragraph2.setSpacingAfter(15);
			paragraph2.add(new Paragraph("Student Full Name: " + userEntityService.getUserEntity(userEntityId).getFullName()));
			
			Paragraph paragraph2a = new Paragraph();
			paragraph2a.setSpacingAfter(15);
			paragraph2a.add(new Paragraph("Email Address: " + userEntityService.getUserEntity(userEntityId).getEmail()));
			
//			Paragraph paragraph2b = new Paragraph();
//			paragraph2b.setSpacingAfter(20);
//			paragraph2b.add(new Paragraph("Home Address: " + userEntityService.getUserEntity(userEntityId).getListOfAddresses()));
//			
			Paragraph paragraph2c = new Paragraph();
			paragraph2c.setSpacingAfter(15);
			paragraph2c.add(new Paragraph("Phone Number: " + userEntityService.getUserEntity(userEntityId).getCellPhone()));
			
			Paragraph paragraph2d = new Paragraph();
			paragraph2d.setSpacingAfter(15);
			paragraph2d.add(new Paragraph("Program of Study: " + userEntityService.getUserEntity(userEntityId).getProgramOfStudy().getName()));
			
			Paragraph paragraph5 = new Paragraph();
			paragraph5.setSpacingAfter(20);
			paragraph5.add(new Paragraph("Student Qualification: ", normalBoldFont));
			
			Paragraph paragraph6 = new Paragraph();
			paragraph6.setSpacingAfter(20);
			/**
			 * use if stmt to check if eval = null
			 */
			paragraph6.add(new Paragraph(eval.getStudentQualification()));
			
			Paragraph paragraph3 = new Paragraph();
			paragraph3.setSpacingAfter(20);
			paragraph3.add(new Paragraph("Admissions Couselor Report: ", normalBoldFont));
			
			Paragraph paragraph4 = new Paragraph();
			paragraph4.setSpacingAfter(20);
			paragraph4.add(new Paragraph(eval.getAdmnOfficerReport()));
			
			Paragraph paragraph7 = new Paragraph();
			paragraph7.setSpacingAfter(20);
			paragraph7.add(new Paragraph("Admissions Couselor: ", normalBoldFont));
			
			Paragraph paragraph8 = new Paragraph();
			paragraph8.setSpacingAfter(10);
			paragraph8.add(new Paragraph(admissionOfficerName));
			
			Paragraph paragraph9 = new Paragraph();
			paragraph9.setSpacingAfter(20);
			
			String dateAdmittedString = new String("");
			SimpleDateFormat formatDateAdmitted = new SimpleDateFormat("MM-dd-YYYY");
			dateAdmittedString = formatDateAdmitted.format(eval.getDateAdmitted());
			
			paragraph9.add(new Paragraph("Admitted on: " + dateAdmittedString));
			
			Paragraph paragraph1b = new Paragraph();
			paragraph1b.setSpacingAfter(20);
			
			Date now = new Date();
			String nowString = new String("");
			SimpleDateFormat format = new SimpleDateFormat("MM-dd-YYYY");
			nowString = format.format(now);
			
			paragraph1b.add(new Paragraph("Report generated by: " + admissionOfficerName + " on " + nowString, smallFont));
			
			
			document.add(paragraph1);
			document.add(paragraph2);
			document.add(paragraph2a);
//			document.add(paragraph2b);
			document.add(paragraph2c);
			document.add(paragraph2d);
			document.add(paragraph5);
			document.add(paragraph6);
			document.add(paragraph3);
			document.add(paragraph4);
			document.add(paragraph7);
			document.add(paragraph8);
			document.add(paragraph9);
			document.add(paragraph1b);

			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		try {
			FileCopyUtils.copy(out.toByteArray(), response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setHeader("Content-Type", "application/pdf");
		/*set the header Content-disposition to inline to render pdf inline instead of prompting a download window*/
		response.setHeader("Content-Disposition", "inline;filename=Test.pdf");
		try {
			response.flushBuffer();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/admin/report/{userEntityId}/missingDocuments", method = RequestMethod.GET)
	public void missingDocumentsReport(@PathVariable("userEntityId") Long userEntityId,
			HttpServletResponse response, 
			HttpServletRequest request,
			Model model) {
		
		Checklist checklist = checklistService.getChecklistByUserEntityId(userEntityId);
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
		if (checklist != null) {
		
			if (checklist.getF1Visa().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("F1Visa");
			}
			if (checklist.getBankStmt().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("BankStmt");
			}
			if (checklist.getI20().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("I20");
			}
			if (checklist.getPassport().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Passport");
			}
			if (checklist.getFinancialAffidavit().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("FinancialAffidavit");
			}
			if (checklist.getApplicationFee().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("ApplicationFee");
			}
			if (checklist.getTranscript().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Transcript");
			}
			if (checklist.getDiplome().equalsIgnoreCase("incomplete")) {
				missingDocuments.add("Diplome");
			}
		
		}
		
		Document document = new Document();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, out);
			document.open();

			addMetaData(document);
//			addContent(document);
			
			Paragraph paragraph1 = new Paragraph(50);
			paragraph1.setSpacingBefore(20);
			paragraph1.setSpacingAfter(20);
			
			paragraph1.add(new Paragraph("Missing Documents Report", h1Font));
			
			
			Paragraph paragraph3 = new Paragraph();
			paragraph3.setSpacingAfter(20);
			paragraph3.add(new Phrase("Dear " + userEntityService.getUserEntity(userEntityId).getFullName() + ":"));
			
			Paragraph paragraph4 = new Paragraph();
			paragraph4.setSpacingAfter(20);
			paragraph4.add(new Paragraph("The admission office is processing your application. The office has conducted initial review on your files to process you application but you have some missing documents. Please submit the missing documetns listed below. Upon completion of your required files the admission officer will evaluate your documents inorder to grant you admission."));
			
			Paragraph paragraph5 = new Paragraph();
			paragraph5.setSpacingAfter(20);
			paragraph5.add(new Paragraph("The documents missing from your file are:"));
			
			
			List orderedList = new List(List.ORDERED);
			orderedList.setIndentationLeft(30);
		    
			for (int i = 0; i < missingDocuments.size(); i++) {
				orderedList.add(new ListItem(missingDocuments.get(i)));
			}
			
			Paragraph paragraph6 = new Paragraph();
			paragraph6.setSpacingBefore(20);
//			paragraph6.setSpacingAfter(20);
			paragraph6.add(new Paragraph("Admissions Couselor: ", normalBoldFont));
			
			Paragraph paragraph7 = new Paragraph();
			paragraph7.setSpacingAfter(20);
			paragraph7.add(new Paragraph(admissionOfficerName));
			
			Paragraph paragraph2 = new Paragraph();
			paragraph2.setSpacingAfter(20);
//			paragraph2.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL));
			paragraph2.setFont(noramlFont);
			
			Date now = new Date();
			String nowString = new String("");
			SimpleDateFormat format = new SimpleDateFormat("MM-dd-YYYY");
			nowString = format.format(now);
			
			paragraph2.add(new Paragraph("Report generated by: " + currentAdmissionOfficer.getFullName() + " on " + nowString, smallFont));
			
			
			document.add(paragraph1);
			document.add(paragraph3);
			document.add(paragraph4);
			document.add(orderedList);
			document.add(paragraph6);
			document.add(paragraph7);
			document.add(paragraph2);

			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		try {
			FileCopyUtils.copy(out.toByteArray(), response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setHeader("Content-Type", "application/pdf");
		/*set the header Content-disposition to inline to render pdf inline instead of prompting a download window*/
		response.setHeader("Content-Disposition", "inline;filename=Test.pdf");
		try {
			response.flushBuffer();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/admin/report/{userEntityId}/acceptanceLetter", method = RequestMethod.GET)
	public void acceptanceLetterReport(@PathVariable("userEntityId") Long userEntityId,
			HttpServletResponse response, 
			HttpServletRequest request,
			Model model) {
		
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
		
		Document document = new Document();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, out);
			document.open();

			addMetaData(document);
//			addContent(document);
			
			Paragraph paragraph1 = new Paragraph();
			paragraph1.setSpacingAfter(20);
			
			Paragraph paragraph2 = new Paragraph();
			paragraph2.setSpacingAfter(20);
			paragraph2.add(new Paragraph("Acceptance Letter", h1Font));
			
			Paragraph paragraph3 = new Paragraph();
			paragraph3.setSpacingAfter(20);
			paragraph3.add(new Paragraph("Dear " + userEntityService.getUserEntity(userEntityId).getFullName() + ":"));
			
			Paragraph paragraph4 = new Paragraph();
			paragraph4.setSpacingAfter(20);
			paragraph4.add(new Paragraph("Congratulations!!!"));
			
			Paragraph paragraph5 = new Paragraph();
			paragraph5.setSpacingAfter(20);
			paragraph5.add(new Paragraph("The admission officer has approved your application for admission. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam imperdiet varius ligula vel interdum. Donec ut sapien nec eros pellentesque viverra vitae quis sem. Cras odio lorem, commodo mollis consequat nec, tempor vel sapien. Integer odio magna, luctus nec laoreet ut, rutrum vitae diam. Vivamus ullamcorper tortor in lectus accumsan, sed congue mi facilisis. Sed mi arcu, egestas vel turpis sed, feugiat laoreet nibh. Duis sit amet tincidunt urna. Nunc elementum elementum mauris quis feugiat. Phasellus pretium nunc sed ipsum adipiscing rhoncus. Vestibulum nec tortor sed ligula ornare lacinia. Nullam a turpis magna. Vivamus at interdum erat. Vestibulum bibendum lorem in feugiat ultricies. Fusce consequat eu risus sit amet vehicula. Maecenas auctor odio ipsum. Phasellus convallis est eu cursus lacinia."));
			
			Paragraph paragraph6 = new Paragraph();
			paragraph6.setSpacingAfter(20);
			paragraph6.add(new Paragraph("Admissions Counselor: ", normalBoldFont));
			
			Paragraph paragraph7 = new Paragraph();
			paragraph7.setSpacingAfter(10);
			paragraph7.add(new Paragraph(admissionOfficerName));
			
			Paragraph paragraph8 = new Paragraph();
			paragraph8.setSpacingAfter(20);
			
			String dateAdmittedString = new String("");
			SimpleDateFormat formatDateAdmitted = new SimpleDateFormat("MM-dd-YYYY");
			dateAdmittedString = formatDateAdmitted.format(evaluation.getDateAdmitted());
			
			paragraph8.add(new Paragraph("Admitted on: " + dateAdmittedString));
			
			
			document.add(paragraph1);
			document.add(paragraph2);
			document.add(paragraph3);
			document.add(paragraph4);
			document.add(paragraph5);
			document.add(paragraph6);
			document.add(paragraph7);
			document.add(paragraph8);

			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		try {
			FileCopyUtils.copy(out.toByteArray(), response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setHeader("Content-Type", "application/pdf");
		/*set the header Content-disposition to inline to render pdf inline instead of prompting a download window*/
		response.setHeader("Content-Disposition", "inline;filename=Test.pdf");
		try {
			response.flushBuffer();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * create the PDF file in memory
	 * PDF not saved in server
	 */
	@RequestMapping("/admin/report/createPDF")
	public void createMemPDF(HttpServletResponse response, HttpServletRequest request) {
		
		Document document = new Document();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, out);
			document.open();

			addMetaData(document);
			addContent(document);

			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		try {
			FileCopyUtils.copy(out.toByteArray(), response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setHeader("Content-Type", "application/pdf");
		/*set the header Content-disposition to inline to render pdf inline instead of prompting a download window*/
		response.setHeader("Content-Disposition", "inline;filename=Test.pdf");
		try {
			response.flushBuffer();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	// create the pdf file and save it in the server
	// in the second method download the file saved in the server
	/*@RequestMapping("/admin/report/createPDF")
	public String createPDF() throws DocumentException {
		Document document = new Document();
		// document.setPageSize(PageSize.A4);

		String rootPath = System.getProperty("catalina.home");
		// System.out.println("rootPath:" + rootPath);
		File dir = new File(rootPath + File.separator + "tmpFiles");
		if (!dir.exists())
			dir.mkdirs();
		String fileName = randomAlphaNum();
		// Create the file on server
		File serverFile = new File(dir.getAbsolutePath() + File.separator
				+ fileName);
		System.out.println("serverFile.toString(): " + serverFile.toString());

		try {
			PdfWriter.getInstance(document, new FileOutputStream(serverFile));
			document.open();

			addMetaData(document);
			addContent(document);

			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return "redirect:download/" + fileName;
		
	}*/
	
	/*@RequestMapping("/admin/report/download/{fileName}")
	public String downloadPDF(@PathVariable("fileName") String fileName, 
			HttpServletResponse response, HttpServletRequest request){
		String rootPath = System.getProperty("catalina.home");
		System.out.println("Downloading File");
		System.out.println("rootPath:" + rootPath);
		File dir = new File(rootPath + File.separator + "tmpFiles");
		response.setHeader("Content-Type", "application/pdf");
		response.setHeader("Content-Disposition", "inline;filename=Test.pdf");
		// copy file

		File serverFile = new File(dir.getAbsolutePath() + File.separator
				+ fileName);
		if (!serverFile.exists())
			return "welcome";
		FileInputStream in = null;
		try {
			in = new FileInputStream(serverFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (in != null)
				FileCopyUtils.copy(in, response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverFile.delete();
		return "welcome";
	}*/
	
	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	  // Reader
	  // under File -> Properties
	private static void addMetaData(Document document){
		document.addTitle("First Title");
		document.addSubject("First Subject");
		document.addKeywords("First");
		document.addCreator("Daniel");
		document.addAuthor("Daniel");
	}
	
	private static void addContent(Document document) throws DocumentException{
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph(""));
		
		preface.add(new Paragraph("Title of document", h1Font));
		preface.add(new Paragraph(""));
		
		preface.add(new Paragraph("Report generated by: author on: " + new Date(), normalBoldFont));
		preface.add(new Paragraph(""));
		
		
		Anchor anchor = new Anchor("this is a link", h1Font);
		anchor.setName("LINK");
		anchor.setReference("http://www.google.com");
		
		// 2nd param is the chapter number
		Chapter chapter = new Chapter(new Paragraph(anchor), 1);
		Section section = chapter.addSection(new Paragraph("section"));
		section.add(new Paragraph("hello"));
		
		document.add(preface);
		document.add(section);
		
	}
	
	private String randomAlphaNum(){
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		return userEntity;
	}

}
