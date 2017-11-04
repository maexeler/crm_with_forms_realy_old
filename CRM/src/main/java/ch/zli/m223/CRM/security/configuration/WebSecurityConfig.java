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

import ch.zli.m223.CRM.role.CrmRoles;

@Configuration
//@EnableGlobalMethodSecurity(jsr250Enabled  = true) // Add method level security by using @RolesAllowed
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired private AccessDeniedHandler accessDeniedHandler;
	@Autowired private UserDetailsService userDetailsService;
	
	// CrmRoles.ADMIN     allow to access /admin/**
    // CrmRoles.CRM_USER  allow to access /user/**
	// CrmRoles.ALL_ROLES allow to access /authenticatedUsers/**
    // custom 403 access denied handler
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.authorizeRequests()
				// Web API
				.antMatchers("/", "/home/**", "/public/**").permitAll()
				.antMatchers("/admin/**").hasAnyRole(CrmRoles.ADMIN)
				.antMatchers("/user/**").hasAnyRole(CrmRoles.USER)
				.antMatchers("/authenticatedUsers/**").hasAnyRole(CrmRoles.ADMIN, CrmRoles.USER)
				// Rest API
				.antMatchers("/rest/v1/**").permitAll() // permit all for easier development and testing
                                                     // don't do this on the production system				
				.anyRequest().authenticated()
			.and()
				.formLogin()
					.loginPage("/login")
					.permitAll()
					.and()
				.logout()
					.permitAll()
					.and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
			.and()
			// Disable Cross-Site Request Forgery prevention for easier development
            // don't do this on the production system
			.csrf().disable()
			;
	}
	
	/* 
	 * Spring Boot configured this already. But if we are not processing an Http-Request,
	 * as when we'r logging in from a menu or link, spring fails to grant us these access rights.
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
}
