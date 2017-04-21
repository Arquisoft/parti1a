package asw.dbManagement.model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import asw.dbManagement.model.types.SuggestionState;
import asw.dbManagement.model.types.VoteType;

@Entity
@Table(name = "TSuggestions")
public class Suggestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String identificador;
	private String titulo;
	private String descripcion;
	private int votosPositivos = 0;
	private int votosNegativos = 0;
	private int votosMinimos;
	private int popularidad = 0;
	private int numComments = 0;
	private boolean aprobada;
	private boolean alert;
	
	@Enumerated(EnumType.STRING)
	private SuggestionState estado;// igual era bueno quitar lo de alert y
									// probada y trabajar con este enum de
									// momentio se queda asi.

	@Column(name = "fecha_creacion")
	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;
	@Column(name = "fecha_fin")
	@Temporal(TemporalType.DATE)
	private Date fechaFin;

	@ManyToOne
	private Category category;
	@ManyToOne
	private Participant participant;
	@OneToMany(mappedBy = "suggestion", fetch = FetchType.EAGER)
	private Set<Comment> commentaries = new HashSet<Comment>();
	@OneToMany(mappedBy = "suggestion")
	private Set<VoteSuggestion> votesSuggestion = new HashSet<VoteSuggestion>();

	public static final int DIAS_ABIERTA = 7;
	public static final int MIN_VOTOS_DEFECTO = 100;

	Suggestion() {
	}

	public Suggestion(String identificador) {
		super();
		this.identificador = identificador;
	}

	public Suggestion(String identificador, String titulo, String descripcion, Participant participant) {
		this(identificador);
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.votosMinimos = MIN_VOTOS_DEFECTO;
		Association.Proponer.link(participant, this);
	}

	public Suggestion(String identificador, String titulo, String descripcion, Participant creator, Category category) {
		this(identificador);
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = Calendar.getInstance().getTime();
		this.estado = SuggestionState.BuscandoApoyo;
		this.votosMinimos = MIN_VOTOS_DEFECTO;
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, DIAS_ABIERTA); // Pone la fecha de finalización
											// DIAS_ABIERTA mas tarde
		this.fechaFin = c.getTime();
		this.category = category;// esto esta como la mierda tendria que ir en
									// el link.
		Association.Proponer.link(participant, this);
	}

	public Suggestion(String identificador, String titulo, String descripcion, int votosMinimos,
			Participant participant) {
		this(identificador);
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.votosMinimos = votosMinimos;
		Association.Proponer.link(participant, this);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Set<Comment> getCommentaries() {
		return new HashSet<Comment>(commentaries);
	}

	protected Set<Comment> _getCommentaries() {
		return commentaries;
	}

	public Set<VoteSuggestion> getVotesSuggestion() {
		return new HashSet<VoteSuggestion>(votesSuggestion);
	}

	protected Set<VoteSuggestion> _getVotesSuggestion() {
		return votesSuggestion;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public void setAprobada(boolean aprobada) {
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

	public int getPopularidad() {
		return popularidad;
	}

	public void setPopularidad(int popularidad) {
		this.popularidad = popularidad;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public SuggestionState getEstado() {
		return estado;
	}

	public void setEstado(SuggestionState estado) {
		this.estado = estado;
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
		this.popularidad = this.votosPositivos - this.votosNegativos;
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
		this.popularidad = this.votosPositivos - this.votosNegativos;
	}
}
