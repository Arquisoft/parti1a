package asw.dbManagement;

import java.util.List;

import asw.dbManagement.model.Comment;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;


public interface CommentService {

	List<Comment> getAllComments();

	List<Comment> getCommentsByParticipant(Participant participant);

	List<Comment> getCommentsBySuggestion(Suggestion suggestion);

	Comment saveComment(Comment comment);

	Comment findCommentById(long id);
}
