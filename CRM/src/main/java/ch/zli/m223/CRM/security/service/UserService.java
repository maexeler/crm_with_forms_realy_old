package ch.zli.m223.CRM.security.service;

import java.util.List;
import java.util.Optional;

import ch.zli.m223.CRM.security.model.User;

/** The user service */
public interface UserService {
	
	/**
	 * Get an user object by its id
	 * @param id the users id
	 * @return Optional containing the user or null
	 */
	Optional<User> getUserById(long id);

	/**
	 * Get an user object by its user mane
	 * @param name the users name
	 * @return Optional containing the user or null
	 */
    Optional<User> getUserByName(String name);

    /**
     * @return a (possibly empty) list of users
     */
    List<User> getAllUsers();

    /**
     * Create a new User</br>
     * and try to give him all the desired roles
     * @param name user name
     * @param password password as plain text
     * @param roleName an array of allowed role manes
     * @return a new user or null if a user with this name already exist
     */
    Optional<User> create(String name, String password, String... roleNamess);
}
