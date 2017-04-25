package asw.citizensLoader.parser.cartas;

import java.io.FileNotFoundException;
import java.io.IOException;
import com.lowagie.text.DocumentException;
import asw.dbManagement.model.Participant;


public abstract class Letter {
		public abstract void createLetter(Participant user) throws DocumentException, FileNotFoundException, IOException;
}
