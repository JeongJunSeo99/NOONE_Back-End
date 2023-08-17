package com.techconnection.noone.biz.point.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techconnection.noone.biz.point.dto.PointRes;
import com.techconnection.noone.biz.user.domain.User;
import com.techconnection.noone.biz.user.dto.UserDtoReq;
import jakarta.persistence.*;
import lombok.*;
import org.joda.time.DateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "point")
@Builder
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private Integer price;

    @Column(name = "trade_cd", nullable = false)
    private String tradeCd;

    @Column(nullable = false)
    private String description;

    private DateTime created_at;

    private DateTime updated_at;

    @Column(nullable = false)
    private String email;

    public static Point createPoint(Integer price, String tradeCd, String des, String email) {
        final Point point = Point.builder()
                .price(price)
                .tradeCd(tradeCd)
                .description(des)
                .email(email)
                .build();
        return point;
    }


}
