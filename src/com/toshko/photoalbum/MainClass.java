package com.toshko.photoalbum;

import java.util.ArrayList;

import com.toshko.photoalbum.db.CategoryDB;
import com.toshko.photoalbum.db.UserRegistry;
import com.toshko.photoalbum.dto.Category;
import com.toshko.photoalbum.dto.User;
public class MainClass {
	
	public static void main(String[] args) {
		/*Person person = new Person("totti", "123456", "toshko", "radev", "totti_gotin@abv.bg");
		Person person2 = new Person("totti", "123456", "toshko", "radev", "totti_gotin@abv.bg");
		ArrayList<Person> test = new ArrayList<>();
		test.add(person);
		Register registry = new Register();
		registry.setRegisteredCustomers(test);
		registry.editProfile("totti");*/
/*		User person3 = new User("chep", "ss", "asdasd", "sadasdasdsa", "asdasdasd");
		User person2 = new User("mamata", "kolio123", "Kolio", "Ivanov", "fabregas558@abv.bg");
		User person = new User("tradev", "todorradev10", "Todor", "Radev", "smcho@abv.bg");
		person.setPassword("testova parola");
		person.setFirstName("messi");
		person.setLastName("leo");
		UserRegistry userDb = new UserRegistry();
		userDb.createUser(person);
		userDb.createUser(person3);		
		userDb.createUser(person2);
		
		Collection<User> users = userDb.getUsers();
		for (User user : users) {
			System.out.println(user);
		}
		boolean result = userDb.editUser(person);
		
		boolean result2 = userDb.isValidCredentials("tradev", "testova parola");
		System.out.println(result2);
*/		
/*		UserRegistry registry = new UserRegistry();
		registry.registerPerson(person);
		registry.registerPerson(person2);
		
		registry.editProfile("mamata", "0887742713", "toshko", "Radev", "smcho@abv.bg");
		System.out.println(person2.getUsername());
		System.out.println(person2.getPassword());
		System.out.println(person2.getFirstName());
		System.out.println(person2.getLastName());
		System.out.println(person2.getEmail());
		
		registry.editProfile("tradev", "123", "goshko", "broshko", "smcho@abv.bg");
		System.out.println(person.getUsername());
		System.out.println(person.getPassword());
		System.out.println(person.getFirstName());
		System.out.println(person.getLastName());
		System.out.println(person.getEmail());
		
		registry.editProfile("kur", "0887742713", "toshko", "Radev", "smcho@abv.bg");
		registry.removeProfile("tradev");*/
/*		Category category = new Category("bum", "bau");
		CategoryDB categoryDb = new CategoryDB();
		categoryDb.createCategory(category);
		category.setName("sss");
		category.setDescription("ddd");
		System.out.println(category.getId());
		boolean result = categoryDb.editCategory(category);
		User user = new User();
		
		UserRegistry userReg = new UserRegistry();
		userReg.createUser(user);
		System.out.println(user.getId());

		Collection<Category> allCategories = categoryDb.getCategories();
		for (Category category2 : allCategories) {
			System.out.println(category2);
		}
		
		categoryDb.removeCategory(4);
		
		System.out.println(result);*/
		/*Category category = new Category("Nigeria", "cherni");
		Category category2 = new Category("djanman", "mnpogo cherni");
		CategoryDB categoryDb = new CategoryDB();
		categoryDb.createCategory(category);
		categoryDb.createCategory(category2);
		CategoryXSubcategory subcategory = new CategoryXSubcategory();
		subcategory.createSubcategory(category, category2);*/
		
/*		java.sql.Date date = java.sql.Date.valueOf("2014-04-26");
		

		Picture picture2 = new Picture("toshko", "", date, 8, "uy nas");
		PictureDB pictureDb = new PictureDB();
		
		pictureDb.createPicture(picture2);
		*/
		
		User user = new User("petkn", "1234", "ivann", "petrov", "asdsad");
		UserRegistry userRegistry = new UserRegistry();
		userRegistry.createUser(user);
		
		CategoryDB category = new CategoryDB();
		ArrayList<Category> rootCategories = (ArrayList<Category>) category.getDirectSubCategories(user.getId(), -1);
		Category rootCategory = rootCategories.get(0);
		Category newCategory = new Category("paris", "");
		category.createCategory(newCategory, rootCategory.getId());
		
		
		category.removeAllUserCategory(user.getId());
/*		Category category = new Category("Nigeria", "cherni");
		
		CategoryDB categoryDb = new CategoryDB();
		categoryDb.createCategory(category);
		
		CategoryXPictures categoryx = new CategoryXPictures();
		categoryx.createCategoryWithPicture(category, picture2);
*/		
		//pictureDb.removePicture(1);
		
		/*Collection<Picture> pictures = pictureDb.getPictures();
		for (Picture picture : pictures) {
			System.out.println(picture);
		}*/
		
		
		/*User user = new User();
		user.setUsername("menchuu");
		user.setPassword("12345551");
		user.setFirstName("toshko");
		UserRegistry userReg = new UserRegistry();
		userReg.createUser(user);
		
		userReg.removeUser(user.getId());*/
	}
}
