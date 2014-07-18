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
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.AssociatedUser;
import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.Institute;
import com.prospectivestiles.domain.StandardTest;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.AssociatedUserService;
import com.prospectivestiles.service.ChecklistService;
import com.prospectivestiles.service.EvaluationService;
import com.prospectivestiles.service.HighSchoolService;
import com.prospectivestiles.service.InstituteService;
import com.prospectivestiles.service.StandardTestService;
import com.prospectivestiles.service.UserEntityService;
import com.prospectivestiles.util.TableHeader;

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
	
	@Inject
	private AddressService addressService;
	@Inject
	private HighSchoolService highSchoolService;
	@Inject
	private InstituteService instituteService;
	@Inject
	private StandardTestService standardTestService;

	
	private static String FILE = "path-to-file";
	private static Font h1Font = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font h2Font = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font h3Font = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static Font normalBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
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
			
			PdfWriter writer = PdfWriter.getInstance(document, out);
			// add header and footer before document.open()
		    TableHeader event = new TableHeader();
	        writer.setPageEvent(event);
		    /*open document*/
			document.open();
			
//			PdfWriter.getInstance(document, out);
//			document.open();

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
			PdfWriter writer = PdfWriter.getInstance(document, out);
			// add header and footer before document.open()
		    TableHeader event = new TableHeader();
	        writer.setPageEvent(event);
		    /*open document*/
			document.open();
			
//			PdfWriter.getInstance(document, out);
//			document.open();

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
			paragraph2.setFont(normalFont);
			
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
	
	@RequestMapping(value = "/admin/report/{userEntityId}/applicationForm", method = RequestMethod.GET)
	public void getApplicationForm(@PathVariable("userEntityId") Long userEntityId,
			HttpServletResponse response, 
			HttpServletRequest request,
			Model model) {
		/**
		 * use if stmt to check if an entity = null
		 */
		UserEntity userEntity = userEntityService.getUserEntity(userEntityId);
		java.util.List<Address> addresses = addressService.getAddressesByUserEntityId(userEntityId);
		java.util.List<HighSchool> highSchools = highSchoolService.getHighSchoolsByUserEntityId(userEntityId);
		java.util.List<Institute> institutes = instituteService.getInstitutesByUserEntityId(userEntityId);
		
		// ############################
		
        // step 1
//        Document document = new Document(PageSize.A4, 36, 36, 54, 54);
        // step 2
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
//        HeaderFooter event = new HeaderFooter();
//        writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
//        writer.setPageEvent(event);
        // step 3
//        document.open();
        // step 4
		
		// ############################
		
		// step 1
//        Document document = new Document(PageSize.A4, 36, 36, 54, 36);
        // step 2
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
//        TableHeader event = new TableHeader();
//        writer.setPageEvent(event);
        // step 3
//        document.open();
        // step 4
        
		// ############################
		
		Document document = new Document(PageSize.A4, 36, 36, 54, 54);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
//			PdfWriter.getInstance(document, out);
			PdfWriter writer = PdfWriter.getInstance(document, out);
			// add header and footer before document.open()
			/*HeaderFooter event = new HeaderFooter();
		    writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
		    writer.setPageEvent(event);*/
		    
		    TableHeader event = new TableHeader();
	        writer.setPageEvent(event);
		    
		    
		    /*open document*/
			document.open();

			addMetaData(document);
			
			Paragraph paragraph = new Paragraph();
			paragraph.add(new Paragraph("APPLICATION FOR ADMISSION", h1Font));
			paragraph.setSpacingBefore(20);
			paragraph.setSpacingAfter(20);
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraph);
			
			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);

			// row 1, cell 1 - span 3
			PdfPCell cell = new PdfPCell(new Phrase("PERSONAL DATA"));
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(8);
			cell.setBorderWidthBottom(2f);
//	        cell.setBackgroundColor(BaseColor.GRAY);
	        cell.setGrayFill(0.75f);
			table.addCell(cell);
			
			// row 2, cell 1
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Last Name: ", normalBoldFont));
			paragraph.add(new Phrase(userEntity.getLastName(), smallFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			cell.setBorder(Rectangle.LEFT);
			table.addCell(cell);
			// row 2, cell 2
			paragraph = new Paragraph();
			paragraph.add(new Phrase("First Name: ", normalBoldFont));
			paragraph.add(new Phrase(userEntity.getFirstName(), smallFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			// row 2, cell 3
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Middle Name: ", normalBoldFont));
			paragraph.add(new Phrase(userEntity.getMiddleName(), smallFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			cell.setBorder(Rectangle.RIGHT);
			table.addCell(cell);
			
			// row 2n, cell 1 - span 3
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Applicant is: ", normalBoldFont));
			if(userEntity.isInternational() == true){
				paragraph.add(new Phrase(" an INTERNATIONAL STUDENT", normalFont));
			} else {
				paragraph.add(new Phrase(" a DOMESTIC STUDENT", normalFont));
			}
			cell = new PdfPCell(paragraph);
			cell.setColspan(3);
			cell.setPadding(8);
			table.addCell(cell);
			
			// row 3, cell 1
			/*Format DOB to "MM-dd-YYYY"*/
			String dobString = new String("");
			if (userEntity.getDob() != null) {
				SimpleDateFormat formatDob = new SimpleDateFormat("MM-dd-YYYY");
				dobString = formatDob.format(userEntity.getDob());
			} 
			
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Date of Birth: ", normalBoldFont));
			paragraph.add(new Phrase(dobString, smallFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			// row 3, cell 2
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Country of Birth: ", normalBoldFont));
			paragraph.add(new Phrase(userEntity.getCountryOfBirth(), smallFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			// row 3, cell 3
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Country of Citizenship: ", normalBoldFont));
			paragraph.add(new Phrase(userEntity.getCitizenship(), smallFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			// row 4, cell 1
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Gender: ", normalBoldFont));
			paragraph.add(new Phrase(userEntity.getGender(), smallFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			// row 4, cell 2
			paragraph = new Paragraph();
			paragraph.add(new Phrase("SSN: ", normalBoldFont));
			paragraph.add(new Phrase(userEntity.getSsn(), smallFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			// row 4, cell 3
			paragraph = new Paragraph();
			paragraph.add(new Phrase("SEVIS number: ", normalBoldFont));
			paragraph.add(new Phrase(userEntity.getSevisNumber(), smallFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			// row 5, cell 1
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Email: ", normalBoldFont));
			paragraph.add(new Phrase(userEntity.getEmail(), smallFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			// row 5, cell 2
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Home Phone: ", normalBoldFont));
			paragraph.add(new Phrase(userEntity.getHomePhone(), smallFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			// row 5, cell 3
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Cell Number: ", normalBoldFont));
			paragraph.add(new Phrase(userEntity.getCellPhone(), smallFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			
			
			// row Addresses
			if (addresses != null) {
				for (Address address : addresses) {
					
					// Address row 1, cell 1 -2
					Paragraph p = new Paragraph();
					p.add(new Phrase(address.getAddressType() + ": ", normalBoldFont));
					p.add(new Phrase(address.getAddress1() + ", " + address.getAddress2(), smallFont));
					cell = new PdfPCell(p);
					
					cell.setColspan(2);
					cell.setPadding(8);
					cell.setBorder(Rectangle.LEFT);
					table.addCell(cell);
					// Address row 1, cell 3
					paragraph = new Paragraph();
					paragraph.add(new Phrase("City: ", normalBoldFont));
					paragraph.add(new Phrase(address.getCity(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setBorder(Rectangle.RIGHT);
					cell.setPadding(8);
					table.addCell(cell);
					
					// Address row 2, cell 1
					paragraph = new Paragraph();
					paragraph.add(new Phrase("State: ", normalBoldFont));
					paragraph.add(new Phrase(address.getState(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
					cell.setPadding(8);
					table.addCell(cell);
					// Address row 2, cell 2
					paragraph = new Paragraph();
					paragraph.add(new Phrase("Zip Code: ", normalBoldFont));
					paragraph.add(new Phrase(address.getZipcode(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setBorder(Rectangle.BOTTOM);
					cell.setPadding(8);
					table.addCell(cell);
					/*Rectangle.BOTTOM=2,Rectangle.TOP=1,Rectangle.RIGHT=8,Rectangle.LEFT=4 
					so for right and bottom,try cell.setBorder(10);*/
					// Address row 2, cell 3
					paragraph = new Paragraph();
					paragraph.add(new Phrase("Country: ", normalBoldFont));
					paragraph.add(new Phrase(address.getCountry(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setPadding(8);
//					cell.setBorder(10);
					cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
				}
				
			}
			
			// row pre6, cell 1 - span 3
			cell = new PdfPCell(new Phrase("TERM AND PROGRAM OF STUDY"));
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(8);
			cell.setBorderWidthBottom(2f);
	        cell.setGrayFill(0.75f);
			table.addCell(cell);
						
			// row 6, cell 1 - span 3
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Term applying for: ", normalBoldFont));
			if (userEntity.getTerm() != null) {
				paragraph.add(new Phrase(userEntity.getTerm().getName(), smallFont));
			}
			cell = new PdfPCell(paragraph);
			cell.setColspan(3);
			cell.setPadding(8);
			table.addCell(cell);
			
			// row 7, cell 1 - span 3
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Program of Study: ", normalBoldFont));
			if (userEntity.getProgramOfStudy() != null) {
				paragraph.add(new Phrase(userEntity.getProgramOfStudy().getName(), smallFont));
			}
			cell = new PdfPCell(paragraph);
			cell.setColspan(3);
			cell.setPadding(8);
			table.addCell(cell);
			
			// row preHighSchool, cell 1 - span 3
			cell = new PdfPCell(new Phrase("UNDERGRADUATE EDUCATIONAL BACKGROUND"));
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(8);
			cell.setBorderWidthBottom(2f);
	        cell.setGrayFill(0.75f);
			table.addCell(cell);
			
			// row HighSchool
			if (highSchools != null) {
				for (HighSchool highSchool : highSchools) {
					
					// HighSchool row 1, cell 1 -2
					Paragraph p = new Paragraph();
					p.add(new Phrase("Name of High School: ", normalBoldFont));
					p.add(new Phrase(highSchool.getName(), smallFont));
					cell = new PdfPCell(p);
					cell.setColspan(2);
					cell.setPadding(8);
					cell.setBorder(Rectangle.LEFT);
					table.addCell(cell);
					
					// HighSchool row 1, cell 3
					paragraph = new Paragraph();
					paragraph.add(new Phrase("Graduation/GED Year: ", normalBoldFont));
					if (highSchool.getDiplomeAwardedDate() != null) {
						/*Format getDiplomeAwardedDate to "MM-dd-YYYY"*/
						String diplomeAwardedDateString = new String("");
						SimpleDateFormat formatDiplomeAwardedDate = new SimpleDateFormat("MM-dd-YYYY");
						diplomeAwardedDateString = formatDiplomeAwardedDate.format(highSchool.getDiplomeAwardedDate());
						
						paragraph.add(new Phrase(diplomeAwardedDateString, smallFont));
					} else {
						if (highSchool.getgEDAwardedDate() != null) {
							/*Format getDiplomeAwardedDate to "MM-dd-YYYY"*/
							String gEDAwardedDateString = new String("");
							SimpleDateFormat formatgEDAwardedDate = new SimpleDateFormat("MM-dd-YYYY");
							gEDAwardedDateString = formatgEDAwardedDate.format(highSchool.getgEDAwardedDate());
							
							paragraph.add(new Phrase(gEDAwardedDateString, smallFont));
						}
					}
					cell = new PdfPCell(paragraph);
					cell.setBorder(Rectangle.RIGHT);
					cell.setPadding(8);
					table.addCell(cell);
					
					// HighSchool row 2, cell 1
					paragraph = new Paragraph();
					paragraph.add(new Phrase("City: ", normalBoldFont));
					paragraph.add(new Phrase(highSchool.getCity(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
					cell.setPadding(8);
					table.addCell(cell);
					// HighSchool row 2, cell 2
					paragraph = new Paragraph();
					paragraph.add(new Phrase("State: ", normalBoldFont));
					paragraph.add(new Phrase(highSchool.getState(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setBorder(Rectangle.BOTTOM);
					cell.setPadding(8);
					table.addCell(cell);
					// HighSchool row 2, cell 3
					paragraph = new Paragraph();
					paragraph.add(new Phrase("Country: ", normalBoldFont));
					paragraph.add(new Phrase(highSchool.getCountry(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setPadding(8);
					cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
				}
			}
			
			// row preHighSchool, cell 1 - span 3
			cell = new PdfPCell(new Phrase("GRADUATE EDUCATIONAL BACKGROUND"));
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(8);
			cell.setBorderWidthBottom(2f);
	        cell.setGrayFill(0.75f);
			table.addCell(cell);
			
			// row Institute
			if (institutes != null) {
				for (Institute institute : institutes) {
					
					// HighSchool row 1, cell 1 -3
					Paragraph p = new Paragraph();
					p.add(new Phrase("Name of University/Institute: ", normalBoldFont));
					p.add(new Phrase(institute.getName(), smallFont));
					cell = new PdfPCell(p);
					cell.setColspan(3);
					cell.setPadding(8);
					cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
					table.addCell(cell);
					
					// Institute row 2, cell 1
					paragraph = new Paragraph();
					paragraph.add(new Phrase("Level of Study: ", normalBoldFont));
					paragraph.add(new Phrase(institute.getLevelOfStudy(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setBorder(Rectangle.LEFT);
					cell.setPadding(8);
					table.addCell(cell);
					// Institute row 2, cell 2
					paragraph = new Paragraph();
					paragraph.add(new Phrase("Program of Study: ", normalBoldFont));
					paragraph.add(new Phrase(institute.getProgramOfStudy(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setBorder(Rectangle.NO_BORDER);
					cell.setPadding(8);
					table.addCell(cell);
					// Institute row 2, cell 3
					paragraph = new Paragraph();
					paragraph.add(new Phrase("Graduation Year: ", normalBoldFont));
					if (institute.getGraduationDate() != null) {
						/*Format getDiplomeAwardedDate to "MM-dd-YYYY"*/
						String graduationDateString = new String("");
						SimpleDateFormat formatGraduationDate = new SimpleDateFormat("MM-dd-YYYY");
						graduationDateString = formatGraduationDate.format(institute.getGraduationDate());
						
						paragraph.add(new Phrase(graduationDateString, smallFont));
					}
					cell = new PdfPCell(paragraph);
					cell.setPadding(8);
					cell.setBorder(Rectangle.RIGHT);
					table.addCell(cell);
					
					// Institute row 3, cell 1
					paragraph = new Paragraph();
					paragraph.add(new Phrase("City: ", normalBoldFont));
					paragraph.add(new Phrase(institute.getCity(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
					cell.setPadding(8);
					table.addCell(cell);
					// Institute row 3, cell 2
					paragraph = new Paragraph();
					paragraph.add(new Phrase("State: ", normalBoldFont));
					paragraph.add(new Phrase(institute.getState(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setBorder(Rectangle.BOTTOM);
					cell.setPadding(8);
					table.addCell(cell);
					// Institute row 3, cell 3
					paragraph = new Paragraph();
					paragraph.add(new Phrase("Country: ", normalBoldFont));
					paragraph.add(new Phrase(institute.getCountry(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setPadding(8);
					cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
				}
			}
			
						
			document.add(table);
			
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
	
	
//    /**
//     * Inner class to add a table as header.
//     */
//    class TableHeader extends PdfPageEventHelper {
//        /** The header text. */
//        String header = "American College of Commerce and Technology";
//        /** The template with the total number of pages. */
//        PdfTemplate total;
// 
//        /**
//         * Allows us to change the content of the header.
//         * @param header The new header String
//         */
//        public void setHeader(String header) {
//            this.header = header;
//        }
// 
//        /**
//         * Creates the PdfTemplate that will hold the total number of pages.
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
//         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//         */
//        public void onOpenDocument(PdfWriter writer, Document document) {
//            total = writer.getDirectContent().createTemplate(30, 16);
//        }
// 
//        /**
//         * Adds a header to every page
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
//         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//         */
//        public void onEndPage(PdfWriter writer, Document document) {
//            PdfPTable table = new PdfPTable(2);
//            try {
////                table.setWidths(new int[]{24, 24, 2});
//                table.setWidths(new int[]{25, 25});
//                table.setTotalWidth(527);
//                table.setLockedWidth(true);
//                
//                table.getDefaultCell().setFixedHeight(20);
//                table.getDefaultCell().setBorder(Rectangle.BOTTOM);
//                table.addCell(header);
//                
//                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
//                table.getDefaultCell().setBorder(Rectangle.BOTTOM);
//                table.addCell(new Phrase("www.acct2day.org"));
//                
////                PdfPCell cell = new PdfPCell(Image.getInstance(total));
////                PdfPCell cell = new PdfPCell(new Phrase("www.acct2day.org"));
////                cell.setBorder(Rectangle.BOTTOM);
////                table.addCell(cell);
//                
//                table.writeSelectedRows(0, -1, 34, 813, writer.getDirectContent());
//            }
//            catch(DocumentException de) {
//                throw new ExceptionConverter(de);
//            }
//            
//            PdfPTable table2 = new PdfPTable(3);
//            try {
//            	table2.setWidths(new int[]{24, 24, 2});
//            	table2.setTotalWidth(527);
//            	table2.setLockedWidth(true);
//            	
//            	table2.getDefaultCell().setFixedHeight(20);
//            	table2.getDefaultCell().setBorder(Rectangle.TOP);
//            	table2.addCell("Printed: " + new Date());
//            	
//            	table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
//            	table2.addCell(String.format("Page %d of", writer.getPageNumber()));
//            	
//            	PdfPCell cell = new PdfPCell(Image.getInstance(total));
//            	cell.setBorder(Rectangle.TOP);
//            	table2.addCell(cell);
//            	
//            	table2.writeSelectedRows(0, -1, 34, 50, writer.getDirectContent());
//            }
//            catch(DocumentException de) {
//            	throw new ExceptionConverter(de);
//            }
//        }
// 
//        /**
//         * Fills out the total number of pages before the document is closed.
//         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
//         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//         */
//        public void onCloseDocument(PdfWriter writer, Document document) {
//            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
//                    new Phrase(String.valueOf(writer.getPageNumber() - 1)),
//                    2, 2, 0);
//        }
//    }
    
	
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
