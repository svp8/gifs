package com.gifmain;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Lazy;

@EnableDiscoveryClient
@SpringBootApplication
public class GifMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(GifMainApplication.class, args);
	}

}
