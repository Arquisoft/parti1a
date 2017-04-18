package asw.dbManagement.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import asw.dbManagement.model.types.VoteType;

@Entity
@Table(name = "TCommentaries")
public class Commentary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String identificador;
	private String contenido;
	private int votosPositivos = 0;
	private int votosNegativos = 0;

	@ManyToOne
	private Participant participant;
	@ManyToOne
	private Suggestion suggestion;
	@OneToMany(mappedBy = "commentary")
	private Set<VoteCommentary> votesCommentaries = new HashSet<VoteCommentary>();

	Commentary() {
	}

	public Commentary(String identificador) {
		super();
		this.identificador = identificador;
	}

	public Commentary(String identificador, String contenido, Participant participant, Suggestion suggestion) {
		this(identificador);
		this.contenido = contenido;
		Association.Comentar.link(participant, this, suggestion);
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public int getVotosPositivos() {
		return votosPositivos;
	}

	public void setVotosPositivos(int votosPositivos) {
		this.votosPositivos = votosPositivos;
	}

	public int getVotosNegativos() {
		return votosNegativos;
	}

	public void setVotosNegativos(int votosNegativos) {
		this.votosNegativos = votosNegativos;
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

	public Long getId() {
		return id;
	}

	public Set<VoteCommentary> getVotesCommentary() {
		return new HashSet<VoteCommentary>(votesCommentaries);
	}

	protected Set<VoteCommentary> _getVotesCommentary() {
		return votesCommentaries;
	}

	public String getIdentificador() {
		return identificador;
	}

	/**
	 * Metodo que incrementa el voto dependiendo del tipo del que este sea.
	 * @param voteType
	 */
	public void incrementarNumeroVotos(VoteType voteType) {
		if (voteType.equals(VoteType.POSITIVE))
			votosPositivos++;
		else if (voteType.equals(VoteType.NEGATIVE))
			votosNegativos++;
	}
	
	/**
	 * Metodo que decrementa el voto dependiendo del tipo del que este sea.
	 * @param voteType
	 */
	public void decrementarNumeroVotos(VoteType voteType){
		if (voteType.equals(VoteType.POSITIVE))
			votosPositivos--;
		else if (voteType.equals(VoteType.NEGATIVE))
			votosNegativos--;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identificador == null) ? 0 : identificador.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Commentary other = (Commentary) obj;
		if (identificador == null) {
			if (other.identificador != null)
				return false;
		} else if (!identificador.equals(other.identificador))
			return false;
		return true;
	}

	public void deleteComment() {
		Association.Comentar.unlink(this);
	}
}
