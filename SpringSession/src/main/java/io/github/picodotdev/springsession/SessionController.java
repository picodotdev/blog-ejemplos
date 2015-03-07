package io.github.picodotdev.springsession;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/attributes", method = RequestMethod.POST)
	public String post(@RequestParam(value = "attributeName", required = true) String name, @RequestParam(value = "attributeValue", required = true) String value, HttpSession session, Model model) {
		session.setAttribute(name, value);
		return "redirect:/";
	}
}