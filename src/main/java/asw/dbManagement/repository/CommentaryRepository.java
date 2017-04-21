package asw.dbManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asw.dbManagement.model.Comment;

@Repository
public interface CommentaryRepository extends JpaRepository<Comment, Long>{

	public Comment findByIdentificador(String identificador);
}
