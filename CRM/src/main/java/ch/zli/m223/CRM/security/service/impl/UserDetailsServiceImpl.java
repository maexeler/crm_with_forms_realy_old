package ch.zli.m223.CRM.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
	private static class UserDetailsImpl
		extends org.springframework.security.core.userdetails.User
	{
		public UserDetailsImpl(User user) {
			super(
				user.getUsername(),
				user.getPassword(), 
				AuthorityUtils.createAuthorityList(
					user.getRoleNames().stream().map(role -> "ROLE_" + role).toArray(String[]::new))
				);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userService.getUserByName(userName)
			.orElseThrow(() -> 
                new UsernameNotFoundException(String.format("User with userName=%s was not found", userName)));
        return new UserDetailsImpl(user);
	}

}
