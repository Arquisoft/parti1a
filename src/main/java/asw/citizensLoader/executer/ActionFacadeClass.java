package asw.citizensLoader.executer;

import java.io.FileNotFoundException;
import java.io.IOException;
import com.lowagie.text.DocumentException;
import asw.citizensLoader.dbupdate.Insert;
import asw.citizensLoader.dbupdate.InsertP;
import asw.dbManagement.model.Participant;

public class ActionFacadeClass implements ActionFacade {

	@Override
	public void saveData(Participant user) throws FileNotFoundException, DocumentException, IOException {
		Insert insert = new InsertP();
		insert.save(user);			
	}
}