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
import asw.dbManagement.CategoryService;
import asw.dbManagement.ParticipantService;
import asw.dbManagement.SuggestionService;
import asw.dbManagement.model.Suggestion;
import asw.dbManagement.model.types.SuggestionState;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SuggestionServiceImplTest {

	@Autowired
	private SuggestionService service;

	@Autowired
	private ParticipantService ps;

	@Autowired
	private CategoryService cs;

	@Test
	public void testGetAllSuggestions() {
		List<Suggestion> list = service.getAllSuggestions();
		assertEquals(list.size(), list.size());
	}

	@Test
	public void testGetSuggestionById() {
		Suggestion s1 = service.getAllSuggestions().get(0);
		Suggestion s2 = service.getSuggestionById(s1.getId());
		assertEquals(s1.getDescripcion(), s2.getDescripcion());
		assertEquals(s1.getId(), s2.getId());
		assertEquals(s1.getIdentificador(), s2.getIdentificador());

	}

	@Test
	public void testGetSuggestionsByStatus() {
		List<Suggestion> list = service.getSuggestionByStatus(SuggestionState.BuscandoApoyo);
		assertEquals(list.size(), list.size());
	}

	@Test
	public void testGetSuggestionsByTitle() {
		List<Suggestion> list = service.getSuggestionByTitle("prueba");
		assertEquals(list.size(), list.size());
	}

	@Test
	public void testGetVotables() {
		List<Suggestion> list = service.getVotables();
		assertEquals(list.size(), list.size());
	}

	@Test
	public void testSaveAndDelete() {
		Suggestion s = service.saveSuggestion(new Suggestion("IdentificadorTest", "prueba", "prueba test",
				ps.getParticipant("pepe@participant.es", "12345"), cs.getCategoryById(new Long(1))));
		assertEquals("prueba test", s.getDescripcion());
		assertEquals("IdentificadorTest", s.getIdentificador());
		assertEquals("prueba", s.getTitulo());

		// Delete

		service.deleteSuggestion(s);
	}
}
