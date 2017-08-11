package ch.zli.m223.CRM.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.zli.m223.CRM.model.User;
import ch.zli.m223.CRM.service.UserService;

@Controller
public class UserAdministrationController {

	@Autowired private UserService userService;
	
	@RequestMapping("/admin/showUsers")
	public String showUsers(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "showUserList";
	}
	
	@RequestMapping("/admin/showUser/{userId}")
	public String showUser(Model model, @PathVariable("userId") long id) {
		model.addAttribute("user", userService.getUserById(id));
		return "showUser";
	}
	
	@RequestMapping("/admin/addNewUser")
	public String addNewUser(Model model) {
		// show a new user
		return "showUserNewForm";
	}
	
	@RequestMapping("/admin/deletUser/{userId}")
	public String deleteUser(Model model, @PathVariable("userId") long id) {
		userService.deleteUser(id);
		return "redirect:/admin/showUsers";
	}
	
	@RequestMapping("/admin/modifyUser/{userId}")
	public String modifyUser(Model model, @PathVariable("userId") long id) {
		// show given user
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "showUserDetailForm";
	}
		
	@RequestMapping("/admin/saveUser")
	public String saveNewUser(Model model, 
		@RequestParam("username") String name,
		@RequestParam("password") String password,
		@RequestParam(value="rolenames", defaultValue="") String[] roles)
	{
		// create a new user
		User user = userService.createUser(name, password, roles);
		if (user == null) {
			// Try it again
			model.addAttribute("error", "userName already exists");
			model.addAttribute("username", name);
			model.addAttribute("password", password);
			return "showUserNewForm";
		}
		return "redirect:/admin/showUsers";
	}
	
	@RequestMapping("/admin/saveUser/{id}")
	public String saveModifiedUser(@PathVariable("id") long id,
		@RequestParam("password") String password,
		@RequestParam(value="rolenames", defaultValue="") String[] roles)
	{
		userService.updateRoles(id, password, roles);
		return "redirect:/admin/showUser/" + id;
	}
	
	private static final String CANGE_PWD_URL = "/authenticatedUsers/changePassword/";
	private static final String SAVE_PWD_URL = "/authenticatedUsers/savePassword/{userId}";
	
	@RequestMapping(CANGE_PWD_URL + "{userId}")
	public String changePassword(Model model, @PathVariable("userId") long id) {
		// show given user
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "showChangePasswordForm";
	}

	@RequestMapping(SAVE_PWD_URL)
	public String saveModifiedUser(Model model, @PathVariable("userId") long id,
		@RequestParam("oldPassword") String oldPassword,
		@RequestParam("newPassword0") String newPassword0,
		@RequestParam("newPassword1") String newPassword1)
	{
		if (!newPassword0.equals(newPassword1)) {
			return "redirect:" + CANGE_PWD_URL + id + "?errorNewPassword=true";
		};
		
		if (!userService.updatePassword(id, oldPassword, newPassword0)) {
			return "redirect:" + CANGE_PWD_URL + id + "?errorOldPassword=true";
		}
		return "redirect:/";
	}
	
	@RequestMapping("authenticatedUsers/doit")
	public String doIt() {
		return "redirect:/";
		
	}
}
