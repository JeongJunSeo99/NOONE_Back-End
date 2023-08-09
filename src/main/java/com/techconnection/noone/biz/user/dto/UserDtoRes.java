package com.techconnection.noone.biz.user.dto;

import lombok.*;

public class UserDtoRes {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenDto {
        private String access_token;
        private String refresh_token;
    }
}
