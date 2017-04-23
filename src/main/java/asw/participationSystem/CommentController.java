package asw.participationSystem;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import asw.dbManagement.CommentService;
import asw.dbManagement.ParticipantService;
import asw.dbManagement.SuggestionService;
import asw.dbManagement.WordService;
import asw.dbManagement.model.Comment;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;
import asw.dbManagement.model.Word;
import asw.streamKafka.productor.KafkaProducer;

@Controller
public class CommentController {
	
	private SecureRandom random = new SecureRandom();

	@Autowired
	private CommentService commentService;

	@Autowired
	private SuggestionService suggestionService;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private WordService wordService;

	@Autowired
	private KafkaProducer kafka;

	@RequestMapping("/comments")
	public String showComments(@RequestParam("sugerencia") Long id, HttpSession session, Model model) {
		session.setAttribute("idSuggestion", id);
		return "redirect:/listComments";
	}

	@RequestMapping("/listComments")
	public String listComments(Model model, HttpSession session) {
		Long id = (Long) session.getAttribute("idSuggestion");
		List<Comment> comentarios = commentService.getCommentsBySuggestion(suggestionService.getSuggestionById(id));
		model.addAttribute("comentarios", comentarios);
		return "comments";
	}

	@RequestMapping("/votarPositivo")
	public String votingPositive(@RequestParam("comentario") Long id, HttpSession session, Model model) {
		if (!participantService.supportCommentPositive(((Participant) session.getAttribute("usuario")).getId(), id))
			model.addAttribute("mensaje", "Ya has votado este comentario anteriormente");
		else {
			model.addAttribute("mensaje", "Ha votado like a este comentario");
			long suggestionId = commentService.findCommentById(id).getSuggestion().getId();
			kafka.sendPositiveComment(id, suggestionId);
		}
		return "redirect:/listComments";
	}

	@RequestMapping("/votarNegativo")
	public String votingNegative(@RequestParam("comentario") Long id, HttpSession session, Model model) {
		if (!participantService.supportCommentNegative(((Participant) session.getAttribute("usuario")).getId(), id))
			model.addAttribute("mensaje", "Ya has votado este comentario anteriormente");
		else {
			model.addAttribute("mensaje", "Ha votado dislike a este comentario");
			long suggestionId = commentService.findCommentById(id).getSuggestion().getId();
			kafka.sendNegativeComment(id, suggestionId);
		}
		return "redirect:/listComments";
	}

	@RequestMapping("/comment")
	public String comment(@RequestParam String comment, HttpSession session, Model model) {
		if (comment.equals("")) {
			model.addAttribute("mensaje", "No ha escrito nada");
		} else {
			List<Word> words = wordService.getAllWords();
			for (int i = 0; i < words.size(); i++) {
				if (comment.contains(words.get(i).getWord())) {
					model.addAttribute("mensaje", "El comentario contiene palabras prohibidas");
					return "redirect:listComments";
				}
			}
			String identificador = nextId();
			Participant p = (Participant) session.getAttribute("usuario");
			Suggestion s = suggestionService.getSuggestionById((Long) session.getAttribute("idSuggestion"));
			Comment c = commentService.saveComment(new Comment(identificador,comment, p, s));
			kafka.sendNewComment(c.getId(), s.getId());

		}
		return "redirect:/listComments";
	}
	
	private String nextId() {
		return new BigInteger(130, random).toString(32);
	}
}
