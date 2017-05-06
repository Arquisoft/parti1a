package cucmber.steps;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import asw.Application;
import asw.dbManagement.CommentService;
import asw.dbManagement.model.Comment;
import asw.streamKafka.productor.KafkaProducer;
import cucumber.api.java.es.Entonces;

@SuppressWarnings("deprecation")
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@IntegrationTest
@WebAppConfiguration
public class ActualizarDashboard2Steps {
	private static SecureRandom random = new SecureRandom();

	@Autowired
	private CommentService commentService;

	@Autowired
	private KafkaProducer kafka;

	private Comment c;

	@Entonces("^puede ver nuevos comentarios$")
	public void puede_ver_nuevos_comentarios() throws Throwable {
		newComment();
		// TODO falta comprobar html
	}

	@Entonces("^puede ver el numero de votos positivos$")
	public void puede_ver_el_numero_de_votos_positivos() throws Throwable {
		positiveVote(c.getIdentificador());
		// TODO falta comprobar html
	}

	@Entonces("^puede ver el numero de votos negativos$")
	public void puede_ver_el_numero_de_votos_negativos() throws Throwable {
		negativeVote(c.getIdentificador());
		// TODO falta comprobar html
	}

	private void newComment() {
		c = new Comment(nextId(), "prueba comentario", ActualizarDashboard1Steps.s.getParticipant(),
				ActualizarDashboard1Steps.s);
		commentService.saveComment(c);
		kafka.sendNewComment(c.getIdentificador(), ActualizarDashboard1Steps.s.getIdentificador());
	}

	private void positiveVote(String ident) {
		kafka.sendPositiveComment(ident, ActualizarDashboard1Steps.s.getIdentificador());
	}

	private void negativeVote(String ident) {
		kafka.sendNegativeComment(ident, ActualizarDashboard1Steps.s.getIdentificador());
	}

	private String nextId() {
		return new BigInteger(130, random).toString(32);
	}
}
