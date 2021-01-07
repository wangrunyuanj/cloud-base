package com.runyuanj.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableBinding(Sink.class)
public class StreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamApplication.class, args);
    }

    public void getMessage(String message) {
        System.out.println(message);
    }

}
