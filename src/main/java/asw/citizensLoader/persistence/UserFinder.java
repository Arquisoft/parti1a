package asw.citizensLoader.persistence;

import java.util.List;

import asw.citizensLoader.persistence.util.Jpa;
import asw.dbManagement.model.Participant;

public class UserFinder {

	public static List<Participant> findByDNI(String dni) {
		return Jpa.getManager().createNamedQuery("User.findByDni", Participant.class).
				setParameter(1, dni).getResultList();
	}

	public static List<Participant> findByEmail(String email) {
		return Jpa.getManager().createNamedQuery("User.findByEmail", Participant.class).
				setParameter(1, email).getResultList();
	}
}
