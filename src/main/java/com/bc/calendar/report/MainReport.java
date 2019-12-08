package com.bc.calendar.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class MainReport<T> extends PdfPageEventHelper {

	private static final Logger logger = LoggerFactory
			.getLogger(MainReport.class);
					
	private static final String UTF_8 = "UTF-8";
	private static final String ARIAL = "Arial";
	protected static final Font CONTENT_FONT = 
			FontFactory.getFont(ARIAL, UTF_8, 8, Font.BOLD, BaseColor.BLACK);
	
	@Value("${calendar.report.root.directory}")
	protected String reportRootDirectory;
	
	abstract File createDocument(List<T> data) throws ReportException;
	
	abstract PdfPTable configTable() throws DocumentException;
	
	protected Document buildWriterInstance(File report) throws DocumentException, IOException {	
		Document document = new Document(PageSize.LETTER, 10f, 10f, 10f, 10f);
		PdfWriter writer =  PdfWriter.getInstance(document, 
				new FileOutputStream(report));
        writer.setPageEvent(this);
        return document;
	} 
	
	protected PdfPTable buildReportTitle(String title) throws DocumentException {
		PdfPTable tableTitle = new PdfPTable(1);
		tableTitle.setWidthPercentage(100);
		tableTitle.addCell(buildContentCell(new Phrase(title, CONTENT_FONT), 
				Element.ALIGN_CENTER, BaseColor.WHITE, Optional.empty(), Optional.empty()));
		return tableTitle;
	}
	
	protected PdfPCell buildContentCell(Phrase phrase, int align, BaseColor baseColor, 
			Optional<Integer> border, 
			Optional<Boolean> hasNoBorder) {
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(align);
        cell.setBackgroundColor(baseColor);
        cell.setFixedHeight(12f);
        border.ifPresent(b -> cell.setBorder(b));
        hasNoBorder.ifPresent(unusedValue -> cell.setBorderWidth(0));
        return cell;		
	}	

	protected PdfPTable addHeaders(String... headers) throws DocumentException {
		PdfPTable headerTable = configTable();
		for (String header : headers) {
			headerTable.addCell(buildContentCell(new Phrase(header, CONTENT_FONT), 
					Element.ALIGN_CENTER, BaseColor.WHITE, Optional.empty(), Optional.empty()));
		}
		
		return headerTable;
	}
}
