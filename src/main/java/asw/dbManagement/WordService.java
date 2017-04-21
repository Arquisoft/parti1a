package asw.dbManagement;

import java.util.List;

import asw.dbManagement.model.Word;


public interface WordService {
	List<Word> getAllWords();
	Word getWordByName(String word);
	
	void saveWord(Word word);
	void deleteWord(Word word);
}
