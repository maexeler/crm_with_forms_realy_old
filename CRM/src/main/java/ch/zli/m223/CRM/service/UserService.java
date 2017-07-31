package ch.zli.m223.CRM.service;

import java.util.List;

import ch.zli.m223.CRM.model.User;

/** The user service */
public interface UserService {
	
	/**
	 * Get an user object by its id
	 * @param id the users id
	 * @return Optional containing the user or null
	 */
	User getUserById(long id);

	/**
	 * Get an user object by its user mane
	 * @param name the users name
	 * @return Optional containing the user or null
	 */
    User getUserByName(String name);

    /**
     * @return a (possibly empty) list of users
     */
    List<User> getAllUsers();

    /**
     * Create a new User</br>
     * and try to give him all the desired roles
     * @param name user name
     * @param password password as plain text
     * @param roleName an array of allowed role names
     * @return a new user or null if a user with this name already exist
     */
    User createUser(String name, String password, String... roleNames);

    /**
     * Delete a user</br>
     * Do nothing if the user does not exist
     * @param userId the user id
     */
	void deleteUser(long userId);

	/**
	 * Update a given user
	 * @param id the user id
     * @param password password as plain text
     * @param roleName an array of allowed role manes
     * @return a the updated user or null if a user with this id does not exist
	 */
	User updateUser(long id, String password, String... roleNames);

	/**
	 * Change the password for a given user
	 * @param userId the user id
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return true if password has been changed, false otherwise
	 */
	boolean updatePassword(long userId, String oldPassword, String newPassword);
}
