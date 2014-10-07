package com.prospectivestiles.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.AddressType;
import com.prospectivestiles.domain.AgreementName;
import com.prospectivestiles.domain.AssociatedUser;
import com.prospectivestiles.domain.Checklist;
import com.prospectivestiles.domain.Evaluation;
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.Institute;
import com.prospectivestiles.domain.StudentAgreement;
import com.prospectivestiles.domain.Term;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.AssociatedUserService;
import com.prospectivestiles.service.ChecklistService;
import com.prospectivestiles.service.EvaluationService;
import com.prospectivestiles.service.HighSchoolService;
import com.prospectivestiles.service.InstituteService;
import com.prospectivestiles.service.StandardTestService;
import com.prospectivestiles.service.StudentAgreementService;
import com.prospectivestiles.service.TermService;
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
	@Inject
	private StudentAgreementService studentAgreementService;
	@Inject
	private TermService termService;
	
	private static String FILE = "path-to-file";
	private static Font h1Font = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font h2Font = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font h3Font = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	private static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	private static Font normalBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font normalUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL | Font.UNDERLINE);
	private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	
	
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
			
			addMetaData(document);
			
			Paragraph paragraph = new Paragraph(" ");
			paragraph.setSpacingAfter(20);
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.add(new Paragraph("Admissions Counselor Applicant's Evaluation Report", h1Font));
			paragraph.setSpacingAfter(20);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);
			
			/**
			 * table no border to display student info at the upper part of the page
			 */
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(75);
			table.setWidths(new int[]{1, 2});
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cell = new PdfPCell();

			createNoBorderTableRow(table, "Student Name: ", userEntityService.getUserEntity(userEntityId).getFullName());
			
			createNoBorderTableRow(table, "Email Address: ", userEntityService.getUserEntity(userEntityId).getEmail());
			
			createNoBorderTableRow(table, "Phone Number: ", userEntityService.getUserEntity(userEntityId).getCellPhone());
			
			String termName;
			if (userEntityService.getUserEntity(userEntityId).getTerm() != null) {
				termName = userEntityService.getUserEntity(userEntityId).getTerm().getName();
			} else {
				termName = " XXXXXXXXXXX ";
			}
			createNoBorderTableRow(table, "Quarter: ", termName);
			
			String programName;
			if (userEntityService.getUserEntity(userEntityId).getProgramOfStudy() != null) {
				programName = userEntityService.getUserEntity(userEntityId).getProgramOfStudy().getName();
			} else {
				programName = " XXXXXXXXXXX ";
			}
			createNoBorderTableRow(table, "Program of Study: ", programName);
			
			String dobString = new String("");
			if (userEntityService.getUserEntity(userEntityId).getDob() != null) {
				SimpleDateFormat formatDob = new SimpleDateFormat("MM-dd-YYYY");
				dobString = formatDob.format(userEntityService.getUserEntity(userEntityId).getDob());
			} 
			createNoBorderTableRow(table, "Date of Birth: ", dobString);
			
			String studentStatus;
			if (userEntityService.getUserEntity(userEntityId).isInternational()) {
				studentStatus = "International";
			} else {
				studentStatus = "Domestic";
			}
			createNoBorderTableRow(table, "Student Status: ", studentStatus);
			
			String countryName = null;
			if (userEntityService.getUserEntity(userEntityId).getCitizenship() != null) {
				countryName = userEntityService.getUserEntity(userEntityId).getCitizenship().getName();
			}
			createNoBorderTableRow(table, "Country of Citizenship: ", countryName);
			
			
			/**
			 * Display home address only
			 */
			paragraph = new Paragraph();
			Address address = addressService.getAddressByUserEntityIdAndAddressType(userEntityId, AddressType.HOME_ADDRESS);
			if (address != null) {
				if (address.getAddress1() != null) {
					paragraph.add(new Chunk(address.getAddress1(), normalFont) + ", ");
				}
				if (address.getAddress2() != null) {
					paragraph.add(new Chunk(address.getAddress2(), normalFont) + ", ");
				}
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
					paragraph.add(new Chunk(address.getCountry().getName(),  normalFont));
				}
			}
			// row 1, cell 1
			cell = new PdfPCell(new Phrase("Home Address: ", normalFont));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			// row 1, cell 2
			cell = new PdfPCell(paragraph);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
						
			
			document.add(table);
			
			
			/**
			 * For line spacing
			 */
			paragraph = new Paragraph(" ");
			paragraph.setSpacingAfter(10);
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			paragraph.add(new Chunk("Admission Granted: ", normalBoldFont));
			if (userEntityService.getUserEntity(userEntityId).getAccountState().equalsIgnoreCase("admitted")) {
				paragraph.add(new Chunk("YES", normalFont));
			} else {
				paragraph.add(new Chunk("NO", normalFont));
			}
			document.add(paragraph);
			
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			paragraph.add(new Paragraph("Admissions Couselor Report: ", normalBoldFont));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			paragraph.add(new Paragraph(eval.getAdmnOfficerReport()));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			paragraph.add(new Phrase("Admissions Couselor: ", normalBoldFont));
			paragraph.add(new Phrase(admissionOfficerName, normalUnderline));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			
			String dateAdmittedString = new String("");
			SimpleDateFormat formatDateAdmitted = new SimpleDateFormat("MM-dd-YYYY");
			if (eval.getDateAdmitted() != null) {
				dateAdmittedString = formatDateAdmitted.format(eval.getDateAdmitted());
			} 
			
			paragraph.add(new Phrase("Date: ", normalBoldFont));
			paragraph.add(new Phrase(dateAdmittedString, normalUnderline));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(20);
			
			Date now = new Date();
			String nowString = new String("");
			SimpleDateFormat format = new SimpleDateFormat("MM-dd-YYYY");
			nowString = format.format(now);
			
			paragraph.add(new Paragraph("Report generated by: " + admissionOfficerName + " on " + nowString, smallFont));
			document.add(paragraph);
			
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
		/**
		 * set the header Content-disposition to inline to render pdf inline instead of prompting a download window
		 */
		response.setHeader("Content-Disposition", "inline;filename=Test.pdf");
		try {
			response.flushBuffer();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/admin/report/{userEntityId}/checklist", method = RequestMethod.GET)
	public void checklistReport(@PathVariable("userEntityId") Long userEntityId,
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
			
			addMetaData(document);
			
			Paragraph paragraph = new Paragraph(" ");
			paragraph.setSpacingAfter(20);
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.add(new Paragraph("Applicant's Documents Checklist", h1Font));
			paragraph.setSpacingAfter(20);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);
			
			/**
			 * table no border to display student info at the upper part of the page
			 */
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(75);
			table.setWidths(new int[]{1, 2});
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell cell = new PdfPCell();

			createNoBorderTableRow(table, "Student Name: ", userEntityService.getUserEntity(userEntityId).getFullName());
			
			createNoBorderTableRow(table, "Email Address: ", userEntityService.getUserEntity(userEntityId).getEmail());
			
			createNoBorderTableRow(table, "Phone Number: ", userEntityService.getUserEntity(userEntityId).getCellPhone());
			
			String termName;
			if (userEntityService.getUserEntity(userEntityId).getTerm() != null) {
				termName = userEntityService.getUserEntity(userEntityId).getTerm().getName();
			} else {
				termName = " XXXXXXXXXXX ";
			}
			createNoBorderTableRow(table, "Quarter: ", termName);
			
			String programName;
			if (userEntityService.getUserEntity(userEntityId).getProgramOfStudy() != null) {
				programName = userEntityService.getUserEntity(userEntityId).getProgramOfStudy().getName();
			} else {
				programName = " XXXXXXXXXXX ";
			}
			createNoBorderTableRow(table, "Program of Study: ", programName);
			
			String dobString = new String("");
			if (userEntityService.getUserEntity(userEntityId).getDob() != null) {
				SimpleDateFormat formatDob = new SimpleDateFormat("MM-dd-YYYY");
				dobString = formatDob.format(userEntityService.getUserEntity(userEntityId).getDob());
			} 
			createNoBorderTableRow(table, "Date of Birth: ", dobString);
			
			String studentStatus;
			if (userEntityService.getUserEntity(userEntityId).isInternational()) {
				studentStatus = "International";
			} else {
				studentStatus = "Domestic";
			}
			createNoBorderTableRow(table, "Student Status: ", studentStatus);
			
			String countryName = null;
			if (userEntityService.getUserEntity(userEntityId).getCitizenship() != null) {
				countryName = userEntityService.getUserEntity(userEntityId).getCitizenship().getName();
			}
			createNoBorderTableRow(table, "Country of Citizenship: ", countryName);
			
			/**
			 * Display home address only
			 */
			paragraph = new Paragraph();
			Address address = addressService.getAddressByUserEntityIdAndAddressType(userEntityId, AddressType.HOME_ADDRESS);
			if (address != null) {
				if (address.getAddress1() != null) {
					paragraph.add(new Chunk(address.getAddress1(), normalFont) + ", ");
				}
				if (address.getAddress2() != null) {
					paragraph.add(new Chunk(address.getAddress2(), normalFont) + ", ");
				}
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
					paragraph.add(new Chunk(address.getCountry().getName(),  normalFont));
				}
			}
			
			// row 1, cell 1
			cell = new PdfPCell(new Phrase("Home Address: ", normalFont));
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
			
			// row 1, cell 2
			cell = new PdfPCell(paragraph);
			cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(cell);
						
			
			document.add(table);
			
			
			/**
			 * For line spacing
			 */
			paragraph = new Paragraph(" ");
			paragraph.setSpacingAfter(10);
			document.add(paragraph);
			
			/**
			 * List all evaluation results for valid and not-required items
			 * You can only generate evaluation report once all the requirements are met
			 */
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(10);
			paragraph.add(new Paragraph("Checklist", normalBoldFont));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.setSpacingAfter(15);
			
			if (eval != null) {
				
				PdfPTable table2 = new PdfPTable(3);
				table2.setWidthPercentage(75);
				table2.setWidths(new int[]{3, 2, 2});
				table2.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				createTableRowHeader(table2, normalBoldFont, "", "Submitted Documents", "Outstanding Documents");

				createTable3Rows(table2, "Application Fee: ", eval.getApplicationFee());
				createTable3Rows(table2, "Bank Statement: ", eval.getBankStmt());
				createTable3Rows(table2, "Financial Affidavit: ", eval.getFinancialAffidavit());
				createTable3Rows(table2, "Diplome: ", eval.getDiplome());
				createTable3Rows(table2, "F1 Visa: ", eval.getF1Visa());
				createTable3Rows(table2, "I20: ", eval.getI20());
				createTable3Rows(table2, "Passport: ", eval.getPassport());
				createTable3Rows(table2, "Application Form: ", eval.getApplicationForm());
				createTable3Rows(table2, "Enrollment Agreement: ", eval.getEnrollmentAgreement());
				createTable3Rows(table2, "Grievance Policy: ", eval.getGrievancePolicy());
				createTable3Rows(table2, "Recommendation Letter: ", eval.getRecommendationLetter());
				createTable2RowsColSpan2(table2, "Source of Money: ", eval.getSourceOfMoney());
				createTable2RowsColSpan2(table2, "Amount of Money: ", eval.getAmountOfMoney());
				
				document.add(table2);
				
			}
			
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
		/**
		 * set the header Content-disposition to inline to render pdf inline instead of prompting a download window
		 */
		response.setHeader("Content-Disposition", "inline;filename=Test.pdf");
		try {
			response.flushBuffer();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private static void createTableRowHeader(PdfPTable table, Font font, String label, String value1, String value2){
		
		PdfPCell cell = new PdfPCell();
		
		// row 1, cell 1
		cell = new PdfPCell(new Phrase(label, font));
		table.addCell(cell);
		
		// row 1, cell 2
		cell = new PdfPCell(new Phrase(value1, font));
		table.addCell(cell);
		
		// row 1, cell 3
		cell = new PdfPCell(new Phrase(value2, font));
		table.addCell(cell);

	}
	private static void createTableRowHeader(PdfPTable table, Font font, String label, String value1, String value2, String value3){
		
		PdfPCell cell = new PdfPCell();
		
		// row 1, cell 1
		cell = new PdfPCell(new Phrase(label, font));
		table.addCell(cell);
		
		// row 1, cell 2
		cell = new PdfPCell(new Phrase(value1, font));
		table.addCell(cell);
		
		// row 1, cell 3
		cell = new PdfPCell(new Phrase(value2, font));
		table.addCell(cell);
		
		// row 1, cell 4
		cell = new PdfPCell(new Phrase(value3, font));
		table.addCell(cell);
		
	}
	private static void createTableRowHeader(PdfPTable table, Font font, String value1, String value2, String value3, String value4, String value5, String value6){
		
		PdfPCell cell = new PdfPCell();
		
		// row 1, cell 1
		cell = new PdfPCell(new Phrase(value1,font));
		cell.setPadding(8);
		table.addCell(cell);
		
		// row 1, cell 2
		cell = new PdfPCell(new Phrase(value2, font));
		cell.setPadding(8);
		table.addCell(cell);
		
		// row 1, cell 3
		cell = new PdfPCell(new Phrase(value3, font));
		cell.setPadding(8);
		table.addCell(cell);
		
		// row 1, cell 4
		cell = new PdfPCell(new Phrase(value4, font));
		cell.setPadding(8);
		table.addCell(cell);
		
		// row 1, cell 5
		cell = new PdfPCell(new Phrase(value5, font));
		cell.setPadding(8);
		table.addCell(cell);
		
		// row 1, cell 6
		cell = new PdfPCell(new Phrase(value6, font));
		cell.setPadding(8);
		table.addCell(cell);
		
	}
	private static void createTable3Rows(PdfPTable table, String label, String value){
		PdfPCell cell = new PdfPCell();
		
		// row 1, cell 1
		cell = new PdfPCell(new Phrase(label, normalFont));
		table.addCell(cell);
		
		if (value.equalsIgnoreCase("complete")) {
			// row 1, cell 2
			cell = new PdfPCell(new Phrase("X", normalFont));
			table.addCell(cell);
			// row 1, cell 3
			cell = new PdfPCell(new Phrase("", normalFont));
			table.addCell(cell);
		} else if (value.equalsIgnoreCase("incomplete")) {
			// row 1, cell 2
			cell = new PdfPCell(new Phrase("", normalFont));
			table.addCell(cell);
			// row 1, cell 3
			cell = new PdfPCell(new Phrase("X", normalFont));
			table.addCell(cell);
		} else {
			// row 1, cell 2
			cell = new PdfPCell(new Phrase("", normalFont));
			table.addCell(cell);
			// row 1, cell 3
			cell = new PdfPCell(new Phrase("", normalFont));
			table.addCell(cell);
		}

	}
	private static void createTable2RowsColSpan2(PdfPTable table, String label, String value){
		PdfPCell cell = new PdfPCell();
		
		// row 1, cell 1
		cell = new PdfPCell(new Phrase(label, normalFont));
		table.addCell(cell);
		
		// row 1, cell 2
		cell = new PdfPCell(new Phrase(value, normalFont));
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
	}
	private static void createNoBorderTableRow(PdfPTable table, String label, String value){
		PdfPCell cell = new PdfPCell();

		// row 1, cell 1
		cell = new PdfPCell(new Phrase(label, normalFont));
		cell.setBorder(Rectangle.NO_BORDER);
//		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//		cell.setPadding(8);
		table.addCell(cell);
		
		// row 1, cell 2
		cell = new PdfPCell(new Phrase(value, normalFont));
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);
	}
	
	@RequestMapping(value = "/admin/report/{userEntityId}/missingDocuments", method = RequestMethod.GET)
	public void missingDocumentsReport(@PathVariable("userEntityId") Long userEntityId,
			HttpServletResponse response, 
			HttpServletRequest request,
			Model model) {
		
//		Checklist checklist = checklistService.getChecklistByUserEntityId(userEntityId);
		Evaluation evaluation = evaluationService.getEvaluationByUserEntityId(userEntityId);
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
				missingDocuments.add("Bank Statement");
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
		
		Document document = new Document();
		document.setMargins(36, 36, 72, 72);

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
					paragraph.add(new Chunk(address.getCountry().getName(),  normalFont));
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
			if (userEntityService.getUserEntity(userEntityId).getProgramOfStudy() != null) {
				paragraph.add(userEntityService.getUserEntity(userEntityId).getProgramOfStudy().getName());
			} else {
				paragraph.add(new Chunk(" XXXXXXXXXXX "));
			}
			paragraph.add(new Chunk(" at the American College of Commerce and Technology has been approved. "));
			if (userEntityService.getUserEntity(userEntityId).getTerm() != null) {
				paragraph.add(userEntityService.getUserEntity(userEntityId).getTerm().getName());
			} else {
				paragraph.add(new Chunk(" XXXXXXXXXXX "));
			}
			paragraph.add(new Chunk(" classes start on "));
			String startDateString = "";
			SimpleDateFormat startDateFormat = new SimpleDateFormat("EEE, MMM dd, YYYY");
			
			if (userEntityService.getUserEntity(userEntityId).getTerm() != null) {
				startDateString = startDateFormat.format(userEntityService.getUserEntity(userEntityId).getTerm().getStartDate());
			} else {
				paragraph.add(new Chunk(" XXXXXXXXXXX "));
			}
			paragraph.add(startDateString);
			paragraph.add(".");
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
		StudentAgreement studentAgreement = studentAgreementService.getStudentAgreementByUserEntityIdAndAgreementName(userEntityId, 
				AgreementName.CERTIFY_INFO_PROVIDED_IS_TRUE_ACCURATE);
		

		
		Document document = new Document(PageSize.A4, 36, 36, 54, 54);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
//			PdfWriter.getInstance(document, out);
			PdfWriter writer = PdfWriter.getInstance(document, out);
			// add header and footer before document.open()
		    
		    TableHeader event = new TableHeader();
	        writer.setPageEvent(event);
		    
		    /*open document*/
			document.open();

			addMetaData(document);
			
			Paragraph paragraph = new Paragraph();
			paragraph.add(new Paragraph("APPLICATION FOR ADMISSION", h1Font));
			paragraph.setSpacingBefore(20);
			paragraph.setSpacingAfter(20);
			paragraph.setAlignment(Element.ALIGN_CENTER);
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
	        cell.setGrayFill(0.95f);
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
			if (userEntity.getCountryOfBirth() != null) {
				paragraph.add(new Phrase(userEntity.getCountryOfBirth().getName(), smallFont));
			}
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			// row 3, cell 3
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Country of Citizenship: ", normalBoldFont));
			if (userEntity.getCitizenship() != null) {
				paragraph.add(new Phrase(userEntity.getCitizenship().getName(), smallFont));
			}
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
			
			/**
			 * Display HOME_ADDRESS
			 */
			paragraph = new Paragraph();
			Address address = addressService.getAddressByUserEntityIdAndAddressType(userEntityId, AddressType.HOME_ADDRESS);
			if (address != null) {
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
				
				// Address row 2, cell 3
				paragraph = new Paragraph();
				paragraph.add(new Phrase("Country: ", normalBoldFont));
				if (address.getCountry() != null) {
					paragraph.add(new Phrase(address.getCountry().getName(), smallFont));
				}
				cell = new PdfPCell(paragraph);
				cell.setPadding(8);
//				cell.setBorder(10);
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				table.addCell(cell);
				
			}
			
			/**
			 * Display MAILING_ADDRESS
			 */
			paragraph = new Paragraph();
			Address mailingAddress = addressService.getAddressByUserEntityIdAndAddressType(userEntityId, AddressType.MAILING_ADDRESS);
			if (mailingAddress != null) {
				// Address row 1, cell 1 -2
				Paragraph p = new Paragraph();
				p.add(new Phrase(mailingAddress.getAddressType() + ": ", normalBoldFont));
				p.add(new Phrase(mailingAddress.getAddress1() + ", " + mailingAddress.getAddress2(), smallFont));
				cell = new PdfPCell(p);
				
				cell.setColspan(2);
				cell.setPadding(8);
				cell.setBorder(Rectangle.LEFT);
				table.addCell(cell);
				// Address row 1, cell 3
				paragraph = new Paragraph();
				paragraph.add(new Phrase("City: ", normalBoldFont));
				paragraph.add(new Phrase(mailingAddress.getCity(), smallFont));
				cell = new PdfPCell(paragraph);
				cell.setBorder(Rectangle.RIGHT);
				cell.setPadding(8);
				table.addCell(cell);
				
				// Address row 2, cell 1
				paragraph = new Paragraph();
				paragraph.add(new Phrase("State: ", normalBoldFont));
				paragraph.add(new Phrase(mailingAddress.getState(), smallFont));
				cell = new PdfPCell(paragraph);
				cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
				cell.setPadding(8);
				table.addCell(cell);
				// Address row 2, cell 2
				paragraph = new Paragraph();
				paragraph.add(new Phrase("Zip Code: ", normalBoldFont));
				paragraph.add(new Phrase(mailingAddress.getZipcode(), smallFont));
				cell = new PdfPCell(paragraph);
				cell.setBorder(Rectangle.BOTTOM);
				cell.setPadding(8);
				table.addCell(cell);
				
				// Address row 2, cell 3
				paragraph = new Paragraph();
				paragraph.add(new Phrase("Country: ", normalBoldFont));
				if (mailingAddress.getCountry() != null) {
					paragraph.add(new Phrase(mailingAddress.getCountry().getName(), smallFont));
				}
				cell = new PdfPCell(paragraph);
				cell.setPadding(8);
//				cell.setBorder(10);
				cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
				table.addCell(cell);
				
			}
			
			// row HeardAboutAcctThru, cell 1 - span 3
			cell = new PdfPCell(new Phrase("How did you hear about ACCT?"));
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(8);
			cell.setBorderWidthBottom(2f);
	        cell.setGrayFill(0.95f);
			table.addCell(cell);
			
			// row HeardAboutAcctThru, cell 1 - span 3
			paragraph = new Paragraph(" ");
			paragraph.add(new Phrase(userEntity.getHeardAboutAcctThru(), smallFont));
			cell = new PdfPCell(paragraph);
			cell.setColspan(3);
			cell.setPadding(8);
			cell.setBorder(Rectangle.BOX);
			table.addCell(cell);
			
			// row pre6, cell 1 - span 3
			cell = new PdfPCell(new Phrase("TERM AND PROGRAM OF STUDY"));
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(8);
			cell.setBorderWidthBottom(2f);
	        cell.setGrayFill(0.95f);
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
	        cell.setGrayFill(0.95f);
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
					if (highSchool.getCountry() != null) {
						paragraph.add(new Phrase(highSchool.getCountry().getName(), smallFont));
					}
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
	        cell.setGrayFill(0.95f);
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
					if (institute.getLevelOfStudy() != null) {
						paragraph.add(new Phrase(institute.getLevelOfStudy().toString(), smallFont));
					}
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
					if (institute.getCountry() != null) {
						paragraph.add(new Phrase(institute.getCountry().getName(), smallFont));
					}
					cell = new PdfPCell(paragraph);
					cell.setPadding(8);
					cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
					table.addCell(cell);
					
				}
			}
			
			// signature
			// row preHighSchool, cell 1 - span 3
			cell = new PdfPCell(new Phrase("I certify the information provided in this application is true and accurate to the best of my knowledge."));
			cell.setColspan(3);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPadding(8);
			cell.setBorderWidthBottom(2f);
	        cell.setGrayFill(0.95f);
			table.addCell(cell);
			
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Name(Signature): ", normalBoldFont));
			if (studentAgreement != null) {
				paragraph.add(new Phrase(studentAgreement.getSignature(), smallFont));
			}
			cell = new PdfPCell(paragraph);
			cell.setColspan(2);
			cell.setPadding(8);
			cell.setBorder(Rectangle.BOX);
			table.addCell(cell);
			
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Date: ", normalBoldFont));
			if (studentAgreement != null) {
				/*String agreementCreatedString = new String("");
				SimpleDateFormat formatAgreementCreatedDate = new SimpleDateFormat("MM-dd-YYYY");
				agreementCreatedString = formatAgreementCreatedDate.format(studentAgreement.getDateCreated());
				paragraph.add(new Phrase(agreementCreatedString, smallFont));*/
				paragraph.add(new Phrase(studentAgreement.getDateCreated().toString(), smallFont));
			}
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			cell.setBorder(Rectangle.BOX);
			table.addCell(cell);
			
			
						
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
	
	@RequestMapping(value = "/accounts/getAccountsByTermStatusState", method = RequestMethod.POST)
	public void postAccountsByTermStatusState(
			HttpServletResponse response, 
			HttpServletRequest request,
			@ModelAttribute UserEntity origUserEntity, 
			BindingResult result, 
			Model model) {
		
		java.util.List<UserEntity> selectedUsers = userEntityService.getAccountsByTermStatusState(
				origUserEntity.getTerm().getId(), origUserEntity.isInternational(), origUserEntity.getAccountState());
		long count = userEntityService.countAccountsByTermStatusState(
				origUserEntity.getTerm().getId(), origUserEntity.isInternational(), origUserEntity.getAccountState());
		String termName = termService.getTerm(origUserEntity.getTerm().getId()).getName();
		
		Document document = new Document(PageSize.A4, 36, 36, 54, 54);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
//			PdfWriter.getInstance(document, out);
			PdfWriter writer = PdfWriter.getInstance(document, out);
			// add header and footer before document.open()
		    
		    TableHeader event = new TableHeader();
	        writer.setPageEvent(event);
		    
		    /*open document*/
			document.open();

			addMetaData(document);
			
			Paragraph paragraph = new Paragraph();
			paragraph.add(new Paragraph("Prospective Students Report", h1Font));
			paragraph.setSpacingBefore(20);
			paragraph.setSpacingAfter(20);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Term: " + termName, normalBoldFont));
//			paragraph.add(new Phrase(": " + origUserEntity.getTerm().getName()));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.add(new Phrase("F-1 Status: ", normalBoldFont));
			if (origUserEntity.isInternational()) {
				paragraph.add(new Phrase("International"));
			} else {
				paragraph.add(new Phrase("Domestic"));
			}
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Application Status: ", normalBoldFont));
			paragraph.add(new Phrase(origUserEntity.getAccountState()));
			document.add(paragraph);
			
			paragraph = new Paragraph();
			paragraph.add(new Paragraph("Total: " + count, normalBoldFont));
//			paragraph.add(new Paragraph(": " + count));
			paragraph.setSpacingAfter(20);
			document.add(paragraph);
			
			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);

			// row 1, cell 1
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Last Name: ", normalBoldFont));
			PdfPCell cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			// row 1, cell 2
			paragraph = new Paragraph();
			paragraph.add(new Phrase("First Name: ", normalBoldFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			// row 1, cell 3
			paragraph = new Paragraph();
			paragraph.add(new Phrase("F-1 Status: ", normalBoldFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			
			// row 1, cell 4
			paragraph = new Paragraph();
			paragraph.add(new Phrase("Application Status: ", normalBoldFont));
			cell = new PdfPCell(paragraph);
			cell.setPadding(8);
			table.addCell(cell);
			
			
			if (selectedUsers != null) {
				for (UserEntity userEntity : selectedUsers) {
					// row 2, cell 1
					paragraph = new Paragraph();
					paragraph.add(new Phrase(userEntity.getLastName(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setPadding(8);
					table.addCell(cell);
					// row 2, cell 2
					paragraph = new Paragraph();
					paragraph.add(new Phrase(userEntity.getFirstName(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setPadding(8);
					table.addCell(cell);
					// row 2, cell 3
					paragraph = new Paragraph();
					if (userEntity.isInternational()) {
						paragraph.add(new Phrase("International", smallFont));
					} else {
						paragraph.add(new Phrase("Domestic", smallFont));
					}
					cell = new PdfPCell(paragraph);
					cell.setPadding(8);
					table.addCell(cell);
					// row 2, cell 4
					paragraph = new Paragraph();
					paragraph.add(new Phrase(userEntity.getAccountState(), smallFont));
					cell = new PdfPCell(paragraph);
					cell.setPadding(8);
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
	
	/**
	 * This method creates a pdf reports of all the applicants
	 * in a table by term, status and accountState
	 * 
	 * @param response
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/admin/report/getAccountsSummary", method = RequestMethod.GET)
	public void getAccountsSummary(
			HttpServletResponse response, 
			HttpServletRequest request,
			Model model) {
		
		java.util.List<Term> terms = termService.getAllTerms();
		
		Document document = new Document(PageSize.A4, 36, 36, 54, 54);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
//			PdfWriter.getInstance(document, out);
			PdfWriter writer = PdfWriter.getInstance(document, out);
			// add header and footer before document.open()
		    
		    TableHeader event = new TableHeader();
	        writer.setPageEvent(event);
		    
		    /*open document*/
			document.open();

			addMetaData(document);
			
			Paragraph paragraph = new Paragraph();
			paragraph.add(new Paragraph("Prospective Students Report", h1Font));
			paragraph.setSpacingBefore(20);
			paragraph.setSpacingAfter(20);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);
			
			if (terms != null) {
				for (Term term : terms) {
					Map<String, Object> accountsCount = userEntityService.countAccountsByTerm(term.getId());
					
					paragraph = new Paragraph();
					paragraph.add(new Phrase("Term: " + term.getName(), normalBoldFont));
					document.add(paragraph);
					
					paragraph = new Paragraph();
					paragraph.add(new Phrase("International", normalBoldFont));
					paragraph.setSpacingAfter(10);
					document.add(paragraph);
					
					PdfPTable table = new PdfPTable(6);
					table.setWidthPercentage(100);
					
					createTableRowHeader(table, normalFont, "pending", "inprocess", "complete", "admitted", "denied", "enrolled");
					
					createTableRowHeader(table, normalFont,
							accountsCount.get("intlPendingCount").toString(), accountsCount.get("intlInprocessCount").toString(), 
							accountsCount.get("intlCompleteCount").toString(), accountsCount.get("intlAdmittedCount").toString(), 
							accountsCount.get("intlDeniedCount").toString(), accountsCount.get("intlEnrolledCount").toString());
					document.add(table);
					
					paragraph = new Paragraph();
					paragraph.add(new Phrase("Domestic", normalBoldFont));
					paragraph.setSpacingAfter(10);
					document.add(paragraph);
					
					
					table = new PdfPTable(6);
					table.setWidthPercentage(100);
					
					createTableRowHeader(table, normalFont, "pending", "inprocess", "complete", "admitted", "denied", "enrolled");
					
					createTableRowHeader(table, normalFont,
							accountsCount.get("domesticPendingCount").toString(), accountsCount.get("domesticInprocessCount").toString(), 
							accountsCount.get("domesticCompleteCount").toString(), accountsCount.get("domesticAdmittedCount").toString(), 
							accountsCount.get("domesticDenied").toString(), accountsCount.get("domesticEnrolledCount").toString());
					
					document.add(table);
					
				}
			}
			
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
	private static void addMetaData(Document document) {
		document.addTitle("American College of Commerce and Technology Prospectives Form");
		document.addSubject("American College of Commerce and Technology Form");
		document.addKeywords("ACCT, Application Form, International");
		document.addAuthor("Daniel Anenia");
		document.addCreator("Daniel Anenia");
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
	


//  /**
//  * Inner class to add a table as header.
//  */
// class TableHeader extends PdfPageEventHelper {
//     /** The header text. */
//     String header = "American College of Commerce and Technology";
//     /** The template with the total number of pages. */
//     PdfTemplate total;
//
//     /**
//      * Allows us to change the content of the header.
//      * @param header The new header String
//      */
//     public void setHeader(String header) {
//         this.header = header;
//     }
//
//     /**
//      * Creates the PdfTemplate that will hold the total number of pages.
//      * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
//      *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//      */
//     public void onOpenDocument(PdfWriter writer, Document document) {
//         total = writer.getDirectContent().createTemplate(30, 16);
//     }
//
//     /**
//      * Adds a header to every page
//      * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
//      *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//      */
//     public void onEndPage(PdfWriter writer, Document document) {
//         PdfPTable table = new PdfPTable(2);
//         try {
////             table.setWidths(new int[]{24, 24, 2});
//             table.setWidths(new int[]{25, 25});
//             table.setTotalWidth(527);
//             table.setLockedWidth(true);
//             
//             table.getDefaultCell().setFixedHeight(20);
//             table.getDefaultCell().setBorder(Rectangle.BOTTOM);
//             table.addCell(header);
//             
//             table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
//             table.getDefaultCell().setBorder(Rectangle.BOTTOM);
//             table.addCell(new Phrase("www.acct2day.org"));
//             
////             PdfPCell cell = new PdfPCell(Image.getInstance(total));
////             PdfPCell cell = new PdfPCell(new Phrase("www.acct2day.org"));
////             cell.setBorder(Rectangle.BOTTOM);
////             table.addCell(cell);
//             
//             table.writeSelectedRows(0, -1, 34, 813, writer.getDirectContent());
//         }
//         catch(DocumentException de) {
//             throw new ExceptionConverter(de);
//         }
//         
//         PdfPTable table2 = new PdfPTable(3);
//         try {
//         	table2.setWidths(new int[]{24, 24, 2});
//         	table2.setTotalWidth(527);
//         	table2.setLockedWidth(true);
//         	
//         	table2.getDefaultCell().setFixedHeight(20);
//         	table2.getDefaultCell().setBorder(Rectangle.TOP);
//         	table2.addCell("Printed: " + new Date());
//         	
//         	table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
//         	table2.addCell(String.format("Page %d of", writer.getPageNumber()));
//         	
//         	PdfPCell cell = new PdfPCell(Image.getInstance(total));
//         	cell.setBorder(Rectangle.TOP);
//         	table2.addCell(cell);
//         	
//         	table2.writeSelectedRows(0, -1, 34, 50, writer.getDirectContent());
//         }
//         catch(DocumentException de) {
//         	throw new ExceptionConverter(de);
//         }
//     }
//
//     /**
//      * Fills out the total number of pages before the document is closed.
//      * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
//      *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
//      */
//     public void onCloseDocument(PdfWriter writer, Document document) {
//         ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
//                 new Phrase(String.valueOf(writer.getPageNumber() - 1)),
//                 2, 2, 0);
//     }
// }
}
