package asw.dashboard;

public interface EventController {
	public void newSuggestion(String data);

	public void deleteSuggestion(String data);

	public void alertSuggestion(String data);

	public void voteSuggestion(String data);

	public void newComment(String data);

	public void positiveComment(String data);

	public void negativeComment(String data);
}
