package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		try {
			//Desktop.getDesktop().browse(new URI("http://localhost:8080"));
			Runtime.getRuntime().exec(
					new String[] {"rundll32", "url.dll,FileProtocolHandler", "http://localhost:8080"}
			);
		} catch (Exception e) {
			System.out.println("브라우저 자동 열기 실패: " + e.getMessage());
		}
	}

}
