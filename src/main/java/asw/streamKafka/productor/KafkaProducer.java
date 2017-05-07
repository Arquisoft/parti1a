package asw.streamKafka.productor;

public interface KafkaProducer {
	// Suggestions
	public void sendNewSuggestion(String suggestionId, String title);

	public void sendDeleteSuggestion(String suggestionId);

	public void sendPositiveSuggestion(String suggestionId);

	public void sendAlertSuggestion(String suggestionId);

	// Comentarios
	public void sendNewComment(String commentId, String suggestionId);

	public void sendPositiveComment(String commentId, String suggestionId);

	public void sendNegativeComment(String commentId, String suggestionId);
}
