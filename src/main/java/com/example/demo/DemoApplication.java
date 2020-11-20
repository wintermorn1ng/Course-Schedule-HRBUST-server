package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        /*System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "8888");*/
        SpringApplication.run(DemoApplication.class, args);
    }

}
