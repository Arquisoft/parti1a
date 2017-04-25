package asw.citizensLoader.executer;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.lowagie.text.DocumentException;

import asw.dbManagement.model.Participant;


public interface ActionFacade {
	public void saveData(Participant user) throws FileNotFoundException, DocumentException, IOException;
}