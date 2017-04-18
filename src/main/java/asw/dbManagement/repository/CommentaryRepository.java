package asw.dbManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asw.dbManagement.model.Commentary;

@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Long>{

	public Commentary findByIdentificador(String identificador);
}
