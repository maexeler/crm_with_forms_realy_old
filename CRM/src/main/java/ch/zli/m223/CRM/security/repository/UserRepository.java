package ch.zli.m223.CRM.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ch.zli.m223.CRM.security.Role;
import ch.zli.m223.CRM.security.model.User;

/**
 * The user repository.Spring keeps generating our JPA queries.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findAll();

	User findByUserName(String userName);

	default User createUser(String userName, String password) {
		if (findByUserName(userName) != null) { return null; }
		
		User user = new User(userName, new BCryptPasswordEncoder().encode(password));
		return save(user);
	}

	default User addRoleToUser(User user, Role role) {
		return save(user.addRole(role));
	}
}
