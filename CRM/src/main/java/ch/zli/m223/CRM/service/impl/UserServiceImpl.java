package ch.zli.m223.CRM.service.impl;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
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
	public User getUserById(long id) {
		return userRepository.findOne(id);
	}

	// This member is used by UserDetailsServiceImpl which is part of the security checking.
	// We have to grant access to everybody, otherwise it login would not work.
	// We may not even use @PermitAll, since the security process is outside of our
	// permission handling
	@Override
	public User getUserByName(String name) {
		return userRepository.findByUserName(name);
	}

	@Override
	@RolesAllowed(CrmRoles.ADMIN)
	public List<User> getAllUsers() {
		return userRepository.findAllUser(new Sort("userName"));
	}

	@Override
	@RolesAllowed(CrmRoles.ADMIN)
	public User createUser(String name, String password, String... roleNames) {
		User user = userRepository.createUser(name, password);
		if (user == null) { return null; }
		
		for (String roleName : roleNames) {
			userRepository.addRoleToUser(user, roleName);
		}
		return user;
	}

	@Override
	@RolesAllowed(CrmRoles.ADMIN)
	public void deleteUser(long userId) {
		userRepository.delete(userId);
	}

	@Override
	@RolesAllowed(CrmRoles.ADMIN)
	public User updateRoles(long id, String password, String... roleNames) {
		User user = userRepository.findOne(id);
		if (user == null) { return null; }
		
		return userRepository.updateRoles(user, password, roleNames);
	}

	@Override
//	@RolesAllowed({CrmRoles.ADMIN, CrmRoles.USER})
	public boolean updatePassword(long userId, String oldPassword, String newPassword) {
		User user = userRepository.findOne(userId);
		if (user == null) { return false; }
				
		return userRepository.updatePassword(user, oldPassword, newPassword);
	}
}
