package asw.dashboard.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import asw.dashboard.DashboardController;
import asw.dashboard.util.Validate;
import asw.dbManagement.repository.SuggestionRepository;

@Controller
@RequestMapping("/dashboardAdmin")
public class DashboardControllerAdminImpl implements DashboardController{
	@Autowired
	private SuggestionRepository suggestionRepository;

	/**
	 * Inicio del dashboard que muestra las sugerencias para admin
	 */
	@RequestMapping("/dashboard")
	public String showSuggestions(HttpSession session, Model model) {
		Validate.validateAdmin(session);
		model.addAttribute("allSuggestions", suggestionRepository.findAll());
		return "admin/dashboard/suggestions";
	}

	/**
	 * Muestra las graficas
	 */
	@RequestMapping("/graphics")
	public String showGraphics(HttpSession session, Model model) {
		Validate.validateAdmin(session);
		model.addAttribute("allSuggestions",
				suggestionRepository.findAll().stream().map(s -> s.getIdentificador()).toArray());
		model.addAttribute("allVotes",
				suggestionRepository.findAll().stream().map(s -> s.getVotosPositivos()).toArray());
		return "admin/dashboard/graficas";
	}

	/**
	 * Comentarios de una sugerencia
	 */
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public String showComments(HttpSession session, @PathVariable("id") String id, Model model) {
		Validate.validateAdmin(session);
		model.addAttribute("suggestionId", id);
		model.addAttribute("allComments",
				suggestionRepository.findByIdentificador(id).getCommentaries());
		return "admin/dashboard/comments";
	}

}