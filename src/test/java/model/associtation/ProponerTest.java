package model.associtation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;

public class ProponerTest {

	private Participant participant;
	private Suggestion suggestion;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		participant = new Participant("martin", "pelaez", "diaz", new Date(1993, 11, 12), "paco@gmail.com",
				"1234567", "Calle Uria", "ESP", false, false);
		suggestion = new Suggestion("prueba", "sugges de prueba", "forma de probar las asocicaciones", 12, participant);
	}

	@Test
	public void testProponerLinked() {
		assertTrue(participant.getSuggestion().contains(suggestion));
		assertTrue(suggestion.getParticipant().equals(participant));
	}

	@Test
	public void testProponerUnLink() {
		suggestion.deleteSuggestion();

		assertFalse(participant.getSuggestion().contains(suggestion));
		assertTrue(participant.getSuggestion().size() == 0);
		assertTrue(suggestion.getParticipant() == null);
	}

	@Test
	public void testSafeReturn() {
		Set<Suggestion> propuestas = participant.getSuggestion();
		propuestas.remove(suggestion);

		assertTrue(propuestas.size() == 0);
		assertTrue("Se debe de retornar una copia de la coleccion o hacerla de solo lectura",
				participant.getSuggestion().size() == 1);
	}
}
