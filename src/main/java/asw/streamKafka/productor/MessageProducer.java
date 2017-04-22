package asw.streamKafka.productor;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import asw.Application;
import asw.dbManagement.model.Comment;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;
import asw.dbManagement.model.types.SuggestionState;
import asw.dbManagement.model.types.VoteType;
import asw.dbManagement.repository.CommentaryRepository;
import asw.dbManagement.repository.ParticipantRepository;
import asw.dbManagement.repository.SuggestionRepository;

// Productor de prueba
@ManagedBean
public class MessageProducer {
	// Para generar codigos aleatorios alfanumericos
	private SecureRandom random = new SecureRandom();

	@Autowired
	KafkaTemplate<String, String> template;

	@Autowired
	SuggestionRepository suggestionRepository;
	@Autowired
	CommentaryRepository commentaryRepository;
	@Autowired
	ParticipantRepository participantRepository;

	@Scheduled(cron = "*/10 * * * * *")
	public void sendNewSuggestion() {
		Participant p = participantRandom();
		String identificador = nextId();
		String title = "prueba";

		if (p != null) {
			suggestionRepository.save(new Suggestion(identificador, title, "prueba de sugerencia", 2, p));

			// Identificador de la sugerencia y titulo 
			send(Topics.NEW_SUGGESTION, "{ \"suggestion\":\"" + identificador + "\", \"title\":\""+ title +"\"}");
		}
	}
	
//	@Scheduled(cron = "*/30 * * * * *")
//	public void sendDeleteSuggestion() {
//		Suggestion s = suggestionRandom();
//		
//		if (s != null) {
//			suggestionRepository.delete(s.getId());
//
//			// Identificador de la sugerencia
//			send(Topics.DELETE_SUGGESTION, "{ \"suggestion\":\"" + s.getIdentificador() + "\" }");
//			
//			Application.logger.info("Sugerencia eliminada " + s.getIdentificador());
//		}
//	}

	@Scheduled(cron = "*/15 * * * * *")
	public void sendPositiveSuggestion() {
		Suggestion s = suggestionRandom();

		if (s != null) {
			s.incrementarNumeroVotos(VoteType.POSITIVE);
			suggestionRepository.save(s);

			// Identificador de la sugerencia
			send(Topics.POSITIVE_SUGGESTION, "{ \"suggestion\":\"" + s.getIdentificador() + "\"}");

			if (s.getVotosPositivos() == s.getVotosMinimos() 
					&& !s.getEstado().equals(SuggestionState.Aceptada)) {
				s.setEstado(SuggestionState.Aceptada);
				suggestionRepository.save(s);

				// Identificador de la sugerencia
				send(Topics.ALERT_SUGGESTION, "{ \"suggestion\":\"" + s.getIdentificador() + "\"}");
				Application.logger.info("Alerta a" + s.getIdentificador());
			}

			Application.logger
					.info("Voto a " + s.getIdentificador() + ", nº votos " + s.getVotosPositivos());
		}
	}

	@Scheduled(cron = "*/20 * * * * *")
	public void sendNewComment() {
		Suggestion s = suggestionRandom();
		Participant p = participantRandom();
		String identificador = nextId();

		if (s != null && p != null) {
			commentaryRepository.save(new Comment(identificador, "prueba", p, s));
			suggestionRepository.save(s);

			// Identificador del comentario y de la sugerencia
			String message = "{ \"comment\":\"" + identificador + "\", \"suggestion\":\""
					+ s.getIdentificador() + "\"}";
			send(Topics.NEW_COMMENT, message);

			Application.logger.info("Comentario en " + s.getIdentificador() + ", nº comentarios "
					+ s.getNumComments());
		}
	}

	@Scheduled(cron = "*/23 * * * * *")
	public void sendPositiveComment() {
		Comment c = commentRandom();

		if (c != null) {
			// Identificador del comentario y de la sugerencia
			String message = "{ \"comment\":\"" + c.getIdentificador() + "\", \"suggestion\":\""
					+ c.getSuggestion().getIdentificador() + "\"}";
			send(Topics.POSITIVE_COMMENT, message);
		}
	}

	@Scheduled(cron = "*/26 * * * * *")
	public void sendNegativeComment() {
		Comment c = commentRandom();

		if (c != null) {
			// Identificador del comentario y de la sugerencia
			String message = "{ \"comment\":\"" + c.getIdentificador() + "\", \"suggestion\":\""
					+ c.getSuggestion().getIdentificador() + "\"}";
			send(Topics.NEGATIVE_COMMENT, message);
		}
	}

	private void send(String topic, String message) {
		ListenableFuture<SendResult<String, String>> future = template.send(topic, message);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				Application.logger.info("...enviando mensaje " + topic);
			}

			@Override
			public void onFailure(Throwable ex) {
				Application.logger.error("ERROR: enviando en " + topic);
			}
		});
	}

	/********************* METODOS AUXILIARES ***********************/

	private String nextId() {
		return new BigInteger(130, random).toString(32);
	}

	private Suggestion suggestionRandom() {
		int size = suggestionRepository.findAll().size();
		int s;
		if (size > 0) {
			s = randomInt(0, size - 1);
			return suggestionRepository.findAll().get(s);
		}
		return null;
	}

	private Participant participantRandom() {
		int size = participantRepository.findAll().size();
		int s;
		if (size > 0) {
			s = randomInt(0, size - 1);
			return participantRepository.findAll().get(s);
		}
		return null;
	}

	private Comment commentRandom() {
		int size = commentaryRepository.findAll().size();
		int s;
		if (size > 0) {
			s = randomInt(0, size - 1);
			return commentaryRepository.findAll().get(s);
		}
		return null;
	}

	private int randomInt(int min, int max) {
		Random rn = new Random();
		return rn.nextInt(max - min + 1) + min;
	}
}
