package ch.zli.m223.CRM.service.impl;

import java.util.Collection;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ch.zli.m223.CRM.model.User;
import ch.zli.m223.CRM.repository.UserRepository;
import ch.zli.m223.CRM.role.CrmRoles;
import ch.zli.m223.CRM.service.UserService;
/**
 * @see UserService
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired private UserRepository userRepository;
	
	@Override
	@RolesAllowed({CrmRoles.ADMIN, CrmRoles.USER})
	public User getUserById(long userId) {
		return userRepository.findOne(userId);
	}

	// This member is used by UserDetailsServiceImpl which is part of the security checking.
	// We have to grant access to everybody, otherwise the login would not work.
	// We may not even use @PermitAll, since the security process is outside of our
	// permission handling
	@Override
	public User getUserByName(String name) {
		return userRepository.findByUserName(name);
	}

	@Override
	@RolesAllowed(CrmRoles.ADMIN)
	public Collection<User> getAllUsers() {
		return userRepository.findAllUser(new Sort("userName"));
	}

	@Override
	@RolesAllowed(CrmRoles.ADMIN)
	public User createUser(String userName, String password, String... roleNames) {
		if (getUserByName(userName) != null) { return null; }
		return userRepository.createUser(userName, password, roleNames);
	}

	@Override
	@RolesAllowed(CrmRoles.ADMIN)
	public void deleteUser(long userId) {
		try { userRepository.delete(userId); }
		catch(EmptyResultDataAccessException ignored) {}
	}

	@Override
	@RolesAllowed(CrmRoles.ADMIN)
	public User setRoles(long userId, String... roleNames) {
		User user = userRepository.findOne(userId);
		if (user == null) { return null; }
		
		return userRepository.setRoles(user, roleNames);
	}

	@Override
	@RolesAllowed({CrmRoles.ADMIN, CrmRoles.USER})
	public boolean updatePassword(long userId, String oldPassword, String newPassword) {
		User user = userRepository.findOne(userId);
		if (user == null) { return false; }
				
		return userRepository.updatePassword(user, oldPassword, newPassword);
	}
}
