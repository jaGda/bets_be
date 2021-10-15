package com.bets_be.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "LOGIN")
    private String login;

    @NotNull
    @Column(name = "MAIL")
    private String mail;

    @OneToMany(
            targetEntity = Coupon.class,
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private List<Coupon> coupons = new ArrayList<>();

    public User(String name, String login, String mail) {
        this.name = name;
        this.login = login;
        this.mail = mail;
    }
}