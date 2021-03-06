package asw.participationSystem;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import asw.dbManagement.ParticipantService;
import asw.dbManagement.SuggestionService;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;
import asw.streamKafka.productor.KafkaProducerImpl;

@Controller
public class VoteController {

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private SuggestionService suggestionService;

	@Autowired
	private KafkaProducerImpl kafka;

	@RequestMapping("/support")
	public String votingUp(@RequestParam("sugerencia") Long id, HttpSession session, Model model) {
		if (!participantService.supportSuggestion(((Participant) session.getAttribute("usuario")).getId(), id))
			model.addAttribute("mensaje", "Ya has votado esta sugerencia anteriormente");
		else {
			model.addAttribute("mensaje", "");
			
			Suggestion s = suggestionService.getSuggestionById(id);
			// Enviar aviso a kafka
			kafka.sendPositiveSuggestion(s.getIdentificador());
			
			// Enviar alerta
			if (s.getVotosPositivos() == s.getVotosMinimos())
				kafka.sendAlertSuggestion(s.getIdentificador());
		}
		return "redirect:/index";
	}

}
