package br.com.seuapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PautaApiIntegracaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PautaApiIntegracaoApplication.class, args);
    }
}
