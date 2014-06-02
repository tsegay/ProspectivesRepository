package com.prospectivestiles.web;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.id.GUIDGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.InputStream;

@Controller
public class AdminPDFReportGenerator {

	private static String FILE = "path-to-file";
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);

	@RequestMapping(value="/download/{fileName}")
	public String download(@PathVariable("fileName") String fileName, HttpServletResponse res, HttpServletRequest req){
		String rootPath = System.getProperty("catalina.home");
		System.out.println("Downloading File");
//      System.out.println("rootPath:" + rootPath);
		File dir = new File(rootPath + File.separator + "tmpFiles");
		res.setHeader("Content-Type", "application/pdf");
	    res.setHeader("Content-Disposition",
	            "inline;filename=Saba_PhBill.pdf");
		//copy file
		
		File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
		if (!serverFile.exists())
			return "welcome";
		FileInputStream in=null;
		try {
			in = new FileInputStream(serverFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(in!=null)
			FileCopyUtils.copy(in, res.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverFile.delete();
		return "welcome";
		
	}
	
	public String random(){
		SecureRandom rand = new SecureRandom();
		return new BigInteger(130, rand).toString(32);
	}
	@RequestMapping("/createMemPDF")
	public void createMemPDF(HttpServletResponse res, HttpServletRequest req){

		Document document = new Document();
		//		document.setPageSize(PageSize.A4);
		
		//String rootPath = System.getProperty("catalina.home");
		//        System.out.println("rootPath:" + rootPath);
		//File dir = new File(rootPath + File.separator + "tmpFiles");
		//if (!dir.exists())
			//dir.mkdirs();
	//	String fileName= random();
		// Create the file on server
		//File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
		//System.out.println("serverFile.toString(): " + serverFile.toString());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, out);
			document.open();

			/*Paragraph paragraph = new Paragraph();
			paragraph.add("First generated pdf file!!");
			document.add(paragraph);*/

			addMetaData(document);
			addTitlePage(document);
			addContent(document);

			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		try {
			FileCopyUtils.copy(out.toByteArray(), res.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res.setHeader("Content-Type", "application/pdf");
	    res.setHeader("Content-Disposition",
	            "inline;filename=Saba_PhBill.pdf");
try {
	res.flushBuffer();
	res.getOutputStream().close();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}	    

//return "redirect:download/"+fileName;

	}
	@RequestMapping("/createPDF")
	public String createPDF(){

		Document document = new Document();
		//		document.setPageSize(PageSize.A4);

		String rootPath = System.getProperty("catalina.home");
		//        System.out.println("rootPath:" + rootPath);
		File dir = new File(rootPath + File.separator + "tmpFiles");
		if (!dir.exists())
			dir.mkdirs();
		String fileName= random();
		// Create the file on server
		File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
		System.out.println("serverFile.toString(): " + serverFile.toString());


		try {
			PdfWriter.getInstance(document, new FileOutputStream(serverFile));
			document.open();

			/*Paragraph paragraph = new Paragraph();
			paragraph.add("First generated pdf file!!");
			document.add(paragraph);*/

			addMetaData(document);
			addTitlePage(document);
			addContent(document);

			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return "redirect:download/"+fileName;

	}

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(Document document) {
		document.addTitle("My first PDF");
		document.addSubject("Using iText");
		document.addKeywords("Java, PDF, iText");
		document.addAuthor("Daniel Anenia");
		document.addCreator("Daniel Anenia");
	}

	private static void addTitlePage(Document document)
			throws DocumentException {
		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);
		// Lets write a big header
		preface.add(new Paragraph("Title of the document", catFont));

		addEmptyLine(preface, 1);
		// Will create: Report generated by: _name, _date
		preface.add(new Paragraph("Report generated by: " + "user" + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				smallBold));
		addEmptyLine(preface, 3);
		preface.add(new Paragraph("This document describes something which is very important ",
				smallBold));

		addEmptyLine(preface, 8);

		preface.add(new Paragraph("This document is a test document...).",
				redFont));

		document.add(preface);
		// Start a new page
		document.newPage();
	}

	private static void addContent(Document document) throws DocumentException {
		Anchor anchor = new Anchor("First Chapter", catFont);
		anchor.setName("First Chapter");

		// Second parameter is the number of the chapter
		Chapter catPart = new Chapter(new Paragraph(anchor), 1);

		Paragraph subPara = new Paragraph("Subcategory 1", subFont);
		Section subCatPart = catPart.addSection(subPara);
		subCatPart.add(new Paragraph("Hello"));

		subPara = new Paragraph("Subcategory 2", subFont);
		subCatPart = catPart.addSection(subPara);
		subCatPart.add(new Paragraph("Paragraph 1"));
		subCatPart.add(new Paragraph("Paragraph 2"));
		subCatPart.add(new Paragraph("Paragraph 3"));

		// add a list
		createList(subCatPart);
		Paragraph paragraph = new Paragraph();
		addEmptyLine(paragraph, 5);
		subCatPart.add(paragraph);

		// add a table
		createTable(subCatPart);

		// now add all this to the document
		document.add(catPart);

		// Next section
		anchor = new Anchor("Second Chapter", catFont);
		anchor.setName("Second Chapter");

		// Second parameter is the number of the chapter
		catPart = new Chapter(new Paragraph(anchor), 1);

		subPara = new Paragraph("Subcategory", subFont);
		subCatPart = catPart.addSection(subPara);
		subCatPart.add(new Paragraph("This is a very important message"));

		// now add all this to the document
		document.add(catPart);

	}

	private static void createTable(Section subCatPart)
			throws BadElementException {
		PdfPTable table = new PdfPTable(3);

		// t.setBorderColor(BaseColor.GRAY);
		// t.setPadding(4);
		// t.setSpacing(4);
		// t.setBorderWidth(1);

		PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Table Header 2"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Table Header 3"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		table.setHeaderRows(1);

		table.addCell("1.0");
		table.addCell("1.1");
		table.addCell("1.2");
		table.addCell("2.1");
		table.addCell("2.2");
		table.addCell("2.3");

		subCatPart.add(table);

	}

	private static void createList(Section subCatPart) {
		List list = new List(true, false, 10);
		list.add(new ListItem("First point"));
		list.add(new ListItem("Second point"));
		list.add(new ListItem("Third point"));
		subCatPart.add(list);
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}
