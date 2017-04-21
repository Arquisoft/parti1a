package asw.dbManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asw.dbManagement.model.Suggestion;
import asw.dbManagement.model.types.SuggestionState;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long>{
	
	public Suggestion findByIdentificador(String identificador);
	List<Suggestion> findAll();
	List<Suggestion> findByEstadoOrderByPopularidadDesc(SuggestionState ss);
	List<Suggestion> findByTitulo(String titulo);
	List<Suggestion> findByEstado(SuggestionState estado);

}
