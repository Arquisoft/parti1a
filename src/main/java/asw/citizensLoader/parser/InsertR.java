package asw.citizensLoader.parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.lowagie.text.DocumentException;

import asw.citizensLoader.dbupdate.Insert;
import asw.citizensLoader.dbupdate.InsertP;
import asw.citizensLoader.persistence.UserFinder;
import asw.dbManagement.model.Participant;


public class InsertR implements Insert {

	@Override
	public Participant save(Participant user) throws FileNotFoundException, DocumentException, IOException {
		return new InsertP().save(user);
	}

	@Override
	public List<Participant> findByDNI(String dni) {
		return UserFinder.findByDNI(dni);
	}

	@Override
	public List<Participant> findByEmail(String email) {
		return UserFinder.findByEmail(email);
	}
}
