package dbManagement.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import asw.Application;
import asw.dbManagement.CommentService;
import asw.dbManagement.ParticipantService;
import asw.dbManagement.SuggestionService;
import asw.dbManagement.model.Comment;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommentServiceImplTest {

	@Autowired
	private CommentService service;

	@Autowired
	private ParticipantService ps;

	@Autowired
	private SuggestionService ss;

	@Test
	public void testGetCommentById() {
		Comment c1 = service.getAllComments().get(0);
		Long id = c1.getId();
		Comment c2 = service.findCommentById(id);
		assertEquals(c1.getIdentificador(), c2.getIdentificador());
		assertEquals(c1.getTexto(), c2.getTexto());
	}

	@Test
	public void testGetAllComments() {
		List<Comment> list = service.getAllComments();
		assertEquals(list.size(), list.size());
	}

	@Test
	public void testGetCommentsByParticipant() {
		List<Comment> list = service.getCommentsByParticipant(ps.getParticipant("pepe@participant.es", "12345"));
		assertEquals(list.size(), list.size());
	}

	@Test
	public void testGetCommentsBySuggestion() {
		List<Comment> list = service.getCommentsBySuggestion(ss.getSuggestionById(new Long(81)));
		assertEquals(list.size(), list.size());
	}

	@Test
	public void testSaveComment() {
		String identificador = "IdentificadorTest";
		List<Comment> list = service.getAllComments();
		identificador = identificador + list.size();
		Comment c = service.saveComment(new Comment(identificador, "test",
				ps.getParticipant("pepe@participant.es", "12345"), ss.getAllSuggestions().get(0)));

		if (c != null) {
			assertEquals(identificador, c.getIdentificador());
			assertEquals("test", c.getTexto());
		}
	}
}
