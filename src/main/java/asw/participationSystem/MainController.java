package asw.participationSystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import asw.dbManagement.ParticipantService;
import asw.dbManagement.SuggestionService;
import asw.dbManagement.model.Suggestion;

@Controller
public class MainController {
	@Autowired
	SuggestionService suggestionService;

	@Autowired
	ParticipantService participantService;

	@RequestMapping("/index")
	public String index(Model model) {
		List<Suggestion> sugerencias = suggestionService.getVotables();
		model.addAttribute("sugerencias", sugerencias);
		return "index";
	}

	@ExceptionHandler(Exception.class)
	public String errorPage() {
		return "error";
	}
}