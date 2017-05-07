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
import asw.dbManagement.UpdateInfo;
import asw.dbManagement.model.Participant;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateInfoImplTest {

	@Autowired
	private UpdateInfo update;

	@Autowired
	private ParticipantService ps;

	@Test
	public void testUpdateEmail() {
//		update.updateEmail(ps.getParticipant("pepe@participant.es", "12345"), "pepe@participant.com");
//
//		update.updateEmail(ps.getParticipant("pepe@participant.com", "12345"), "pepe@participant.es");
	}

	@Test
	public void testUpdatePassword() {
		// Participant p = ps.getParticipant("pepe@participant.es", "12345");
		// update.updatePassword(p, "12345", "78901");
		// assertEquals("Pepe", p.getNombre());
		// assertEquals("78901", p.getPassword());
		//
		// update.updatePassword(p, "78901", "12345");
	}
}
