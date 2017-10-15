package ch.zli.m223.CRM.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.zli.m223.CRM.model.User;
import ch.zli.m223.CRM.model.dto.RolesDto;
import ch.zli.m223.CRM.model.dto.UserDto;
import ch.zli.m223.CRM.service.UserService;

@RestController
public class UserRestController {

	@Autowired
	private UserService userService;
	
	/**
	 * Get all users
	 * @return a (possibly empty) list of users
	 */
	@RequestMapping(value="/rest/users", method=RequestMethod.GET)
	public List<UserDto> showUserList() {
		List<User> users = userService.getAllUsers();
		ArrayList<UserDto> res = new ArrayList<UserDto>();
		users.forEach(user -> res.add(new UserDto(user)));
		return res;
	}
	
	// CRUD functions for Users
	
	/**
	 * Create a new user
	 * @param userDto the new user
	 * @return the new User
	 */
	@RequestMapping(value="/rest/user/create", method=RequestMethod.PUT)
	public UserDto createUser(@RequestBody UserDto userDto) {
		User user = 
			userService.createUser(userDto.userName, userDto.password, userDto.roles.stream().toArray(String[]::new));
		return new UserDto(user);
	}
	
	/**
	 * Get (read) an individual user
	 * @param id the users id
	 * @return a user or null if not found
	 */
	@RequestMapping(value="/rest/user/{id}", method=RequestMethod.GET)
	public UserDto showUser(@PathVariable("id") long id) {
		User user = userService.getUserById(id);
		return new UserDto(user);
	}
	
	/**
	 * Update the users roles
	 * @param userDto roles must be set
	 * @return the updated user
	 */
	@RequestMapping(value="/rest/user/{id}/update/roles", method=RequestMethod.PUT)
	public UserDto updateUserRoles(
		@PathVariable("id") long id,
		@RequestBody RolesDto roles)
	{
		User user = 
			userService.updateRoles(id, roles.roles);
		return new UserDto(user);
	}
	
	/**
	 * Update the users password
	 * @param oldPassword old password as hash or as plain text
	 * @param newPassword new password as plain text
	 * @return the updated user
	 */
	@RequestMapping(value="/rest/user/{id}/update/password", method=RequestMethod.PUT)
	public boolean updateUserPassword(
		@PathVariable("id") long id, 
		@RequestParam("oldPassword") String oldPassword,
		@RequestParam("newPassword") String newPassword)
	{
		return userService.updatePassword(id, oldPassword, newPassword);
	}
	
	/**
	 * Delete an individual user
	 * @param userId the users id
	 */
	@RequestMapping(value="/rest/user/{id}/delete", method=RequestMethod.PUT)
	public void deleteUser(@PathVariable("id") long userId) {
		userService.deleteUser(userId);
	}
	
}
