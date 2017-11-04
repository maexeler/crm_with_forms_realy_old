package ch.zli.m223.CRM.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import ch.zli.m223.CRM.model.User;
import ch.zli.m223.CRM.model.impl.UserImpl;

/**
 * The user repository.Spring keeps generating our JPA queries.
 */
public interface UserRepository extends JpaRepository<UserImpl, Long> {

	/** @return a list of all available users */
	default List<User> findAllUser(Sort sort) {
		return new ArrayList<>(findAll(sort));
	}

	/** Find a user by name
	 * 
	 * @param userName the users name
	 * @return the user or null if not found
	 */
	User findByUserName(String userName);

	/** Create a user
	 * <br> no check for an already existing user with this name is made
	 * 
	 * @param userName its name
	 * @param password its password
	 * @param roleNames its roles
	 * @return the newly created user
	 */
	default User createUser(String userName, String password, String[] roleNames) {
		return save(new UserImpl(userName, password, roleNames));
	}

	/**
	 * Set the users roles
	 * <br> by overriding its old roles
	 * @param user the user
	 * @param roleNames its new roles
	 * @return
	 */
	default User setRoles(User user, String[] roleNames) {
		UserImpl userImpl = (UserImpl)user;
		userImpl.setRoles(roleNames);
		return save(userImpl);
	}

	/** Update a users password
	 * 
	 * @param user the user
	 * @param oldPassword its old password in plain text
	 * @param newPassword its new password in plain text
	 * @return true if successful, false otherwise
	 */
	default boolean updatePassword(User user, String oldPassword, String newPassword) {
		UserImpl userImpl = (UserImpl)user;
		
		if (userImpl.changePassword(oldPassword, newPassword)) {
			save(userImpl);
			return true;
		}
		return false;
	}
}
