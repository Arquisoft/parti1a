package model.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;
import asw.dbManagement.model.VoteSuggestion;
import asw.dbManagement.model.types.VoteType;

public class VoteSuggestionTest {

	private Participant participant1, participant2, participant3;
	private Suggestion suggestion1, suggestion2, suggestion3;
	private VoteSuggestion vote1, vote2, vote3, vote4, vote5, vote6, vote7, vote8, vote9;

	@Before
	public void setUp() {
		// Creacion participantes
		participant1 = new Participant("pepe", "arriaga", "123456", new Date(), "pepe@gmail.com", "1234567",
				"calle mon", "ESP", false, false);
		participant2 = new Participant("juan", "manuel", "123456", new Date(), "juan@hotmail.com", "654321",
				"calle uria", "FRA", false, true);
		participant3 = new Participant("jose", "garcia", "123456", new Date(), "jose@msn.com", "1285418", "Rua Peru",
				"GBR", true, false);
		// Creacion propuestas
		suggestion1 = new Suggestion("1352479651675846", "Botellon", "Eliminar prohibicion al botellon", 100,
				participant1);
		suggestion2 = new Suggestion("1352468547865643", "Permitir pintadas", "Legalizar las pintadas en las fachadas",
				10000, participant2);
		suggestion3 = new Suggestion("87946543127849+123798", "Fiestas universitarias",
				"Incrementar el número de fiestas techno en la universidad", 10, participant3);
		// Creacion votos a las propuestas
		vote1 = new VoteSuggestion(participant1, suggestion1, VoteType.POSITIVE);
		vote2 = new VoteSuggestion(participant2, suggestion1, VoteType.POSITIVE);
		vote3 = new VoteSuggestion(participant3, suggestion1, VoteType.NEGATIVE);

		vote4 = new VoteSuggestion(participant1, suggestion2, VoteType.NEGATIVE);
		vote5 = new VoteSuggestion(participant2, suggestion2, VoteType.NEGATIVE);
		vote6 = new VoteSuggestion(participant3, suggestion2, VoteType.NEGATIVE);

		vote7 = new VoteSuggestion(participant1, suggestion3, VoteType.POSITIVE);
		vote8 = new VoteSuggestion(participant2, suggestion3, VoteType.POSITIVE);
		vote9 = new VoteSuggestion(participant3, suggestion3, VoteType.POSITIVE);
	}

	@Test
	public void testNumberVotesSuggestion1() {
		assertTrue(suggestion1.getVotesSuggestion().size() == 3);

		assertTrue(suggestion1.getVotosPositivos() == 2);
		assertTrue(suggestion1.getVotosNegativos() == 1);
	}

	@Test
	public void testNumberVotesSuggestion2() {
		assertTrue(suggestion2.getVotesSuggestion().size() == 3);

		assertTrue(suggestion2.getVotosPositivos() == 0);
		assertTrue(suggestion2.getVotosNegativos() == 3);
	}

	@Test
	public void testNumberVotesSuggestion3() {
		assertTrue(suggestion3.getVotesSuggestion().size() == 3);

		assertTrue(suggestion3.getVotosPositivos() == 3);
		assertTrue(suggestion3.getVotosNegativos() == 0);
	}

	@Test
	public void testNumberVotesSuggestion1DeleteVote() {
		vote1.deleteVoteSuggestion();

		assertTrue(suggestion1.getVotesSuggestion().size() == 2);

		assertTrue(suggestion1.getVotosPositivos() == 1);
		assertTrue(suggestion1.getVotosNegativos() == 1);

		vote2.deleteVoteSuggestion();
		assertTrue(suggestion1.getVotesSuggestion().size() == 1);

		assertTrue(suggestion1.getVotosPositivos() == 0);
		assertTrue(suggestion1.getVotosNegativos() == 1);

		vote3.deleteVoteSuggestion();
		assertTrue(suggestion1.getVotesSuggestion().size() == 0);

		assertTrue(suggestion1.getVotosPositivos() == 0);
		assertTrue(suggestion1.getVotosNegativos() == 0);
	}

	@Test
	public void testNumberVotesSuggestion2DeleteVote() {
		vote4.deleteVoteSuggestion();

		assertTrue(suggestion2.getVotesSuggestion().size() == 2);

		assertTrue(suggestion2.getVotosPositivos() == 0);
		assertTrue(suggestion2.getVotosNegativos() == 2);

		vote5.deleteVoteSuggestion();
		assertTrue(suggestion2.getVotesSuggestion().size() == 1);

		assertTrue(suggestion2.getVotosPositivos() == 0);
		assertTrue(suggestion2.getVotosNegativos() == 1);

		vote6.deleteVoteSuggestion();
		assertTrue(suggestion2.getVotesSuggestion().size() == 0);

		assertTrue(suggestion2.getVotosPositivos() == 0);
		assertTrue(suggestion2.getVotosNegativos() == 0);
	}

	@Test
	public void testNomberVotesSuggestion3DeleteVote() {
		vote7.deleteVoteSuggestion();

		assertTrue(suggestion3.getVotesSuggestion().size() == 2);

		assertTrue(suggestion3.getVotosPositivos() == 2);
		assertTrue(suggestion3.getVotosNegativos() == 0);

		vote8.deleteVoteSuggestion();
		assertTrue(suggestion3.getVotesSuggestion().size() == 1);

		assertTrue(suggestion3.getVotosPositivos() == 1);
		assertTrue(suggestion3.getVotosNegativos() == 0);

		vote9.deleteVoteSuggestion();
		assertTrue(suggestion3.getVotesSuggestion().size() == 0);

		assertTrue(suggestion3.getVotosPositivos() == 0);
		assertTrue(suggestion3.getVotosNegativos() == 0);
	}

	@Test
	public void testMetodosSuggestion() {
		suggestion1.setContenido("Eliminar prohibicion al botellones");
		suggestion1.setNombre("Botellones");
		suggestion1.setVotosMinimos(50);
		suggestion1.setVotosNegativos(10);
		suggestion1.setVotosPositivos(10);
		suggestion1.setAlert(true);

		assertTrue(suggestion1.getContenido().equals("Eliminar prohibicion al botellones"));
		assertTrue(suggestion1.getNombre().equals("Botellones"));
		assertTrue(suggestion1.getIdentificador().equals("1352479651675846"));
		assertTrue(suggestion1.getVotosMinimos() == 50);
		assertTrue(suggestion1.getVotosNegativos() == 10);
		assertTrue(suggestion1.getVotosPositivos() == 10);
		assertTrue(suggestion1.isAlert());

		vote1.setVoteType(VoteType.NEGATIVE);
		assertTrue(vote1.getVoteType().equals(VoteType.NEGATIVE));
	}

	@Test
	public void testMetodoEqualsHashCode() {
		assertFalse(suggestion1.equals(suggestion2));
		assertFalse(suggestion1.equals(4));
		assertFalse(suggestion1.equals(null));
		assertTrue(suggestion1.equals(suggestion1));
		
		assertFalse(vote1.equals(vote2));
		assertFalse(vote1.equals(4));
		assertFalse(vote1.equals(null));
		assertTrue(vote1.equals(vote1));
	}
	
	@Test
	public void testVoteSuggestionToString(){
		assertEquals(vote1.toString(),"VoteSuggestion [participant=" + participant1 + ", suggestion=" + suggestion1 + "]");
	}
}
