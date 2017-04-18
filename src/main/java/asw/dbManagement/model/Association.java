package asw.dbManagement.model;

public class Association {

	public static class Proponer {

		public static void link(Participant participant, Suggestion suggestion) {
			suggestion._setParticipant(participant);
			participant._getSuggestion().add(suggestion);
		}

		public static void unlink(Participant participant, Suggestion suggestion) {
			participant._getSuggestion().remove(suggestion);
			suggestion._setParticipant(null);
		}
	}

	public static class Comentar {

		public static void link(Participant participant, Commentary comentario, Suggestion suggestion) {
			comentario._setSuggestion(suggestion);
			suggestion._getCommentaries().add(comentario);

			comentario._setParticipant(participant);
			participant._getCommentaries().add(comentario);
			
			int num = suggestion.getNumComments() + 1;
			suggestion.setNumComments(num);
		}

		public static void unlink(Commentary comentario) {
			int num = comentario.getSuggestion().getNumComments();
			comentario.getSuggestion().setNumComments(num--);
			
			comentario.getSuggestion()._getCommentaries().remove(comentario);
			comentario.getParticipant()._getCommentaries().remove(comentario);

			comentario._setSuggestion(null);
			comentario._setParticipant(null);
		}
	}

	public static class VotarSuggestion {

		public static void link(Participant participant, VoteSuggestion voteSuggestion, Suggestion suggestion) {
			voteSuggestion._setSuggestion(suggestion);
			voteSuggestion._setParticipant(participant);

			suggestion._getVotesSuggestion().add(voteSuggestion);
			participant._getVotesSuggestion().add(voteSuggestion);
		}

		public static void unlink(VoteSuggestion voteSuggestion) {
			Participant participant = voteSuggestion.getParticipant();
			Suggestion suggestion = voteSuggestion.getSuggestion();

			suggestion._getVotesSuggestion().remove(voteSuggestion);
			participant._getVotesSuggestion().remove(voteSuggestion);

			voteSuggestion._setParticipant(null);
			voteSuggestion._setSuggestion(null);
		}

	}

	public static class VotarCommentary {

		public static void link(Participant participant, VoteCommentary voteCommentary, Commentary commentary) {
			voteCommentary._setComentary(commentary);
			voteCommentary._setParticipant(participant);

			commentary._getVotesCommentary().add(voteCommentary);
			participant._getVotesCommentary().add(voteCommentary);
		}

		public static void unlink(VoteCommentary voteCommentary) {
			Participant participant = voteCommentary.getParticipant();
			Commentary commentary = voteCommentary.getCommentary();

			commentary._getVotesCommentary().remove(voteCommentary);
			participant._getVotesCommentary().remove(voteCommentary);

			voteCommentary._setParticipant(null);
			voteCommentary._setComentary(null);
		}
	}

}
