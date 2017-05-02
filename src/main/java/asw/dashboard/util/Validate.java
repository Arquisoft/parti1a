package asw.dashboard.util;

import javax.servlet.http.HttpSession;

import asw.dbManagement.model.Participant;
import asw.participants.factory.ErrorFactory;
import asw.participants.factory.ErrorFactory.Errors;

public class Validate {

	public static void validateAdmin(HttpSession session) {
		if (session.getAttribute("usuario") == null)
			throw ErrorFactory.getError(Errors.USER_NOT_FOUND);
		else {
			Participant p = (Participant) session.getAttribute("usuario");
			if (!p.isAdmin())
				throw ErrorFactory.getError(Errors.USER_NOT_FOUND);
		}
	}

	public static void validateUser(HttpSession session) {
		if (session.getAttribute("usuario") == null)
			throw ErrorFactory.getError(Errors.USER_NOT_FOUND);
		else {
			Participant p = (Participant) session.getAttribute("usuario");
			if (p.isAdmin())
				throw ErrorFactory.getError(Errors.USER_NOT_FOUND);
		}
	}
}
