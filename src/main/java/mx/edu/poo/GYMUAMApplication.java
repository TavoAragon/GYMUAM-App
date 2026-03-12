package mx.edu.poo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GYMUAMApplication {
    public static void main(String[] args) {
        SpringApplication.run(GYMUAMApplication.class, args);
        System.out.println("🚀 GYMUAM API iniciada en http://localhost:8080");
    }
}