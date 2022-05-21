package com.sts;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration //treats this class as bean
@ConfigurationProperties("db") //gets variable value from application.properties prefixed with db

public class Variables {
	private String name;

	public String getA() {
		return name;
	}

	public void setA(String name) {
		this.name = name;
	}
	
	
}
