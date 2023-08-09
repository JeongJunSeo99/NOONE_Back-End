package com.techconnection.noone;

import com.techconnection.noone.fileupload.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class NooneApplication {

	public static void main(String[] args) {
		SpringApplication.run(NooneApplication.class, args);
	}

}
