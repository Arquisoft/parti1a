package asw.dashboard.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import asw.Application;
import asw.dashboard.EventController;
import asw.streamKafka.productor.util.Topics;

@Controller
public class EventControllerImpl implements EventController {
	private List<SseEmitter> sseEmitters = Collections.synchronizedList(new ArrayList<>());

	@RequestMapping(value = "/newSuggestion")
	@KafkaListener(topics = Topics.NEW_SUGGESTION)
	public void newSuggestion(String data) {
		SseEventBuilder event = SseEmitter.event().name("newSuggestion").data(data);
		sendData(event);
	}

	@RequestMapping(value = "/deleteSuggestion")
	@KafkaListener(topics = Topics.DELETE_SUGGESTION)
	public void deleteSuggestion(String data) {
		SseEventBuilder event = SseEmitter.event().name("deleteSuggestion").data(data);
		sendData(event);

		Application.logger.info("Enviado evento sugerencia eliminada");
	}

	@RequestMapping(value = "/alertSuggestion")
	@KafkaListener(topics = Topics.ALERT_SUGGESTION)
	public void alertSuggestion(String data) {
		SseEventBuilder event = SseEmitter.event().name("alertSuggestion").data(data);
		sendData(event);
	}

	@RequestMapping(value = "/voteSuggestion")
	@KafkaListener(topics = Topics.POSITIVE_SUGGESTION)
	public void voteSuggestion(String data) {
		SseEventBuilder event = SseEmitter.event().name("voteSuggestion").data(data);
		sendData(event);
	}

	@RequestMapping(value = "/newComment")
	@KafkaListener(topics = Topics.NEW_COMMENT)
	public void newComment(String data) {
		SseEventBuilder event = SseEmitter.event().name("newComment").data(data);
		sendData(event);
	}

	@RequestMapping(value = "/positiveComment")
	@KafkaListener(topics = Topics.POSITIVE_COMMENT)
	public void positiveComment(String data) {
		SseEventBuilder event = SseEmitter.event().name("positiveComment").data(data);
		sendData(event);
	}

	@RequestMapping(value = "/negativeComment")
	@KafkaListener(topics = Topics.NEGATIVE_COMMENT)
	public void negativeComment(String data) {
		SseEventBuilder event = SseEmitter.event().name("negativeComment").data(data);
		sendData(event);
	}

	/************** METODOS AUXILIARES *************/
	@CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping("/dashboardAdmin/updates")
	SseEmitter updateHTML() {
		SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
		synchronized (this.sseEmitters) {
			this.sseEmitters.add(sseEmitter);
			sseEmitter.onCompletion(() -> {
				synchronized (this.sseEmitters) {
					this.sseEmitters.remove(sseEmitter);
				}
			});
		}
		return sseEmitter;
	}

	void sendData(SseEventBuilder event) {
		synchronized (this.sseEmitters) {
			for (SseEmitter sseEmitter : this.sseEmitters) {
				try {
					sseEmitter.send(event);
				} catch (IOException e) {
					sseEmitter = new SseEmitter(Long.MAX_VALUE);
					Application.logger.error("Se ha cerrado el stream actual");
				}
			}
		}
	}
}
