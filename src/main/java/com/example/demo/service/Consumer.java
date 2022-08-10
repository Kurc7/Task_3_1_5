package com.example.demo.service;

import com.example.demo.model.User;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.*;

@Service
public class Consumer {
    private static String code ="CODE: ";
    UserService userService;

    public Consumer(UserService userService) {
        this.userService = userService;
    }

    RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "http://94.198.50.185:7081/api/users";
    public static String cookie;

    public void getAllUsers() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<User[]> response = restTemplate.exchange(
                BASE_URL, HttpMethod.GET, entity, new ParameterizedTypeReference<User[]>() {
                });

        //сохраняем пользователей из ответа API в базу
        List<User> objects = List.of(response.getBody());
        for (User user : objects) {
            userService.save(user);
        }
        //сохраняем куки сессии
        cookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE).split(";")[0];
    }

    public void postCreateUser() throws IOException {
        HttpHeaders headersCreate = new HttpHeaders();
        headersCreate.setContentType(MediaType.APPLICATION_JSON);
        headersCreate.add("Cookie", cookie);

        userService.save(new User(3l, "James", "Brown", (byte) 18));
        User newUser = userService.getUserById(3l);

        JSONObject newUserJson = new JSONObject();
        newUserJson.put("id", newUser.getId());
        newUserJson.put("name", newUser.getName());
        newUserJson.put("lastName", newUser.getLastName());
        newUserJson.put("age", newUser.getAge());

        HttpEntity<String> request = new HttpEntity<String>(newUserJson.toString(), headersCreate);
        ResponseEntity response = restTemplate.exchange(BASE_URL, HttpMethod.POST, request, String.class);
        code += response.getBody();

    }

    public void putUpdateUser() throws IOException {
        HttpHeaders headersUpdate = new HttpHeaders();
        headersUpdate.setContentType(MediaType.APPLICATION_JSON);
        headersUpdate.add("Cookie", cookie);

        User updateUser = userService.getUserById(3l);
        updateUser.setName("Thomas");
        updateUser.setLastName("Shelby");
        userService.update(updateUser);

        JSONObject updateUserJson = new JSONObject();
        updateUserJson.put("id", updateUser.getId());
        updateUserJson.put("name", updateUser.getName());
        updateUserJson.put("lastName", updateUser.getLastName());
        updateUserJson.put("age", updateUser.getAge());

        HttpEntity<String> request = new HttpEntity<String>(updateUserJson.toString(), headersUpdate);
        ResponseEntity response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, request, String.class);
        code += response.getBody();
    }

    public void deleteUser() throws IOException {
        HttpHeaders headersUpdate = new HttpHeaders();
        headersUpdate.setContentType(MediaType.APPLICATION_JSON);
        headersUpdate.add("Cookie", cookie);

        Long id = 3l;
        userService.delete(id);


        HttpEntity<String> request = new HttpEntity<String>(headersUpdate);
        ResponseEntity response = restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.DELETE, request, String.class);
        code += response.getBody();
        System.out.println(code);
    }
}
