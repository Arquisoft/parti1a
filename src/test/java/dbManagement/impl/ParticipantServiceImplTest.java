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
import asw.dbManagement.CommentService;
import asw.dbManagement.ParticipantService;
import asw.dbManagement.model.Comment;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.types.VoteType;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParticipantServiceImplTest {

	@Autowired
	private ParticipantService service;
	
	@Autowired
	private CommentService cs;

	@Test
	public void t1_testGetParticipant() {
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
	public void t2_testSupportSuggestion() {
		boolean result = service.supportSuggestion(new Long(1), new Long(162));
		assertEquals(false, result);
	}

	@Test
	public void t3_testSupportCommentPositive() {
		Comment comm = cs.findCommentById(new Long(195));
		comm.incrementarNumeroVotos(VoteType.POSITIVE);
		boolean result = service.supportCommentPositive(new Long(1), comm.getId());
		assertEquals(false, result);
	}

	@Test
	public void t4_testSupportCommentNegative() {
		Comment comm = cs.findCommentById(new Long(194));
		comm.incrementarNumeroVotos(VoteType.NEGATIVE);
		boolean result = service.supportCommentNegative(new Long(1), comm.getId());
		assertEquals(false, result);

	}

}
