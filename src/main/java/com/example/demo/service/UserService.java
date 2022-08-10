package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    User getUserById(long id);
    void save(User user);
    void update(User user);
    void delete(long id);
}
