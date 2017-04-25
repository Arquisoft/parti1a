package loader;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import asw.dbManagement.model.Participant;

public class UserTest {

	@Test
	public void testEquals() {
		Date date = new Date(System.currentTimeMillis());
		Participant user1 = new Participant("Dani", "Duque", "", date, "email@gmail.com", "7777777R", "C\\Buenavida",
				"Español", false, false);
		Participant user2 = new Participant("Dani", "Duque", "", date, "email@gmail.com", "7777777R", "C\\Buenavida",
				"Español", false, false);
		Participant user3 = new Participant("Dani", "Duque", "", date, "email@gmail.com", "7787777R", "C\\Buenavida",
				"Español", false, false);
		Participant user4 = new Participant("Dani", "Duque", "", date, "email@gmail.com", null, "C\\Buenavida",
				"Español", false, false);

		assertEquals(true, user1.equals(user2));
		assertEquals(true, user3.equals(user3));
		assertEquals(false, user2.equals(user3));
		assertNotNull(user1);
		assertEquals(false, user1.equals(new Integer(8)));
		assertEquals(false, user4.equals(user3));
	}

	@Test
	public void testHashCode() {
		Date date = new Date(System.currentTimeMillis());
		Participant user1 = new Participant("Dani", "Duque", "", date, "email@gmail.com", "7777777R", "C\\Buenavida",
				"Español", false, false);
		Participant user2 = new Participant("Dani", "Duque", "", date, "email@gmail.com", "7777777R", "C\\Buenavida",
				"Español", false, false);
		Participant user3 = new Participant("Dani", "Duque", "", date, "email@gmail.com", "7787777R", "C\\Buenavida",
				"Español", false, false);
		Participant user4 = new Participant("Dani", "Duque", "", date, "email@gmail.com", null, "C\\Buenavida",
				"Español", false, false);
		Participant user5 = new Participant("Dani", "Duque", "", date, "email@gmail.com", null, "C\\Buenavida",
				"Español", false, false);

		assertEquals(user1.hashCode(), user2.hashCode());
		assertEquals(user4.hashCode(), user5.hashCode());
		assertNotEquals(user2.hashCode(), user3.hashCode());

		System.out.println(user1.toString());
	}

	@Test
	public void testAll() {
		Date date = new Date(System.currentTimeMillis());
		Participant user1 = new Participant("Dani", "Duque", "", date, "email@gmail.com", "7777777R", "C\\Buenavida",
				"Español", false, false);

		assertEquals("Dani", user1.getNombre());
		assertEquals("Duque", user1.getApellidos());
		assertEquals("email@gmail.com", user1.getEmail());
		assertEquals(date, user1.getFechaNacimiento());
		assertEquals("C\\Buenavida", user1.getDireccion());
		assertEquals("Español", user1.getNacionalidad());
		assertEquals("7777777R", user1.getDNI());
		assertEquals(false, user1.isAdmin());
		assertEquals(false, user1.isPolitician());
	}

}
