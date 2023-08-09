package com.techconnection.noone.biz.user.dto;

import com.techconnection.noone.biz.user.domain.User;
import lombok.*;

public class UserDtoReq {
    @Getter
    @Setter
    @ToString
    public static class SignUpDto{
        private String email;
        private String password;


        @Builder
        public SignUpDto(String userId, String password){
            this.email = userId;
            this.password = password;
        }

        public User toUser() {
            return User.builder()
                    .signUpDto(this)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class LoginDto{
        private String email;
        private String password;

        private String name;

        private String phone;

        @Builder
        public LoginDto(String email, String password, String name, String phone) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.phone = phone;
        }

    }
}