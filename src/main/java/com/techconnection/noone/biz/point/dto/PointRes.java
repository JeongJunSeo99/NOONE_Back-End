package com.techconnection.noone.biz.point.dto;

import com.techconnection.noone.biz.point.domain.Point;
import lombok.Builder;
import lombok.Getter;

public class PointRes {

    @Getter
    public static class Res {
        private Long id;
        private Integer price;
        private String tradeCd;
        private String description;

        @Builder
        public Res(Point point) {
            this.id = point.getId();
            this.price = point.getPrice();
            this.tradeCd = point.getTradeCd();
            this.description = point.getDescription();
        }
    }
}
