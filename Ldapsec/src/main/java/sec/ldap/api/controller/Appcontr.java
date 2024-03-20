package sec.ldap.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class Appcontr {
	@GetMapping("/sec")
	public String Secure()
	{
	return "end point"; 
	}

}
