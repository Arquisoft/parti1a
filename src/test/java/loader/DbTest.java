package loader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.junit.After;
import org.junit.Test;
import com.lowagie.text.DocumentException;
import asw.citizensLoader.executer.ActionSingleton;
import asw.citizensLoader.persistence.UserFinder;
import asw.citizensLoader.persistence.util.Jpa;
import asw.dbManagement.model.Participant;

public class DbTest {

	@Test
	public void usuarioYaExistenteDni() throws FileNotFoundException, DocumentException, IOException {
		ActionSingleton aS = ActionSingleton.getInstance();
		Date date = new Date(System.currentTimeMillis());
		Participant user1 = new Participant("Paco", "Francisco", "", date, "francisco@gmail.com", "87654321P",
				"C\\Uría", "Español", false, false);
		Participant user2 = new Participant("Paco", "Francisco", "", date, "franci@gmail.com", "87654321P", "C\\Uría",
				"Español", false, false);

		aS.getAF().saveData(user1);
		aS.getAF().saveData(user2);

		EntityManager mapper = Jpa.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		List<Participant> test = UserFinder.findByDNI("87654321P");
		assertEquals(test.get(0).getEmail(), "francisco@gmail.com");

		trx.commit();
		mapper.close();
	}

	@Test
	public void usuarioYaExistenteEmail() throws FileNotFoundException, DocumentException, IOException {
		ActionSingleton aS = ActionSingleton.getInstance();
		Date date = new Date(System.currentTimeMillis());
		Participant user1 = new Participant("Paco", "Francisco", "", date, "francisco@gmail.com", "87654321P",
				"C\\Uría", "Español", false, false);
		Participant user2 = new Participant("Paco", "Francisco", "", date, "francisco@gmail.com", "87654353Y",
				"C\\Uría", "Español", false, false);

		aS.getAF().saveData(user1);
		aS.getAF().saveData(user2);

		EntityManager mapper = Jpa.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		List<Participant> test = UserFinder.findByEmail("francisco@gmail.com");
		assertEquals(test.get(0).getDNI(), "87654321P");

		trx.commit();
		mapper.close();

	}

	@After
	public void deleting() {
		EntityManager mapper = Jpa.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();
		List<Participant> aBorrar = UserFinder.findByDNI("87654321P");
		Jpa.getManager().remove(aBorrar.get(0));
		trx.commit();
		mapper.close();
	}

}
