package asw.dbManagement.model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import asw.dbManagement.model.types.VoteType;

@Entity
@Table(name = "TComments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String identificador;
	private String texto;
	private int votosPositivos = 0;
	private int votosNegativos = 0;
	private int valoracion = 0;
	@Column(name="fecha_creacion")
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;

	@ManyToOne
	private Participant participant;
	@ManyToOne
	private Suggestion suggestion;
	@OneToMany(mappedBy = "comment")
	private Set<VoteComment> votesCommentaries = new HashSet<VoteComment>();

	Comment() {
	}

	public Comment(String identificador) {
		super();
		this.identificador = identificador;
	}

	public Comment(String identificador, String texto, Participant participant, Suggestion suggestion) {
		this(identificador);
		this.texto = texto;
		this.fechaCreacion = Calendar.getInstance().getTime();
		Association.Comentar.link(participant, this, suggestion);
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
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

	public Set<VoteComment> getVotesCommentary() {
		return new HashSet<VoteComment>(votesCommentaries);
	}

	protected Set<VoteComment> _getVotesCommentary() {
		return votesCommentaries;
	}

	public String getIdentificador() {
		return identificador;
	}

	public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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
		this.valoracion = this.votosPositivos - this.votosNegativos;
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
		this.valoracion = this.votosPositivos - this.votosNegativos;
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
		Comment other = (Comment) obj;
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
