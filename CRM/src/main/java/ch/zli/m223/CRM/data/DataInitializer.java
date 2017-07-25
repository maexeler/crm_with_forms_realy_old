package ch.zli.m223.CRM.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ch.zli.m223.CRM.model.Customer;
import ch.zli.m223.CRM.role.CrmRole;
import ch.zli.m223.CRM.security.service.UserService;
import ch.zli.m223.CRM.service.CustomerService;

/**
 * Fills the data store with some data at server startup
 *
 */
@Component
public class DataInitializer implements ApplicationRunner {

	@Autowired UserService userService;
	@Autowired CustomerService customerService;
		
	@Override
	@SuppressWarnings("unused")
	public void run(ApplicationArguments args) throws Exception {

		// Application data
		Customer c0, c1, c2;
		c0 = customerService.addCustomer("Werner Max", "Tobelrainli 6", "5416 Kirchdorf");
		c1 = customerService.addCustomer("Bohli Armin", "Dorfgasse 2", "8853 Lachen");
		c2 = customerService.addCustomer("Ehrensberger Susi", "Heimstrasse 41", "5417 Untersiggenthal");
		
		customerService.addMemoToCustomer(c0.getCustomerId(), "Erstkontakt positiv verlaufen");
		customerService.addMemoToCustomer(c0.getCustomerId(), "Sitzung vereinbart");
		customerService.addMemoToCustomer(c0.getCustomerId(), "Offerteneingang bestätigt");
		
		customerService.addMemoToCustomer(c1.getCustomerId(), "Erstkontakt, Kunde unsicher, nachhaken");
		
		customerService.addMemoToCustomer(c2.getCustomerId(), 
				"Chefsekretärin, Hund heisst Mischu, keine Familie, Ansprechpartner für alles"
		);
		
		// Security, create some users
		userService.create("user",  "user",  CrmRole.USER.getRoleName());
		userService.create("admin", "admin", CrmRole.ADMIN.getRoleName());
		userService.create("usmin", "usmin", CrmRole.ADMIN.getRoleName(), CrmRole.USER.getRoleName());
	}

}
