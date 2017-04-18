package asw.dbManagement.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TParticipants")
public class Participant {

	// Id generado automáticamente para diferenciar cada uno (para mapear)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Atributos del participante
	private String nombre;
	private String apellidos;
	private String password;
	private Date fechaNacimiento;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String DNI;
	private String direccion;
	private String nacionalidad;

	private boolean isAdmin;
	private boolean isPolitician;

	@OneToMany(mappedBy = "participant", fetch=FetchType.EAGER)
	private Set<Suggestion> suggestions = new HashSet<Suggestion>();
	@OneToMany(mappedBy = "participant", fetch=FetchType.EAGER)
	private Set<Commentary> commentaries = new HashSet<Commentary>();
	@OneToMany(mappedBy = "participant")
	private Set<VoteSuggestion> votesSuggestion = new HashSet<VoteSuggestion>();
	@OneToMany(mappedBy = "participant")
	private Set<VoteCommentary> votesCommentaries = new HashSet<VoteCommentary>();

	/**
	 * Constructor vacío (ya que es para mapear)
	 */
	Participant() {
	}
	
	public Participant(String dni){
		super();
		this.DNI = dni;
	}

	/**
	 * Constructor
	 * 
	 * @param nombre
	 * @param apellidos
	 * @param password
	 * @param fechaNacimiento
	 * @param email
	 * @param dNI
	 * @param direccion
	 * @param nacionalidad
	 */
	public Participant(String nombre, String apellidos, String password, Date fechaNacimiento, String email, String dni,
			String direccion, String nacionalidad, boolean isAdmin, boolean isPolitician) {
		this(dni);
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;		
		this.direccion = direccion;
		this.nacionalidad = nacionalidad;
		this.isAdmin = isAdmin;
		this.isPolitician = isPolitician;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDNI() {
		return DNI;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isPolitician() {
		return isPolitician;
	}

	public void setPolitician(boolean isPolitician) {
		this.isPolitician = isPolitician;
	}

	public Set<Suggestion> getSuggestion() {
		return new HashSet<Suggestion>(suggestions);
	}

	protected Set<Suggestion> _getSuggestion() {
		return suggestions;
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
	
	public Set<VoteCommentary> getVotesCommentary() {
		return new HashSet<VoteCommentary>(votesCommentaries);
	}

	protected Set<VoteCommentary> _getVotesCommentary() {
		return votesCommentaries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DNI == null) ? 0 : DNI.hashCode());
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
		Participant other = (Participant) obj;
		if (DNI == null) {
			if (other.DNI != null)
				return false;
		} else if (!DNI.equals(other.DNI))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Participant [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", password=" + password
				+ ", fechaNacimiento=" + fechaNacimiento + ", email=" + email + ", DNI=" + DNI + ", direccion="
				+ direccion + ", nacionalidad=" + nacionalidad + ", isAdmin=" + isAdmin + ", isPolitician="
				+ isPolitician + "]";
	}
}
