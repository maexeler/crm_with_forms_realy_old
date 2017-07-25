package ch.zli.m223.CRM.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import ch.zli.m223.CRM.role.CrmRole;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired private AccessDeniedHandler accessDeniedHandler;
	@Autowired private UserDetailsService userDetailsService;
	
	// CrmRoles.ADMIN    allow to access /admin/**
    // CrmRoles.CRM_USER allow to access /user/**
    // custom 403 access denied handler
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/", "/home", "/public/**").permitAll()
				.antMatchers("/admin/**").hasAnyRole(CrmRole.ADMIN.getRoleName())
				.antMatchers("/user/**").hasAnyRole(CrmRole.USER.getRoleName())
				.anyRequest().authenticated()
			.and()
				.formLogin()
					.loginPage("/login")
					.permitAll()
					.and()
				.logout()
					.permitAll()
					.and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}
	
	/* 
	 * Spring Boot configured this already. But if we are not processing an Http-Request,
	 * as when we'r logging in from a menu, spring fails to grant us these access rights.
	 */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
        
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

/*
	// TODO: Test only. create 3 users, admin, user and an admin-user
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("user").roles(CrmRoles.CRM_USER)
                .and()
                .withUser("admin").password("admin").roles(CrmRoles.ADMIN)
                .and()
                .withUser("usmin").password("usmin").roles(CrmRoles.CRM_USER, CrmRoles.ADMIN);
    }
*/
}
