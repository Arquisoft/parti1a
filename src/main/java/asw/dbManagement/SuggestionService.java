package asw.dbManagement;

import java.util.List;

import asw.dbManagement.model.Suggestion;
import asw.dbManagement.model.types.SuggestionState;


public interface SuggestionService {
	
	List<Suggestion> getAllSuggestions();
	List<Suggestion> getVotables();
	List<Suggestion> getSuggestionByTitle(String titulo);
	List<Suggestion> getSuggestionByStatus(SuggestionState estado);
	Suggestion getSuggestionById(Long id);
	
	Suggestion saveSuggestion(Suggestion suggestion);
	void deleteSuggestion(Suggestion suggestion);
}
