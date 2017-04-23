package asw.streamKafka.productor;

public class Topics {
	// Sugerencias
	public static final String NEW_SUGGESTION = "newSuggestion";
	public static final String DELETE_SUGGESTION = "deleteSuggestion";
	public static final String POSITIVE_SUGGESTION = "positiveSuggestion";
	public static final String ALERT_SUGGESTION = "alertSuggestion";
	
	// Comentarios
	public static final String NEW_COMMENT = "newComment";
	public static final String POSITIVE_COMMENT = "positiveComment";
	public static final String NEGATIVE_COMMENT = "negativeComment";
	
	// Otras (no las usamos en el dashboard)
	public static final String MIN_VOTES_REACHED = "minVotesReached";
	public static final String DAYS_OPEN = "daysOpen";
	public static final String NEW_CATEGORY = "newCategory";
	public static final String DELETE_CATEGORY = "deleteCategory";
	public static final String DENIED_SUGGESTION = "deniedSuggestion";
}
