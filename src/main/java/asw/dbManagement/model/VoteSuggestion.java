package asw.dbManagement.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import asw.dbManagement.model.types.VoteSuggestionKey;
import asw.dbManagement.model.types.VoteType;

@Entity
@IdClass(VoteSuggestionKey.class)
@Table(name = "TVoteSuggestion")
public class VoteSuggestion {

	@Id
	@ManyToOne
	@JoinColumn(name = "PARTICIPANT_ID", referencedColumnName = "ID")
	private Participant participant;

	@Id
	@ManyToOne
	@JoinColumn(name = "SUGGESTION_ID", referencedColumnName = "ID")
	private Suggestion suggestion;
	private VoteType voteType;

	VoteSuggestion() {
	}

	public VoteSuggestion(Participant participant, Suggestion suggestion, VoteType voteType) {
		this.voteType = voteType;
		Association.VotarSuggestion.link(participant, this, suggestion);
		suggestion.incrementarNumeroVotos(voteType);
	}

	public Participant getParticipant() {
		return participant;
	}

	protected void _setParticipant(Participant participant) {
		this.participant = participant;
	}

	public Suggestion getSuggestion() {
		return suggestion;
	}

	protected void _setSuggestion(Suggestion suggestion) {
		this.suggestion = suggestion;
	}

	public void deleteVoteSuggestion() {
		suggestion.decrementarNumeroVotos(voteType);
		Association.VotarSuggestion.unlink(this);
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	@Override
	public String toString() {
		return "VoteSuggestion [participant=" + participant + ", suggestion=" + suggestion + "]";
	}
}
