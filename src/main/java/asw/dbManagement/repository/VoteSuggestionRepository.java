package asw.dbManagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import asw.dbManagement.model.VoteSuggestion;
import asw.dbManagement.model.types.VoteSuggestionKey;


@Repository
public interface VoteSuggestionRepository extends CrudRepository<VoteSuggestion, VoteSuggestionKey>{

}
