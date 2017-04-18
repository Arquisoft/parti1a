package asw.dbManagement.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import asw.dbManagement.model.types.VoteType;

@Entity
@Table(name = "TSuggestions")
public class Suggestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String identificador;
	private String nombre;
	private String contenido;
	private int votosPositivos = 0;
	private int votosNegativos = 0;
	private int votosMinimos;

	@ManyToOne
	private Participant participant;
	@OneToMany(mappedBy = "suggestion", fetch = FetchType.EAGER)
	private Set<Commentary> commentaries = new HashSet<Commentary>();
	@OneToMany(mappedBy = "suggestion")
	private Set<VoteSuggestion> votesSuggestion = new HashSet<VoteSuggestion>();

	private int numComments = 0;
	private boolean aprobada;
	private boolean alert;

	Suggestion() {
	}

	public Suggestion(String identificador) {
		super();
		this.identificador = identificador;
	}

	public Suggestion(String identificador, String nombre, String contenido, int votosMinimos,
			Participant participant) {
		this(identificador);
		this.nombre = nombre;
		this.contenido = contenido;
		this.votosMinimos = votosMinimos;
		Association.Proponer.link(participant, this);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public void setVotosPositivos(int votosPositvos) {
		this.votosPositivos = votosPositvos;
	}

	public int getVotosNegativos() {
		return votosNegativos;
	}

	public void setVotosNegativos(int votosNegativos) {
		this.votosNegativos = votosNegativos;
	}

	public int getVotosMinimos() {
		return votosMinimos;
	}

	public void setVotosMinimos(int votosMinimos) {
		this.votosMinimos = votosMinimos;
	}

	public Participant getParticipant() {
		return participant;
	}

	protected void _setParticipant(Participant participant) {
		this.participant = participant;
	}

	public Long getId() {
		return id;
	}

	public Set<Commentary> getCommentaries() {
		return new HashSet<Commentary>(commentaries);
	}

	protected Set<Commentary> _getCommentaries() {
		return commentaries;
	}

	public Set<VoteSuggestion> getVotesSuggestion() {
		return new HashSet<VoteSuggestion>(votesSuggestion);
	}

	protected Set<VoteSuggestion> _getVotesSuggestion() {
		return votesSuggestion;
	}

	public String getIdentificador() {
		return identificador;
	}

	public int getNumComments() {
		return numComments;
	}

	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}

	public void setAprobada(boolean aprobada){
		this.aprobada = aprobada;
	}
	
	public boolean isAprobada() {
		return aprobada;
	}

	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
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
		Suggestion other = (Suggestion) obj;
		if (identificador == null) {
			if (other.identificador != null)
				return false;
		} else if (!identificador.equals(other.identificador))
			return false;
		return true;
	}

	public void deleteSuggestion() {
		Association.Proponer.unlink(participant, this);
	}

	/**
	 * Metodo que incrementa el voto dependiendo del tipo del que este sea.
	 * 
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
	 * 
	 * @param voteType
	 */
	public void decrementarNumeroVotos(VoteType voteType) {
		if (voteType.equals(VoteType.POSITIVE))
			votosPositivos--;
		else if (voteType.equals(VoteType.NEGATIVE))
			votosNegativos--;
	}
}
