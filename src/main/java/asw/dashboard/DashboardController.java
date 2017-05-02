package asw.dashboard;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

public interface DashboardController {
	public String showSuggestions(HttpSession session, Model model);

	public String showGraphics(HttpSession session, Model model);

	public String showComments(HttpSession session, @PathVariable("id") String id, Model model);
}
