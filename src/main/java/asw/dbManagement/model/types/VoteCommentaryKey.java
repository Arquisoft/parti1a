package asw.dbManagement.model.types;

import java.io.Serializable;

public class VoteCommentaryKey implements Serializable {

	private static final long serialVersionUID = 1L;

	Long participant;
	Long commentary;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentary == null) ? 0 : commentary.hashCode());
		result = prime * result + ((participant == null) ? 0 : participant.hashCode());
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
		VoteCommentaryKey other = (VoteCommentaryKey) obj;
		if (commentary == null) {
			if (other.commentary != null)
				return false;
		} else if (!commentary.equals(other.commentary))
			return false;
		if (participant == null) {
			if (other.participant != null)
				return false;
		} else if (!participant.equals(other.participant))
			return false;
		return true;
	}
}
