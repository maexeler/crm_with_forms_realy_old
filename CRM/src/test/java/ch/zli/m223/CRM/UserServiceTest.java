package ch.zli.m223.CRM;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
		List<User> users = userService.getAllUsers();
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
	}

	@Test public void updateRolesTest() {
		User user = userService.getUserById(1L);
		userService.updateRoles(user.getId(), "lazy", "boy");
		user = userService.getUserById(1L);
		assertTrue(user.getRoleNames().contains("lazy"));
		assertTrue(user.getRoleNames().contains("boy"));
		
		// User does not exist
		userService.updateRoles(42, "lazy", "boy");
	}

	@Test public void updatePasswordTest() {
		User user = userService.getUserById(1L);
		String oldPassword = user.getPassword();
		userService.updatePassword(user.getId(), oldPassword, "gaga");
		user = userService.getUserById(1L);
		assertTrue(user.verifyPassword("gaga"));
		
		// Wrong password
		assertFalse(userService.updatePassword(user.getId(), "42", "gaga"));
		
		// User does not exist
		userService.updatePassword(42, oldPassword, "gaga");
	}
}
