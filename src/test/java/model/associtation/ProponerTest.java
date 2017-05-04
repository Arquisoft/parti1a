package model.associtation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import asw.dbManagement.model.Category;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;

public class ProponerTest {

	private Participant participant;
	private Suggestion suggestion,suggestion2,suggestion3,suggestion4;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		participant = new Participant("martin", "pelaez", "diaz", new Date(1993, 11, 12), "paco@gmail.com",
				"1234567", "Calle Uria", "ESP", false, false);
		suggestion = new Suggestion("prueba", "sugges de prueba", "forma de probar las asocicaciones", 12, participant);
		suggestion2 = new Suggestion("heghdhdf", "prueba", "aumentando codecov", 12, participant);
		suggestion3 = new Suggestion("vgifqh ", "prueba3", "aumentando codecov", participant,new Category("categoria rueba"));
		suggestion4 = new Suggestion("vsghsvghs", "pruebass", "aumentar codecov", participant);
	}

	@Test
	public void testProponerLinked() {
		assertTrue(participant.getSuggestion().contains(suggestion));
		assertTrue(suggestion.getParticipant().equals(participant));
		
		assertTrue(participant.getSuggestion().contains(suggestion2));
		assertTrue(suggestion2.getParticipant().equals(participant));
		
		assertTrue(participant.getSuggestion().contains(suggestion3));
		assertTrue(suggestion3.getParticipant().equals(participant));
		
		assertTrue(participant.getSuggestion().contains(suggestion4));
		assertTrue(suggestion4.getParticipant().equals(participant));
	}

	@Test
	public void testProponerUnLink() {
		suggestion.deleteSuggestion();

		assertFalse(participant.getSuggestion().contains(suggestion));
		assertTrue(participant.getSuggestion().size() == 3);
		assertTrue(suggestion.getParticipant() == null);
		
		suggestion2.deleteSuggestion();

		assertFalse(participant.getSuggestion().contains(suggestion2));
		assertTrue(participant.getSuggestion().size() == 2);
		assertTrue(suggestion2.getParticipant() == null);
		
		suggestion3.deleteSuggestion();

		assertFalse(participant.getSuggestion().contains(suggestion3));
		assertTrue(participant.getSuggestion().size() == 1);
		assertTrue(suggestion3.getParticipant() == null);
		
		suggestion4.deleteSuggestion();

		assertFalse(participant.getSuggestion().contains(suggestion4));
		assertTrue(participant.getSuggestion().size() == 0);
		assertTrue(suggestion4.getParticipant() == null);
	}

	@Test
	public void testSafeReturn() {
		Set<Suggestion> propuestas = participant.getSuggestion();
		propuestas.remove(suggestion);
		propuestas.remove(suggestion2);
		propuestas.remove(suggestion3);
		propuestas.remove(suggestion4);
		
		assertTrue(propuestas.size() == 0);
		assertTrue("Se debe de retornar una copia de la coleccion o hacerla de solo lectura",
				participant.getSuggestion().size() == 4);
	}
}
