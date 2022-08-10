package com.example.demo;

import com.example.demo.service.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application {
    static Consumer consumer;

    @Autowired
    public Application(Consumer consumer) {
        this.consumer = consumer;
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
        consumer.getAllUsers();
        consumer.postCreateUser();
        consumer.putUpdateUser();
        consumer.deleteUser();
    }
}
