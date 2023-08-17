package com.techconnection.noone.biz.point.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class PointReq {
    @Getter
    @Setter
    @ToString
    public static class dto {
        private Integer price;
        private String description;

        @Builder
        public dto(Integer price, String description) {
            this.price = price;
            this.description = description;
        }
    }
}
