package com.toshko.photoalbum.db;

import static org.junit.Assert.*;

import org.junit.Test;

import com.toshko.photoalbum.dto.User;

public class UserRegistryTests {

	@Test
	public void userCreationPositive() {
		User user = new User("todorradev", "Todor", "Radev", "todor.r.radev@gmail.com");
		UserRegistry registry = new UserRegistry();
		boolean result = registry.createUser(user);
		assertTrue(result);
	}
	
	public void duplicateUsernameNegative() {
		User user = new User("todorradev", "Ivan", "Petrov", "ivan@gmail.com");
		UserRegistry registry = new UserRegistry();
		boolean result = registry.createUser(user);
		assertFalse(result);
	}
	
	public void validCredentialsPositive() {
		UserRegistry registry = new UserRegistry();
		boolean result = registry.isValidCredentials("todorradev", "");
		assertTrue(result);
	}

}
