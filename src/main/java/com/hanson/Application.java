package com.hanson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 直接运行的入口类
 * @author mac
 *
 */
  
@SpringBootApplication
//@ImportResource("test-config.xml")
public class Application {
	public static void main(String args[]){
		SpringApplication.run(Application.class, args);
	}
}

