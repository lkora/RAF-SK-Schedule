package schedule.manager.service.provided.pdf;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import schedule.exporter.spec.ScheduleExporter;
import schedule.lecture.Lecture;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of the ScheduleExporter interface that exports data to a PDF format.
 */
public class ScheduleExporterPDF implements ScheduleExporter {

	@Override
	public boolean exportData(List<Lecture> lectures, String path) throws IOException {
		try (PDDocument doc = new PDDocument()) {
			PDPage page = new PDPage(PDRectangle.A4);
			doc.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(doc, page);

			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

			float margin = 50;
			float yStart = page.getMediaBox().getHeight() - margin;
			float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
			float tableHeight = yStart - margin;

			BaseTable table = new BaseTable(yStart, yStart, margin, tableWidth, margin, doc, page, true, true);

			// Create Header row
			Row<PDPage> headerRow = table.createRow(15);
			Cell<PDPage> cell = headerRow.createCell(100, "Lecture Information");
			cell.setFont(PDType1Font.HELVETICA_BOLD);

			table.addHeaderRow(headerRow);

			// Create rows for lecture data
			drawLectureRows(lectures, table);

			// Draw the table
			table.draw();
			contentStream.close();

			doc.save(path);

		}
		return true;
	}
	private void drawLectureRows(List<Lecture> lectures, BaseTable table) {
		for (Lecture lecture : lectures) {
			Row<PDPage> row = table.createRow(10);
			row.createCell((100 / 8f), lecture.getSubject());
			row.createCell((100 / 8f), lecture.getType().toString());
			row.createCell((100 / 8f), lecture.getProfessor());
			row.createCell((100 / 8f), lecture.getGroups().toString());
			row.createCell((100 / 8f), lecture.getDay().toString());
			row.createCell((100 / 8f), lecture.getStart().toString());
			row.createCell((100 / 8f), lecture.getEnd().toString());
		}
	}
}
