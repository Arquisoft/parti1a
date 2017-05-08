package loader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import org.junit.Test;
import com.lowagie.text.DocumentException;
import asw.citizensLoader.parser.cartas.*;
import asw.dbManagement.model.Participant;


public class LetterTest {

	@Test
	public void creadasCorrectamente() throws FileNotFoundException, DocumentException, IOException {
		Letter letter = new PdfLetter();
		Date date = new Date(System.currentTimeMillis());
		Participant user1 = new Participant("Paco", "Francisco", "", date, "francisco@gmail.com", "87654321P", "C\\Uría",
				"Español", false, false);
		letter.createLetter(user1);

		File file = new File("src/main/resources/cartas/pdf/87654321P.pdf");
		assertNotNull(file.toString());
		file.delete();
		assertFalse(file.exists());

		letter = new WordLetter();
		letter.createLetter(user1);

		file = new File("src/main/resources/cartas/word/87654321P.docx");
		assertNotNull(file.toString());
		file.delete();
		assertFalse(file.exists());

		letter = new TxtLetter();
		letter.createLetter(user1);

		file = new File("src/main/resources/cartas/txt/87654321P.txt");
		assertNotNull(file.toString());
		file.delete();
		assertFalse(file.exists());
	}
	
}
