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
import asw.dbManagement.ParticipantService;
import asw.dbManagement.model.Participant;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParticipantServiceImplTest {

	@Autowired
	private ParticipantService service;

	@Test
	public void testGetParticipant() {
		Participant p = service.getParticipant("pepe@participant.es", "12345");
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
	public void testSupportSuggestion() {
		boolean result = service.supportSuggestion(new Long(1), new Long(1));
		if (result)
			assertEquals(true, result);
		else
			assertEquals(false, result);
	}

	@Test
	public void testSupportCommentPositive() {
		boolean result = service.supportCommentPositive(new Long(1), new Long(2));
		if (result)
			assertEquals(true, result);
		else
			assertEquals(false, result);
	}

	@Test
	public void testSupportCommentNegative() {
		boolean result = service.supportCommentNegative(new Long(1), new Long(1));
		if (result)
			assertEquals(true, result);
		else
			assertEquals(false, result);

	}

}
