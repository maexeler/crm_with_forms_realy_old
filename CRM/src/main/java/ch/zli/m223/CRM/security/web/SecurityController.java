package ch.zli.m223.CRM.security.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SecurityController {
	
	@GetMapping(value = {"/login/", "/login"})
    public String loginOrOut(@RequestParam Map<String,String> allRequestParams) {
		if (allRequestParams.containsKey("logout")) {
			return "redirect:/";
		}
        return "/security/login";
    }
	
	@GetMapping("/403")
    public String error403() {
        return "/security/403";
    }

}
