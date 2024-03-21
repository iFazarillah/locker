package com.lockerz.locker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class LockerApplication {



	public static void main(String[] args) {
		System.out.println("Locker App Starts");
		SpringApplication.run(LockerApplication.class, args);
	}

}
