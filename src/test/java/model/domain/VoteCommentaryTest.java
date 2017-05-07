package model.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import asw.dbManagement.model.Comment;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;
import asw.dbManagement.model.VoteComment;
import asw.dbManagement.model.types.VoteType;

public class VoteCommentaryTest {

	private Participant participant1, participant2, participant3;
	private Suggestion suggestion1, suggestion2, suggestion3;
	private Comment comentario1, comentario2, comentario3;
	private VoteComment voto1, voto2, voto3, voto4, voto5, voto6, voto7, voto8, voto9;

	@Before
	public void setUp() {
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
		// Creacion comentarios
		comentario1 = new Comment("vihvdfibwe", "comenario prueba 1 sobre suggestion1", participant1, suggestion1);
		comentario2 = new Comment("vifjbjjdoe", "comenario prueba 2 sobre suggestion2", participant2, suggestion2);
		comentario3 = new Comment("vifjbjjdoe", "comenario prueba 3 sobre suggestion3", participant3, suggestion3);
		// Creacion voto comentario
		voto1 = new VoteComment(participant1, comentario1, VoteType.NEGATIVE);
		voto2 = new VoteComment(participant2, comentario1, VoteType.POSITIVE);
		voto3 = new VoteComment(participant3, comentario1, VoteType.POSITIVE);

		voto4 = new VoteComment(participant1, comentario2, VoteType.NEGATIVE);
		voto5 = new VoteComment(participant2, comentario2, VoteType.POSITIVE);
		voto6 = new VoteComment(participant3, comentario2, VoteType.POSITIVE);

		voto7 = new VoteComment(participant1, comentario3, VoteType.NEGATIVE);
		voto8 = new VoteComment(participant2, comentario3, VoteType.POSITIVE);
		voto9 = new VoteComment(participant3, comentario3, VoteType.POSITIVE);
	}

	@Test
	public void testNumberVotesCommentary1() {
		assertTrue(comentario1.getVotesCommentary().size() == 3);
		assertTrue(participant1.getVotesCommentary().size() == 3);

		assertTrue(comentario1.getVotosPositivos() == 2);
		assertTrue(comentario1.getVotosNegativos() == 1);
	}

	@Test
	public void testNumberVotesCommentary2() {
		assertTrue(comentario2.getVotesCommentary().size() == 3);
		assertTrue(participant2.getVotesCommentary().size() == 3);

		assertTrue(comentario2.getVotosPositivos() == 2);
		assertTrue(comentario2.getVotosNegativos() == 1);
	}

	@Test
	public void testNumberVotesCommentary3() {
		assertTrue(comentario3.getVotesCommentary().size() == 3);
		assertTrue(participant3.getVotesCommentary().size() == 3);

		assertTrue(comentario3.getVotosPositivos() == 2);
		assertTrue(comentario3.getVotosNegativos() == 1);
	}

	@Test
	public void testNumberVotesComentario1DeleteVote() {
		voto1.deleteVoteCommentary();

		assertTrue(comentario1.getVotesCommentary().size() == 2);

		assertTrue(comentario1.getVotosPositivos() == 2);
		assertTrue(comentario1.getVotosNegativos() == 0);

		voto2.deleteVoteCommentary();

		assertTrue(comentario1.getVotesCommentary().size() == 1);

		assertTrue(comentario1.getVotosPositivos() == 1);
		assertTrue(comentario1.getVotosNegativos() == 0);

		voto3.deleteVoteCommentary();

		assertTrue(comentario1.getVotesCommentary().size() == 0);

		assertTrue(comentario1.getVotosPositivos() == 0);
		assertTrue(comentario1.getVotosNegativos() == 0);
	}

	@Test
	public void testNumberVotesComentario2DeleteVote() {
		voto4.deleteVoteCommentary();

		assertTrue(comentario2.getVotesCommentary().size() == 2);

		assertTrue(comentario2.getVotosPositivos() == 2);
		assertTrue(comentario2.getVotosNegativos() == 0);

		voto5.deleteVoteCommentary();

		assertTrue(comentario2.getVotesCommentary().size() == 1);

		assertTrue(comentario2.getVotosPositivos() == 1);
		assertTrue(comentario2.getVotosNegativos() == 0);

		voto6.deleteVoteCommentary();

		assertTrue(comentario2.getVotesCommentary().size() == 0);

		assertTrue(comentario2.getVotosPositivos() == 0);
		assertTrue(comentario2.getVotosNegativos() == 0);
	}

	@Test
	public void testNumberVotesComentario3DeleteVote() {
		voto7.deleteVoteCommentary();

		assertTrue(comentario3.getVotesCommentary().size() == 2);

		assertTrue(comentario3.getVotosPositivos() == 2);
		assertTrue(comentario3.getVotosNegativos() == 0);

		voto8.deleteVoteCommentary();

		assertTrue(comentario3.getVotesCommentary().size() == 1);

		assertTrue(comentario3.getVotosPositivos() == 1);
		assertTrue(comentario3.getVotosNegativos() == 0);

		voto9.deleteVoteCommentary();

		assertTrue(comentario3.getVotesCommentary().size() == 0);

		assertTrue(comentario3.getVotosPositivos() == 0);
		assertTrue(comentario3.getVotosNegativos() == 0);
	}

	@Test
	public void testMetodosComentario() {
		comentario1.setTexto("prueba");
		comentario1.setVotosPositivos(100);
		comentario1.setVotosNegativos(10);
		comentario1.setValoracion(0);
		Date d = new Date();
		comentario1.setFechaCreacion(d);

		assertTrue(comentario1.getTexto().equals("prueba"));
		assertTrue(comentario1.getVotosPositivos() == 100);
		assertTrue(comentario1.getVotosNegativos() == 10);
		assertTrue(comentario1.getIdentificador().equals("vihvdfibwe"));
		assertTrue(comentario1.getValoracion() == 0);
		assertTrue(comentario1.getFechaCreacion().equals(d));

		voto1.setVoteType(VoteType.NEGATIVE);
		assertTrue(voto1.getVoteType().equals(VoteType.NEGATIVE));
	}

	@Test
	public void testMetodoEqualsHashCode() {
		assertFalse(comentario1.equals(suggestion2));
		assertFalse(comentario1.equals(4));
		assertFalse(comentario1.equals(null));
		assertTrue(comentario1.equals(comentario1));
		assertTrue(comentario2.equals(comentario3));

		assertFalse(voto1.equals(voto2));
		assertFalse(voto1.equals(4));
		assertFalse(voto1.equals(null));
		assertTrue(voto1.equals(voto1));
	}

	@Test
	public void testVoteSuggestionToString() {
		assertEquals(voto1.toString(),
				"VoteCommentary [participant=" + participant1 + ", comentary=" + comentario1 + "]");
	}
}