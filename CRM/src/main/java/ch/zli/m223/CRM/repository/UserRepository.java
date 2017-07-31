package ch.zli.m223.CRM.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ch.zli.m223.CRM.model.User;
import ch.zli.m223.CRM.model.impl.UserImpl;

/**
 * The user repository.Spring keeps generating our JPA queries.
 */
public interface UserRepository extends JpaRepository<UserImpl, Long> {

	default List<User> findAllUser(Sort sort) {
		return new ArrayList<>(findAll(sort));
	}

	User findByUserName(String userName);

	default User createUser(String userName, String password) {
		if (findByUserName(userName) != null) { return null; }
		
		UserImpl user = new UserImpl(userName, new BCryptPasswordEncoder().encode(password));
		return save(user);
	}

	default User addRoleToUser(User user, String role) {
		return save(((UserImpl)user).addRole(role));
	}

	default User updateUser(User user, String password, String[] roleNames) {
		UserImpl userImpl = (UserImpl)user;
		userImpl.setPassword(password);
		userImpl.setRoles(roleNames);
		save(userImpl);
		return userImpl;
	}

	default boolean updatePassword(User user, String oldPassword, String newPassword) {
		UserImpl userImpl = (UserImpl)user;
		
		if (userImpl.changePassword(oldPassword, newPassword)) {
			save(userImpl);
			return true;
		}
		return false;
	}
}
