package com.sts;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //this tells spring to refresh the attributes of this bean/class when a empty post request is made to a webhook of spring actuator http://localhost:8080/actuator/refresh, in this case
public class Controller {
	
	@Value("${app.description: default}") //if this property is not defined the default will be take as value for the variable
	private String valueFromProperty;
	
	@Value("${list.values}") //way to store comma separate property in a list/array variable
	private List<String> values;
	
	@Value("#{${dbValues}}") //way to store property array in a variable
	private Map<String, String> dbValues;
	
	@Autowired //injecting property value directly in variable class, check the Variables class
	private Variables variables;
	
	@Autowired 
	private Environment environment;
	
	@GetMapping
	public String greeting() {
		return values.get(0); //getting value from property file
		//return variables.getA();
	}
	
	@GetMapping("/env")
	public String envDetails() {
		return environment.toString(); //way to get information stored in environment, using this we can look up profiles and properties but it is not recommended because it affects testability when a bussiness logic gets triggered from environment objects
	}
}
