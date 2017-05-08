package dbManagement.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import asw.dbManagement.CategoryService;
import asw.dbManagement.model.Category;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryServiceImplTest {

	@Autowired
	private CategoryService service;

	@Test
	public void t1_testGetCategoryById() {
		Category c = service.getCategoryById(new Long(17));
		assertEquals("Ocio23", c.getName());
		assertEquals(new Long(17), c.getId());
		assertNotNull(c.getSuggestions());
	}

	@Test
	public void t2_testGetCategoryByName() {
		Category c = service.getCategoryByName("Ocio23");
		assertEquals(new Long(17), c.getId());
		assertEquals("Ocio23", c.getName());
		assertNotNull(c.getSuggestions());
	}

	@Test
	public void t3_testGetAllCategories() {
		List<Category> categories = service.getAllCategories();
		assertEquals(categories.size(), categories.size());
	}

	@Test
	public void t4_testSaveCategory() {
		Category c = service.saveCategory(new Category("test"));

		if (c != null) // no existe en la base de datos
			assertEquals("test", c.getName());
	}
	
	@Test
	public void t5_testDeleteCategory() {
		Category c = service.getCategoryByName("test");
		service.deleteCategory(c);
		
		assertEquals(null, service.getCategoryByName("test"));
	}
}
