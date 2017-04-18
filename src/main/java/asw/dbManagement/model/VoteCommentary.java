package asw.dbManagement.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import asw.dbManagement.model.types.VoteCommentaryKey;
import asw.dbManagement.model.types.VoteType;

@Entity
@IdClass(VoteCommentaryKey.class)
@Table(name = "TVoteCommentary")
public class VoteCommentary {

	@Id
	@ManyToOne
	@JoinColumn(name = "PARTICIPANT_ID", referencedColumnName = "ID")
	private Participant participant;

	@Id
	@ManyToOne
	@JoinColumn(name = "COMMENTARY_ID", referencedColumnName = "ID")
	private Commentary commentary;
	@Enumerated(EnumType.STRING)
	private VoteType voteType;

	VoteCommentary() {
	}

	public VoteCommentary(Participant participant, Commentary commentary, VoteType voteType) {
		this.voteType = voteType;
		Association.VotarCommentary.link(participant, this, commentary);
		commentary.incrementarNumeroVotos(voteType);		
	}

	public Participant getParticipant() {
		return participant;
	}

	protected void _setParticipant(Participant participant) {
		this.participant = participant;
	}

	public Commentary getCommentary() {
		return commentary;
	}

	protected void _setComentary(Commentary commentary) {
		this.commentary = commentary;
	}

	public void deleteVoteCommentary() {
		commentary.decrementarNumeroVotos(voteType);
		Association.VotarCommentary.unlink(this);
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	@Override
	public String toString() {
		return "VoteCommentary [participant=" + participant + ", comentary=" + commentary + "]";
	}
}
