package com.bc.calendar.report;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bc.calendar.vo.ScheduleTime;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class CalendarReport extends MainReport<ScheduleTime> {

	private static final Logger logger = LoggerFactory
			.getLogger(CalendarReport.class);
	
	@Value("${calendar.report.header.time}")
	private String timeHeader;
	@Value("${calendar.report.header.params}")
	private String paramsHeader;
	@Value("${calendar.report.header.notes}")
	private String notesHeader;
	@Value("${calendar.report.file.name}")
	private String calendarReportFileName;
	@Value("${calendar.report.file.directory}")
	private String calendarReportFileDir;
	@Value("${calendar.report.file.title}")
	private String calendarReportTitle;
	
	private Document document;
	
	private File report;
	
	private LocalDate reportDate;
	private int cols = 3;
	private float widths[] = {5f, 15f, 80f};
	
	@PostConstruct
	private void initReport() {
		report = new File(MessageFormat.format(
				calendarReportFileDir, 
				System.getProperty("user.home"), 
				reportRootDirectory, 
				calendarReportFileName));			
	}
	
	@Override
	public File createDocument(List<ScheduleTime> currentSchedules) throws ReportException {
		
		try {
			document = buildWriterInstance(report);
			document.open();

			for (ScheduleTime scheduleTime : currentSchedules) {
				PdfPTable table = configTable(cols, widths);
				PdfPCell time = buildContentCell(new Phrase(scheduleTime.getTime().toString(), CONTENT_FONT), 
						Element.ALIGN_CENTER, BaseColor.WHITE, Optional.empty(), Optional.empty(), MAX_HEIGHT);			
				PdfPCell params = buildContentCell(new Phrase(scheduleTime.getParams()[0], CONTENT_FONT), 
						Element.ALIGN_CENTER, BaseColor.WHITE, Optional.empty(), Optional.empty(), MAX_HEIGHT);	
				PdfPCell notes = buildContentCell(new Phrase(scheduleTime.getNotes(), CONTENT_FONT), 
						Element.ALIGN_CENTER, BaseColor.WHITE, Optional.empty(), Optional.empty(), MAX_HEIGHT);
				
				table.addCell(time);
				table.addCell(params);
				table.addCell(notes);

				document.add(table);
			}
		} catch (DocumentException | IOException e) {
			logger.error("There was an error to create the report.", e);
			throw new ReportException("There was an error to create the report.", e);
		} finally {
			document.close();
		}
		
		return report;
	}
	
	@Override
	public PdfPTable configTable() throws DocumentException {
		return configTable(cols, widths);
	}
	
	private PdfPTable configTable(int cols, float... widths) throws DocumentException {
		PdfPTable table = new PdfPTable(cols);
		table.setWidthPercentage(100);
		table.setWidths(widths);
		return table;
	}
	
	@Override
	public void onStartPage(PdfWriter writer, Document document) {	
		
		try {
			document.add(buildReportTitle(String.format(calendarReportTitle, reportDate)));
			document.add(addHeaders(timeHeader, paramsHeader, notesHeader));			
		} catch (DocumentException e) {
			logger.error("There was an error while building the report headers", e);
		}
	}

	public void setReportDate(LocalDate reportDate) {
		this.reportDate = reportDate;
	}

}
