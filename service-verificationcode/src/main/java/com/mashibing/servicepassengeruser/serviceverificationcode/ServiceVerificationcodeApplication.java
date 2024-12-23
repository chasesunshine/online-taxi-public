package com.mashibing.servicepassengeruser.serviceverificationcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
* @author 马佳健
* @date 2024/12/16
* @description
*/
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceVerificationcodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceVerificationcodeApplication.class, args);
	}

}
