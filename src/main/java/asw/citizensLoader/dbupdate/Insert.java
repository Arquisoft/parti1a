package asw.citizensLoader.dbupdate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.lowagie.text.DocumentException;

import asw.dbManagement.model.Participant;

public interface Insert {
	Participant save(Participant user) throws FileNotFoundException, DocumentException, IOException;

	List<Participant> findByDNI(String dni);

	List<Participant> findByEmail(String email);
}
