package ch.zli.m223.CRM.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ch.zli.m223.CRM.model.User;
import ch.zli.m223.CRM.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test public void getUserByIdTest() {
		assertEquals(1L, (long)userService.getUserById(1L).getId());
	}

	@Test public void getUserByNameTest() {
		assertEquals("user", userService.getUserByName("user").getUserName());
	}

	@Test public void getAllUserTest() {
		Collection<User> users = userService.getAllUsers();
		assertFalse(users == null);
		assertTrue(users.size() > 0);
	}

	@Test public void createUserTest() {
		User user = userService.createUser("me", "metoo", "lazy", "boy");
		assertTrue(user != null);
		assertEquals("me", user.getUserName());
		assertEquals(2, user.getRoleNames().size());
		assertTrue(user.getRoleNames().contains("lazy"));
		assertTrue(user.getRoleNames().contains("boy"));
		
		// User already exists
		user = userService.createUser("me", "metoo", "lazy", "boy");
		assertTrue(user == null);
	}

	@Test public void deleteUserTest() {
		int sizeBefore = userService.getAllUsers().size();
		User user = userService.createUser("anotherme", "metoo", "lazy", "boy");
		int sizeMiddle = userService.getAllUsers().size();
		userService.deleteUser(user.getId());
		int sizeAfter = userService.getAllUsers().size();
		assertEquals(sizeBefore + 1, sizeMiddle);
		assertEquals(sizeBefore, sizeAfter);
		
		// User does not exist
		userService.deleteUser(42);
	}

	@Test public void updateRolesTest() {
		User user = userService.getUserById(1L);
		userService.setRoles(user.getId(), "lazy", "boy");
		user = userService.getUserById(1L);
		assertTrue(user.getRoleNames().contains("lazy"));
		assertTrue(user.getRoleNames().contains("boy"));
		
		// User does not exist
		userService.setRoles(42, "lazy", "boy");
	}

	@Test public void updatePasswordTest() {
		User user = userService.createUser("metoo", "metoo", "lazy", "boy");
		userService.updatePassword(user.getId(), "metoo", "gaga");
		user = userService.getUserById(user.getId());
		assertTrue(user.verifyPassword("gaga"));
		
		// We should have a password hash
		assertNotNull(user.getPasswordHash());
		assertFalse(user.getPasswordHash().isEmpty());
		
		// Wrong password
		assertFalse(userService.updatePassword(user.getId(), "42", "gaga"));
		
		// User does not exist
		userService.updatePassword(42, "metoo", "gaga");
	}
}
