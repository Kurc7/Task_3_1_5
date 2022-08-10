package com.example.demo.repository;

import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

public interface  UserRepository  extends JpaRepository<User, Long> {
}
