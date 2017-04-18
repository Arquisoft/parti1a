package model.associtation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;
import asw.dbManagement.model.VoteSuggestion;
import asw.dbManagement.model.types.VoteType;

public class VotarSuggestionTest {

	private Participant participant, participantVoto;
	private Suggestion suggestion;
	private VoteSuggestion voto;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		participant = new Participant("martin", "pelaez", "diaz", new Date(1993, 11, 12), "paco@gmail.com", "1234567",
				"Calle Uria", "ESP", false, false);
		suggestion = new Suggestion("prueba", "sugges de prueba", "forma de probar las asocicaciones", 12, participant);

		participantVoto = new Participant("dueño", "comentario", "diaz", new Date(1993, 11, 12), "pepe@gmail.com",
				"7654321", "Calle Uria", "ESP", false, false);
		voto = new VoteSuggestion(participantVoto, suggestion, VoteType.POSITIVE);
	}

	@Test
	public void testVotarSuggestionLinked() {
		assertTrue(participantVoto.getVotesSuggestion().contains(voto));
		assertTrue(suggestion.getVotesSuggestion().contains(voto));

		assertTrue(voto.getParticipant().equals(participantVoto));
		assertTrue(voto.getSuggestion().equals(suggestion));
	}

	@Test
	public void testVotarSuggestionUnLink() {
		voto.deleteVoteSuggestion();

		assertFalse(participantVoto.getVotesSuggestion().contains(voto));
		assertFalse(suggestion.getVotesSuggestion().contains(voto));

		assertTrue(voto.getParticipant() == null);
		assertTrue(voto.getSuggestion() == null);

		assertTrue(participantVoto.getVotesSuggestion().size() == 0);
		assertTrue(suggestion.getVotesSuggestion().size() == 0);
	}

	@Test
	public void testSafeReturn() {
		Set<VoteSuggestion> votos = participantVoto.getVotesSuggestion();
		votos.remove(voto);

		assertTrue(votos.size() == 0);
		assertTrue("Se debe de retornar una copia de la coleccion o hacerla de solo lectura",
				participantVoto.getVotesSuggestion().size() == 1);

		votos = suggestion.getVotesSuggestion();
		votos.remove(voto);
		assertTrue(votos.size() == 0);
		assertTrue("Se debe de retornar una copia de la coleccion o hacerla de solo lectura",
				suggestion.getVotesSuggestion().size() == 1);
	}
}
