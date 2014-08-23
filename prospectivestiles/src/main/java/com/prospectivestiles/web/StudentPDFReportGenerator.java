package com.prospectivestiles.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.prospectivestiles.domain.Address;
import com.prospectivestiles.domain.AddressType;
import com.prospectivestiles.domain.AgreementName;
import com.prospectivestiles.domain.HighSchool;
import com.prospectivestiles.domain.Institute;
import com.prospectivestiles.domain.StudentAgreement;
import com.prospectivestiles.domain.UserEntity;
import com.prospectivestiles.service.AddressService;
import com.prospectivestiles.service.HighSchoolService;
import com.prospectivestiles.service.InstituteService;
import com.prospectivestiles.service.StandardTestService;
import com.prospectivestiles.service.StudentAgreementService;
import com.prospectivestiles.service.UserEntityService;
import com.prospectivestiles.util.TableHeader;

@Controller
public class StudentPDFReportGenerator {
	
	@Autowired
	private UserEntityService userEntityService;
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

	
	private static String FILE = "path-to-file";
	private static Font h1Font = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font h2Font = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font h3Font = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	private static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	private static Font normalBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font normalUnderline = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL | Font.UNDERLINE);
	private static Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	
	
	@RequestMapping(value = "/myAccount/report/applicationForm", method = RequestMethod.GET)
	public void getApplicationForm(
			HttpServletResponse response, 
			HttpServletRequest request,
			Model model) {
		/**
		 * use if stmt to check if an entity = null
		 */

		/**
		 * When user edits page and clicks on submit,
		 * The changes are udpated in the db but the page shows the data before the update.
		 * If user logs out and login back, the new updated data is loaded from db.
		 * So, I have to override the userEntity saved in the session, 
		 * by loading the data from the db on every call of the page
		 */	
		UserEntity userEntityInSession = getUserEntityFromSecurityContext();	
		
		UserEntity userEntity = userEntityService.getUserEntity(userEntityInSession.getId());
		
		
		
		java.util.List<Address> addresses = addressService.getAddressesByUserEntityId(userEntity.getId());
		java.util.List<HighSchool> highSchools = highSchoolService.getHighSchoolsByUserEntityId(userEntity.getId());
		java.util.List<Institute> institutes = instituteService.getInstitutesByUserEntityId(userEntity.getId());
		StudentAgreement studentAgreement = studentAgreementService.getStudentAgreementByUserEntityIdAndAgreementName(userEntity.getId(), 
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
			
			/**
			 * Display HOME_ADDRESS
			 */
			paragraph = new Paragraph();
			Address address = addressService.getAddressByUserEntityIdAndAddressType(userEntity.getId(), AddressType.HOME_ADDRESS);
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
				paragraph.add(new Phrase(address.getCountry(), smallFont));
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
			Address mailingAddress = addressService.getAddressByUserEntityIdAndAddressType(userEntity.getId(), AddressType.MAILING_ADDRESS);
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
				paragraph.add(new Phrase(mailingAddress.getCountry(), smallFont));
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
//				String agreementCreatedString = new String("");
//				SimpleDateFormat formatAgreementCreatedDate = new SimpleDateFormat("MM-dd-YYYY");
//				agreementCreatedString = formatAgreementCreatedDate.format(studentAgreement.getDateCreated());
//				paragraph.add(new Phrase(agreementCreatedString, smallFont));
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
	
	private UserEntity getUserEntityFromSecurityContext() {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication auth = securityCtx.getAuthentication();
		UserEntity userEntity = (UserEntity) auth.getPrincipal();
		return userEntity;
	}

}
