package model.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import asw.dbManagement.model.Word;

public class WordTest {
	
	private Word word;

	@Before
	public void setUp(){
		word = new Word("fjcjwhc");
	}
	
	@Test
	public void testGetId(){
		assertNull(word.getId());
	}
	
	@Test
	public void testGetWord(){
		assertTrue(word.getWord().equals("fjcjwhc"));
	}
	
	@Test
	public void testSetWord(){
		word.setWord("hola");
		assertTrue(word.getWord().equals("hola"));
	}
	
	@Test
	public void testToString(){
		assertEquals(word.toString(), "Word [name=" + "fjcjwhc" + "]");
	}
	
	@Test
	public void testHashCodeEquals(){
		assertFalse(word.equals(4));
		assertFalse(word.equals(null));
		assertTrue(word.equals(word));
	}
}
