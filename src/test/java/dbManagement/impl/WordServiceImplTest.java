package dbManagement.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import asw.Application;
import asw.dbManagement.WordService;
import asw.dbManagement.model.Word;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WordServiceImplTest {

	@Autowired
	private WordService service;

	@Test
	public void testGetAllWords() {
		List<Word> list = service.getAllWords();
		assertEquals(list.size(), list.size());
	}

	@Test
	public void testGetWordByName() {
		service.saveWord(new Word("Prueba"));
		Word word = service.getWordByName("Prueba");

		assertEquals("Prueba", word.getWord());

		service.deleteWord(service.getWordByName("Prueba"));
	}

	@Test
	public void testDeleteAndSave() {
		service.saveWord(new Word("Prueba"));
		service.deleteWord(service.getWordByName("Prueba"));
	}

}
