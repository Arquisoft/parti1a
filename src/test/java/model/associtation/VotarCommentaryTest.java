package model.associtation;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import asw.dbManagement.model.Commentary;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;
import asw.dbManagement.model.VoteCommentary;
import asw.dbManagement.model.types.VoteType;

public class VotarCommentaryTest {

	private Participant participant, participantComentario;
	private Suggestion suggestion;
	private VoteCommentary voto;
	private Commentary commentary;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		participant = new Participant("martin", "pelaez", "diaz", new Date(1993, 11, 12), "paco@gmail.com", "1234567",
				"Calle Uria", "ESP", false, false);
		suggestion = new Suggestion("prueba", "sugges de prueba", "forma de probar las asocicaciones", 12, participant);

		participantComentario = new Participant("dueño", "comentario", "diaz", new Date(1993, 11, 12), "pepe@gmail.com",
				"7654321", "Calle Uria", "ESP", false, false);
		commentary = new Commentary("comentario de prueba ", "esto es un comentario de prueba", participantComentario,
				suggestion);
		voto = new VoteCommentary(participant, commentary, VoteType.POSITIVE);
	}

	@Test
	public void testVotarCommentarioLinked() {
		assertTrue(participant.getVotesCommentary().contains(voto));
		assertTrue(commentary.getVotesCommentary().contains(voto));

		assertTrue(voto.getParticipant().equals(participant));
		assertTrue(voto.getCommentary().equals(commentary));
	}

	@Test
	public void testVotarSuggestionUnLink() {
		voto.deleteVoteCommentary();

		assertFalse(participant.getVotesCommentary().contains(voto));
		assertFalse(commentary.getVotesCommentary().contains(voto));

		assertTrue(voto.getParticipant() == null);
		assertTrue(voto.getCommentary() == null);

		assertTrue(participant.getVotesCommentary().size() == 0);
		assertTrue(commentary.getVotesCommentary().size() == 0);
	}

	@Test
	public void testSafeReturn() {
		Set<VoteCommentary> votos = participant.getVotesCommentary();
		votos.remove(voto);

		assertTrue(votos.size() == 0);
		assertTrue("Se debe de retornar una copia de la coleccion o hacerla de solo lectura",
				participant.getVotesCommentary().size() == 1);

		votos = commentary.getVotesCommentary();
		votos.remove(voto);
		assertTrue(votos.size() == 0);
		assertTrue("Se debe de retornar una copia de la coleccion o hacerla de solo lectura",
				commentary.getVotesCommentary().size() == 1);
	}

}
