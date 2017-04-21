package asw.dbManagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import asw.dbManagement.model.VoteComment;
import asw.dbManagement.model.types.VoteCommentaryKey;



@Repository
public interface VoteCommentRepository extends CrudRepository<VoteComment, VoteCommentaryKey>{

}
