package br.com.eits.boot.application.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/secret")
	@CrossOrigin
	public String secretService() {
		return "A secret message";
	}

}
