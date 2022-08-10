package com.example.demo.model;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User{
    @Id
    private Long id;
    private String name;
    private String lastName;
    private Byte age;
}