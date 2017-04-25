package loader;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;

import com.lowagie.text.DocumentException;

import asw.citizensLoader.executer.ActionSingleton;
import asw.citizensLoader.persistence.UserFinder;
import asw.citizensLoader.persistence.util.Jpa;
import asw.dbManagement.model.Participant;

public class ExecuterTest {

	@Test
	public void testActionSingleton() throws FileNotFoundException, DocumentException, IOException {
		ActionSingleton aS = ActionSingleton.getInstance();
		ActionSingleton aS2 = ActionSingleton.getInstance();

		assertEquals(aS, aS2);

		Date date = new Date(System.currentTimeMillis());
		Participant user = new Participant("Paco", "Francisco", "", date, "francisco@gmail.com", "87654321P", "C\\Uría",
				"Español", false, false);

		aS.getAF().saveData(user);

		EntityManager mapper = Jpa.createEntityManager();
		EntityTransaction trx = mapper.getTransaction();
		trx.begin();

		Participant user2 = UserFinder.findByEmail("francisco@gmail.com").get(0);

		assertEquals(user, user2);

		trx.commit();

	}

}
