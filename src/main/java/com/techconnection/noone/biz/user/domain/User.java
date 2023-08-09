package com.techconnection.noone.biz.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techconnection.noone.biz.user.dto.UserDtoReq;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String refreshToken;

    @Builder
    public User(UserDtoReq.SignUpDto signUpDto){
        this.email = signUpDto.getEmail();
        this.password = signUpDto.getPassword();
    }


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    //@Builder.Default
    private List<Authority> roles = new ArrayList<>();


    public void setRoles(List<Authority> role) {
        this.roles = role;
        role.forEach(o -> o.setUser(this));
    }

    public void setRefreshToken(String refreshToken) { // 추가!
        this.refreshToken = refreshToken;
    }
}
