package com.prospectivestiles.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Class to add a table as header.
 */
public class TableHeader extends PdfPageEventHelper {
    /** The header text. */
    String header = "LOGO";
    /** The template with the total number of pages. */
    PdfTemplate total;
    
    private static Font vSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
    
    /**
     * Allows us to change the content of the header.
     * @param header The new header String
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Creates the PdfTemplate that will hold the total number of pages.
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
     *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
     */
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 16);
    }

    /**
     * Adds a header to every page
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
     *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
     */
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(2);
        try {
//            table.setWidths(new int[]{24, 24, 2});
        	/*2 cols for the header*/
            table.setWidths(new int[]{25, 25});
            table.setTotalWidth(527);
            table.setLockedWidth(true);
            
            /*cell 1 - Logo*/
            table.getDefaultCell().setFixedHeight(20);
            table.getDefaultCell().setBorder(Rectangle.BOTTOM);
            table.addCell(header);
            
            /*Image img = null;
			try {
//				img = Image.getInstance("${pageContext.request.contextPath}/resources/images/LOGO_for_Online_Reg._Form_Small_7.jpg");
//			"http://localhost:8080/myApp/chart.jpg")
				String imageUrl = "/prospectivestiles/src/main/webapp/resources/images/LOGO_for_Online_Reg._Form_Small_7.png";

			    img = Image.getInstance(new URL(imageUrl));
				
//				${pageContext.request.contextPath}/resources/images/favicon-2.jpg
//				/prospectivestiles/src/main/webapp/resources/images/LOGO_for_Online_Reg._Form_Small_7.png
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	img.scalePercent(150f);
        	table.addCell(img);*/
            
            /*cell 2 - ACCT address on right corner of header*/
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.getDefaultCell().setBorder(Rectangle.BOTTOM);
            
            PdfPCell cell = new PdfPCell();
            Paragraph paragraph = new Paragraph("American College of Commerce and Technology", vSmallFont);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(paragraph);
            paragraph = new Paragraph("150 S Washington St, Suite 101", vSmallFont);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(paragraph);
            paragraph = new Paragraph("Falls Church, VA 22046", vSmallFont);
            paragraph.setAlignment(Element.ALIGN_RIGHT);
			cell.addElement(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);
            
//            PdfPCell cell = new PdfPCell(Image.getInstance(total));
//            PdfPCell cell = new PdfPCell(new Phrase("www.acct2day.org"));
//            cell.setBorder(Rectangle.BOTTOM);
//            table.addCell(cell);
            
            table.writeSelectedRows(0, -1, 34, 833, writer.getDirectContent());
        }
        catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
        
        PdfPTable table2 = new PdfPTable(3);
        try {
        	table2.setWidths(new int[]{24, 24, 2});
        	table2.setTotalWidth(527);
        	table2.setLockedWidth(true);
        	
        	table2.getDefaultCell().setFixedHeight(20);
        	table2.getDefaultCell().setBorder(Rectangle.TOP);
        	String currentTimeString = new String("");
			SimpleDateFormat formatCurrentTime = new SimpleDateFormat("MM-dd-YYYY hh:mm aaa");
			currentTimeString = formatCurrentTime.format(new Date());
        	Paragraph p = new Paragraph("Printed: " + currentTimeString, vSmallFont);
        	table2.addCell(p);
        	
        	table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        	String pageN = String.format("Page %d of", writer.getPageNumber());
        	p = new Paragraph(pageN, vSmallFont);
//        	table2.addCell(String.format("Page %d of", writer.getPageNumber()));
        	table2.addCell(p);
        	
        	Image a = Image.getInstance(total);
        	a.scalePercent(65f);
//        	PdfPCell cell = new PdfPCell(Image.getInstance(total));
        	PdfPCell cell = new PdfPCell(a);
        	cell.setBorder(Rectangle.TOP);
        	table2.addCell(cell);
        	
        	table2.writeSelectedRows(0, -1, 34, 40, writer.getDirectContent());
        }
        catch(DocumentException de) {
        	throw new ExceptionConverter(de);
        }
    }

    /**
     * Fills out the total number of pages before the document is closed.
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
     *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
     */
    public void onCloseDocument(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                new Phrase(String.valueOf(writer.getPageNumber() - 1)),
                2, 2, 0);
    }
}