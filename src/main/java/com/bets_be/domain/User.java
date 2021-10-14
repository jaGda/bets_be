package com.bets_be.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    private Long userId;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "LOGIN")
    private String login;

    @NotNull
    @Column(name = "MAIL")
    private String mail;

    public User(String name, String login, String mail) {
        this.name = name;
        this.login = login;
        this.mail = mail;
    }
}
