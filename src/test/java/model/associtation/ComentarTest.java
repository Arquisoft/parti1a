package model.associtation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import asw.dbManagement.model.Commentary;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;

public class ComentarTest {
	
	private Participant participant,participantComentario;
	private Suggestion suggestion;
	private Commentary commentary;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		participant = new Participant("martin", "pelaez", "diaz", new Date(1993, 11, 12), "paco@gmail.com",
				"1234567", "Calle Uria", "ESP", false, false);
		suggestion = new Suggestion("prueba", "sugges de prueba", "forma de probar las asocicaciones", 12, participant);
		
		participantComentario = new Participant("dueño", "comentario", "diaz", new Date(1993, 11, 12), "pepe@gmail.com",
				"7654321", "Calle Uria", "ESP", false, false);
		
		commentary = new Commentary("comentario de prueba ", "esto es un comentario de prueba",participantComentario,suggestion);
	}

	@Test
	public void testComentarLinked() {
		assertTrue(participantComentario.getCommentaries().contains(commentary));
		assertTrue(suggestion.getCommentaries().contains(commentary));
		
		assertTrue(commentary.getParticipant().equals(participantComentario));
		assertTrue(commentary.getSuggestion().equals(suggestion));
	}
	
	@Test
	public void testComentarUnLink(){
		commentary.deleteComment();
		
		assertFalse(participantComentario.getCommentaries().contains(commentary));
		assertFalse(suggestion.getCommentaries().contains(commentary));
		
		assertTrue(commentary.getParticipant() == null);
		assertTrue(commentary.getSuggestion() == null);
		
		assertTrue(participantComentario.getCommentaries().size() == 0);
		assertTrue(suggestion.getCommentaries().size() == 0);
	}
	
	@Test
	public void testSafeReturn(){
		Set<Commentary> comentarios = participant.getCommentaries();
		comentarios.remove(commentary);
		
		assertTrue(comentarios.size() == 0);
		assertTrue("Se debe de retornar una copia de la coleccion o hacerla de solo lectura",
				participantComentario.getCommentaries().size() == 1);
		
		comentarios = suggestion.getCommentaries();
		comentarios.remove(commentary);
		assertTrue(comentarios.size() == 0);
		assertTrue("Se debe de retornar una copia de la coleccion o hacerla de solo lectura",
				suggestion.getCommentaries().size() == 1);
	}
}
