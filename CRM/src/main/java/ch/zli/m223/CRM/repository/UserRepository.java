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

	default List<User> findAllUser(Sort sort) {
		return new ArrayList<>(findAll(sort));
	}

	User findByUserName(String userName);

	default User createUser(String userName, String password, String[] roleNames) {
		return save(new UserImpl(userName, password, roleNames));
	}

	default User setRoles(User user, String[] roleNames) {
		UserImpl userImpl = (UserImpl)user;
		userImpl.setRoles(roleNames);
		return save(userImpl);
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
