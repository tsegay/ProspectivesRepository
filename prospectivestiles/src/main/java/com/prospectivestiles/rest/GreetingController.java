package com.prospectivestiles.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.prospectivestiles.domain.UserEntity;

//@RestController
@Controller
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

//    @RequestMapping("/greeting")
	@RequestMapping(value = "/greeting", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
    public Greeting greeting(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
	
	public Map<String, Object> testRestTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		Page page = restTemplate.getForObject("http://graph.facebook.com/pivotalsoftware", Page.class);
		
		System.out.println("Name:    " + page.getName());
		System.out.println("About:   " + page.getAbout());
		System.out.println("Phone:   " + page.getPhone());
		System.out.println("Website: " + page.getWebsite());
		Map<String, Object> test = new HashMap<String, Object>();
		test.put("Name", page.getName());
		test.put("About", page.getAbout());
		test.put("Phone", page.getPhone());
		test.put("Website", page.getWebsite());
		
		return test;
	}
	
	@RequestMapping(value="/testRestTemplate", method = RequestMethod.GET)
	public String showtestRestTemplate(Model model) {
		
//		GreetingController greetingController = new GreetingController();
		Map<String, Object> testRestTemplate = testRestTemplate();
		model.addAttribute("testRestTemplate", testRestTemplate);
		return "testRestTemplate";
	}
	
    
}
