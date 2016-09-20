package se.uu.its.integration.ladok2groups.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.uu.its.integration.ladok2groups.service.Ladok2Groups;

@RestController
public class RootController {
	
	@Autowired
	Ladok2Groups ladokGroups;
    
    @RequestMapping("")
    public String index() {
    	try {
			ladokGroups.updateGroupEvents();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "Hello";
    }
}
