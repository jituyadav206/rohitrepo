package com.altimetrik.springsecuritywithjwt.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "USER")
public class User {

    @Id
    private int id;
    private String userName;
    private String password;
    private String email;
}
