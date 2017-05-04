package model.domain;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import asw.dbManagement.model.Category;
import asw.dbManagement.model.Participant;
import asw.dbManagement.model.Suggestion;

public class SuggestionCategoryTest {
	
	private Participant participant;
	private Category categoria1;
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		participant = new Participant("martin", "pelaez", "diaz", new Date(1993, 11, 12), "paco@gmail.com",
				"1234567", "Calle Uria", "ESP", false, false);
		categoria1 = new Category("fdjcvjd");
		new Suggestion("vgifqh ", "prueba3", "aumentando codecov", participant,categoria1);
	}

	@Test
	public void testMetodoEqualsHashCode() {
		assertFalse(categoria1.equals(4));
		assertFalse(categoria1.equals(null));
		assertTrue(categoria1.equals(categoria1));
	}
	
	@Test
	public void testMetodoToString(){
		assertEquals(categoria1.toString(),"Category [name=" + "fdjcvjd" + "]");
	}
	
	@Test
	public void testGetSuggestion(){
		assertTrue(categoria1.getSuggestions().size() == 1);
	}
	
	@Test
	public void testGetId(){
		assertTrue(categoria1.getId() == null);
	}
}
