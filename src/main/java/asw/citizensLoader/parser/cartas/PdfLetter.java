package asw.citizensLoader.parser.cartas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import asw.dbManagement.model.Participant;

public class PdfLetter extends Letter {
	private Document document;

	public void createLetter(Participant user) throws DocumentException, FileNotFoundException {
		document = null;
		FileOutputStream letter = null;
		File folder = new File("src/main/resources/cartas/pdf");
		if (!folder.exists())
			folder.mkdirs();
		letter = new FileOutputStream("src/main/resources/cartas/pdf/" + user.getDNI() + ".pdf");
		document = new Document();
		PdfWriter.getInstance(document, letter);
		document.open();
		document.add(new Paragraph("Usuario: " + user.getEmail() + "\n Password: " + user.getPassword()));
		document.close();
	}
}
