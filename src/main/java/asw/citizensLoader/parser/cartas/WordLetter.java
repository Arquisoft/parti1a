package asw.citizensLoader.parser.cartas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.lowagie.text.DocumentException;

import asw.dbManagement.model.Participant;

public class WordLetter extends Letter {
	private FileOutputStream carta;

	public void createLetter(Participant user) throws FileNotFoundException, DocumentException, IOException {
		XWPFDocument documento = new XWPFDocument();
		File folder = new File("src/main/resources/cartas/word");
		if (!folder.exists())
			folder.mkdirs();
		carta = new FileOutputStream("src/main/resources/cartas/word/" + user.getDNI() + ".docx");
		XWPFParagraph paragraph = documento.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.setText("Usuario: " + user.getEmail());
		run.addBreak();
		run.setText("Password: " + user.getPassword());
		documento.write(carta);
		documento.close();
		carta.close();
	}
}
