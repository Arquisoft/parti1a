package asw.dbManagement.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.dbManagement.SuggestionService;
import asw.dbManagement.model.Suggestion;
import asw.dbManagement.model.types.SuggestionState;
import asw.dbManagement.repository.SuggestionRepository;

@Service
public class SuggestionServiceImpl implements SuggestionService {
	private SuggestionRepository repository;

	@Autowired
	public SuggestionServiceImpl(SuggestionRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Suggestion> getAllSuggestions() {
		return repository.findAll();
	}

	@Override
	public List<Suggestion> getSuggestionByTitle(String titulo) {
		return repository.findByTitulo(titulo);
	}

	@Override
	public Suggestion getSuggestionById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public Suggestion saveSuggestion(Suggestion suggestion) {
		return repository.save(suggestion);
	}

	@Override
	public List<Suggestion> getVotables() {
		return repository.findByEstadoOrderByPopularidadDesc(SuggestionState.BuscandoApoyo);
	}

	@Override
	public void deleteSuggestion(Suggestion suggestion) {
		repository.delete(suggestion);
	}

	@Override
	public List<Suggestion> getSuggestionByStatus(SuggestionState estado) {
		return repository.findByEstado(estado);
	}
}
