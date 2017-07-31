package ch.zli.m223.CRM.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ch.zli.m223.CRM.security.SpringRole;
import ch.zli.m223.CRM.security.model.User;
import ch.zli.m223.CRM.security.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {

	@Autowired public UserService userService;
	
	/**
	 * This is a wrapper around a domain user object 
	 * implementing all the needed features to be used by spring security
	 */
	@SuppressWarnings("serial")
	public static class UserDetailsImpl
		extends org.springframework.security.core.userdetails.User
	{
		private User user;
		
		public UserDetailsImpl(User user) {
			super(
				user.getUserName(),
				user.getPassword(), 
				AuthorityUtils.createAuthorityList(
					user.getRoleNames().stream().map(role -> SpringRole.ROLE_PREFIX + role).toArray(String[]::new))
				);
			this.user = user;
		}
		public long getId() { return user.getId(); }
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userService.getUserByName(userName);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User with userName=%s was not found", userName));
		}
        return new UserDetailsImpl(user);
	}

}
