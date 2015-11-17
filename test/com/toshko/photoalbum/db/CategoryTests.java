package com.toshko.photoalbum.db;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.toshko.photoalbum.dto.Category;

public class CategoryTests {

	@Test
	public void categoryCreationPositive() {
		Category category = new Category("categoryForTest", "description");
		CategoryDB categoryDb = new CategoryDB();
		boolean result = categoryDb.createCategory(category);
		//The id for the created directory is 68
		assertTrue(result);
	}
	
	public void subCategoryCreationPositive() {
		CategoryDB categoryDb = new CategoryDB();
		Category category = new Category("subCategoryForTest", "description");
		Category secondCategory = new Category("secondSubCategoryForTest", "description");
		Category thirdCategory = new Category("secondSubCategoryForTest", "description");
		boolean result = categoryDb.createCategory(category, 68);
		result = categoryDb.createCategory(secondCategory, 68);
		result = categoryDb.createCategory(thirdCategory, 68);
		assertTrue(result);
	}
	
/*	public void getDirectSubCategoriesPositive() {
		CategoryDB categoryDb = new CategoryDB();
		Collection<Category> allSubCategories = categoryDb.getDirectSubCategories(68);
		int sizeOfSubCategories = allSubCategories.size();
		boolean result = false;
		if(sizeOfSubCategories == 3) {
			result = true;
		}
		assertTrue(result);
	}*/
	
}
