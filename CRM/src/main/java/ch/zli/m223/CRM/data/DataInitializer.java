package ch.zli.m223.CRM.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ch.zli.m223.CRM.model.Customer;
import ch.zli.m223.CRM.role.CrmRoles;
import ch.zli.m223.CRM.security.util.FakeUser;
import ch.zli.m223.CRM.service.CustomerService;
import ch.zli.m223.CRM.service.UserService;

/**
 * Fills the data store with some data at server startup
 *
 */
@Component
public class DataInitializer implements ApplicationRunner {

	@Autowired UserService userService;
	@Autowired CustomerService customerService;
	
//	@Autowired AuthenticationManager authenticationManager;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		try {
			makeUsSuperUser();

			// Application data
			Customer c0, c1, c2;
			c0 = customerService.addCustomer("Werner Max", "Tobelrainli 6", "5416 Kirchdorf");
			c1 = customerService.addCustomer("Bohli Armin", "Dorfgasse 2", "8853 Lachen");
			c2 = customerService.addCustomer("Ehrensberger Susi", "Heimstrasse 41", "5417 Untersiggenthal");
			
			customerService.addMemoToCustomer(c0.getId(), "Erstkontakt positiv verlaufen");
			customerService.addMemoToCustomer(c0.getId(), "Sitzung vereinbart");
			customerService.addMemoToCustomer(c0.getId(), "Offerteneingang bestätigt");
			
			customerService.addMemoToCustomer(c1.getId(), "Erstkontakt, Kunde unsicher, nachhaken");
			
			customerService.addMemoToCustomer(c2.getId(), 
					"Chefsekretärin, Hund heisst Mischu, keine Familie, Ansprechpartner für alles"
			);
			
			// Security, create some valid users
			userService.createUser("user",  "user",  CrmRoles.USER);
			userService.createUser("admin", "admin", CrmRoles.ADMIN);
			userService.createUser("usmin", "usmin", CrmRoles.ADMIN, CrmRoles.USER);
		
			// Security, create a none sense user
			userService.createUser("gaga",  "gaga",  "lady", "is gaga");

		} finally {
			removeSuperUser();
		}
	}

	private void makeUsSuperUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(new FakeUser(CrmRoles.ALL_ROLES));
	}

	private void removeSuperUser() {
		SecurityContextHolder.clearContext();
	}
}
