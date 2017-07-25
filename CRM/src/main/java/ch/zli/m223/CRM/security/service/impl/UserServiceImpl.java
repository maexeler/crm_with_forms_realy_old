package ch.zli.m223.CRM.security.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ch.zli.m223.CRM.role.CrmRole;
import ch.zli.m223.CRM.security.Role;
import ch.zli.m223.CRM.security.model.User;
import ch.zli.m223.CRM.security.repository.UserRepository;
import ch.zli.m223.CRM.security.service.UserService;
/**
 * @see UserService
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired private UserRepository userRepository;
	
	@Override
	public Optional<User> getUserById(long id) {
		return Optional.ofNullable(userRepository.findOne(id));
	}

	@Override
	public Optional<User> getUserByName(String name) {
		return Optional.ofNullable(userRepository.findByUserName(name));
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll(new Sort("userName"));
	}

	@Override
	public Optional<User> create(String name, String password, String... roleNames) {
		User user = userRepository.createUser(name, password);
		if (user == null) { return Optional.ofNullable(null); }
		
		Role role = CrmRole.ADMIN; // Any enumeration will do
		for (String roleName : roleNames) {
			if (role.getRoleExists(roleName)) {
				userRepository.addRoleToUser(user, role.getRoleByName(roleName));
			}
		}
		return Optional.of(user);
	}
}
