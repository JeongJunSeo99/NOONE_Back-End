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

        private String name;

        private String phone;

        private String passwordConfirm;

        private String verificationCode;


        @Builder
        public SignUpDto(String userId, String password, String name, String phone){
            this.email = userId;
            this.password = password;
            this.name = name;
            this.phone = phone;
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



        @Builder
        public LoginDto(String email, String password) {
            this.email = email;
            this.password = password;
        }

    }
}