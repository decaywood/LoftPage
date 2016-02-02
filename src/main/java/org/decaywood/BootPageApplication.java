package org.decaywood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootPageApplication {

	public static void main(String[] args) throws Exception {
		/**
		 * for debug
		 */
		System.setProperty("server.port", "4000");
		System.setProperty("server.context-path", "/");


		SpringApplication.run(BootPageApplication.class, args);
	}

}
