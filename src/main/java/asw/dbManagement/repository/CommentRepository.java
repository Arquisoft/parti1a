package asw.dbManagement.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import asw.dbManagement.model.Comment;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;


@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
	List<Comment> findByParticipant(Participant participant);
	List<Comment> findBySuggestion(Suggestion Suggestion);
	
}
