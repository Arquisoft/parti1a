package asw.citizensLoader.dbupdate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import com.lowagie.text.DocumentException;

import asw.citizensLoader.parser.cartas.Letter;
import asw.citizensLoader.parser.cartas.PdfLetter;
import asw.citizensLoader.parser.cartas.TxtLetter;
import asw.citizensLoader.parser.cartas.WordLetter;
import asw.citizensLoader.persistence.UserFinder;
import asw.citizensLoader.persistence.util.Jpa;
import asw.citizensLoader.reportwriter.ReportWriter;
import asw.dbManagement.model.Participant;

public class InsertP implements Insert {

	@Override
	public Participant save(Participant user) throws FileNotFoundException, DocumentException, IOException {
		EntityManager mapper = Jpa.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		try {
			if (!UserFinder.findByDNI(user.getDNI()).isEmpty()) {
				ReportWriter.getInstance().getWriteReport().log(Level.WARNING,
						"El usuario con el dni " + user.getDNI() + " ya existe en la base de datos");
				trx.rollback();
			} else if (!UserFinder.findByEmail(user.getEmail()).isEmpty()) {
				ReportWriter.getInstance().getWriteReport().log(Level.WARNING,
						"Ya existe un usuario con el email " + user.getEmail() + " en la base de datos");
				trx.rollback();
			} else {
				Jpa.getManager().persist(user);
				trx.commit();
				Letter letter = new PdfLetter();
				letter.createLetter(user);
				letter = new TxtLetter();
				letter.createLetter(user);
				letter = new WordLetter();
				letter.createLetter(user);
			}
		} catch (PersistenceException ex) {
			ReportWriter.getInstance().getWriteReport().log(Level.WARNING, "Error de la BBDD");
			if (trx.isActive())
				trx.rollback();
		} finally {
			if (mapper.isOpen())
				mapper.close();
		}
		return user;
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
