package dbManagement.impl;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import asw.Application;
import asw.dbManagement.CategoryService;
import asw.dbManagement.CommentService;
import asw.dbManagement.ParticipantService;
import asw.dbManagement.SuggestionService;
import asw.dbManagement.model.Comment;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;
import asw.dbManagement.model.types.VoteType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParticipantServiceImplTest {

	@Autowired
	private ParticipantService serviceParticipant;

	@Autowired
	private SuggestionService serviceSuggestion;

	@Autowired
	private CommentService serviceComment;

	@Autowired
	private CategoryService serviceCategory;

	private Participant p;
	private Suggestion s;
	private Comment c1;
	private Comment c2;

	@Test
	public void t1_testGetParticipant() {
		Participant p = serviceParticipant.getParticipant("pepe@participant.es", "12345");
		assertEquals("Pepe", p.getNombre());
		assertEquals(new Long(1), p.getId());
		assertEquals("Gonzalez", p.getApellidos());
		assertEquals("pepe@participant.es", p.getEmail());
		assertEquals(new Long(1), p.getId());
		assertEquals("66543245T", p.getDNI());
		assertEquals("Uria", p.getDireccion());
		assertEquals("española", p.getNacionalidad());
		assertEquals(false, p.isAdmin());
		assertEquals(false, p.isPolitician());
	}

	@Test
	public void t2_testSuggestionAndComments() {
		p = serviceParticipant.getParticipant("pepe@participant.es", "12345");
		createSuggestion();
		createComments();
		
		boolean result = serviceParticipant.supportSuggestion(new Long(1), s.getId());
		assertEquals(true, result);
		
		c1.incrementarNumeroVotos(VoteType.POSITIVE);
		result = serviceParticipant.supportCommentPositive(new Long(1), c1.getId());
		assertEquals(true, result);
		
		c2.incrementarNumeroVotos(VoteType.NEGATIVE);
		result = serviceParticipant.supportCommentNegative(new Long(1), c2.getId());
		assertEquals(true, result);
		
		serviceSuggestion.deleteSuggestion(s);
	}

	private void createSuggestion() {
		String identificador = "IdentificadorTest" + serviceSuggestion.getAllSuggestions().size();
		s = serviceSuggestion.saveSuggestion(new Suggestion(identificador, "prueba", "prueba test",
				p, serviceCategory.getCategoryById(new Long(17))));
	}

	private void createComments() {
		String identificador = "Pruebas";
		int num = serviceComment.getAllComments().size();
		c1 = serviceComment.saveComment(new Comment(identificador + num, "test1", p, s));
		c2 = serviceComment.saveComment(new Comment(identificador + num + 1, "test2", p, s));
	}
}
