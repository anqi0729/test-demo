package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * index controller
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
public class IndexController {




	@RequestMapping("/home")
//	@ResponseBody
	public String index(Model model) {
		System.out.println("welcome");
		return "index";
	}


	
}
