package asw.dbManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asw.dbManagement.model.Suggestion;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long>{
	
	public Suggestion findByIdentificador(String identificador);

}
