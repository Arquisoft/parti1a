package asw.dbManagement.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import asw.dbManagement.ParticipantService;
import asw.dbManagement.model.Comment;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;
import asw.dbManagement.model.types.SuggestionState;
import asw.dbManagement.model.VoteComment;
import asw.dbManagement.model.VoteSuggestion;
import asw.dbManagement.model.types.VoteCommentaryKey;
import asw.dbManagement.model.types.VoteSuggestionKey;
import asw.dbManagement.model.types.VoteType;
import asw.dbManagement.repository.CommentRepository;
import asw.dbManagement.repository.ParticipantRepository;
import asw.dbManagement.repository.SuggestionRepository;
import asw.dbManagement.repository.VoteCommentRepository;
import asw.dbManagement.repository.VoteSuggestionRepository;

//CADA VEZ QUE SE CREE UN OBJETO NUEVO SE DEBE HACER DENTRO DE UN METODO TRANSACTIONAL DENTRO DE UN SERVICE 
//PARA PODER USAR LA CLASE ASSOCIATIONS

@Service
public class ParticipantServiceImpl implements ParticipantService {

	private ParticipantRepository pr;
	private VoteSuggestionRepository vsr;
	private SuggestionRepository sr;
	private VoteCommentRepository vcr;
	private CommentRepository cr;

	@Autowired
	public ParticipantServiceImpl(ParticipantRepository pr, VoteSuggestionRepository vsr, SuggestionRepository sr,
			CommentRepository cr, VoteCommentRepository vcr) {
		this.pr = pr;
		this.vsr = vsr;
		this.sr = sr;
		this.cr = cr;
		this.vcr = vcr;
	}

	@Override
	public Participant getParticipant(String email, String password) {
		Participant participant = pr.findByEmailAndPassword(email, password);
		return participant;
	}

	@Override
	@Transactional
	public boolean supportSuggestion(Long participant, Long suggestion) {
		Participant part = pr.findOne(participant);
		Suggestion sugg = sr.findOne(suggestion);
		if (!vsr.exists(new VoteSuggestionKey(part.getId(), sugg.getId()))) {
			VoteSuggestion vote = new VoteSuggestion(part, sugg, VoteType.POSITIVE);
			vsr.save(vote);
			if (sugg.getPopularidad() >= sugg.getVotosMinimos())
				sugg.setEstado(SuggestionState.EnVotacion);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean supportCommentPositive(Long participant, Long comment) {
		Participant part = pr.findOne(participant);
		Comment com = cr.findOne(comment);
		if (!vcr.exists(new VoteCommentaryKey(part.getId(), com.getId()))) {
			VoteComment vote = new VoteComment(part, com, VoteType.POSITIVE);
			vcr.save(vote);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean supportCommentNegative(Long participant, Long comment) {
		Participant part = pr.findOne(participant);
		Comment com = cr.findOne(comment);
		if (!vcr.exists(new VoteCommentaryKey(part.getId(), com.getId()))) {
			VoteComment vote = new VoteComment(part, com, VoteType.NEGATIVE);
			vcr.save(vote);
			return true;
		}
		return false;
	}
}
