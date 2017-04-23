package asw.streamKafka.productor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.ManagedBean;

/**
 * 
 */
@ManagedBean
public class KafkaProducer {

	private static final Logger logger = Logger.getLogger(KafkaProducer.class);
	
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	// SENDS
	public void sendNewSuggestion(long suggestionId) {
		send(Topics.NEW_SUGGESTION, "Creada la propuesta -> " + suggestionId);
	}

	public void sendNewComment(long commentId) {
		send(Topics.NEW_COMMENT, "Creado el comentario -> " + commentId);
	}

	public void sendPositiveComment(long commentId) {
		send(Topics.POSITIVE_COMMENT, "Voto positivo en el comentario -> " + commentId);
	}

	public void sendNegativeComment(long commentId) {
		send(Topics.NEGATIVE_COMMENT, "Voto negativo en el comentario -> " + commentId);
	}

	public void sendPositiveSuggestion(long suggestionId) {
		send(Topics.POSITIVE_SUGGESTION, "Apoyo a la propuesta -> " + suggestionId);
	}

	public void sendMinVotesReached(long suggestionId) {
		send(Topics.MIN_VOTES_REACHED, "Se ha alcanzado el mínimo de votos -> " + suggestionId);
	}

	public void sendDeleteSuggestion(long suggestionId) {
		send(Topics.DELETE_SUGGESTION, "Borrada la propuesta -> " + suggestionId);
	}

	public void sendNewCategory(long catId) {
		send(Topics.NEW_CATEGORY, "Creada la categoria -> " + catId);
	}

	public void sendDeleteCategory(long catId) {
		send(Topics.DELETE_CATEGORY, "Borrada la categoria -> " + catId);
	}

	public void sendDeniedSuggestion(long suggestionId) {
		send(Topics.DENIED_SUGGESTION, "Denegada la propuesta -> " + suggestionId);
	}

	public void send(String topic, String data) {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data);
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("Success on sending message \"" + data + "\" to topic " + topic);
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Error on sending message \"" + data + "\", stacktrace " + ex.getMessage());
			}
		});
	}

}
