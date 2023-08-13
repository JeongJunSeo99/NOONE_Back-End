package com.techconnection.noone.biz.inquiry.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techconnection.noone.biz.inquiry.dto.InquiryDtoReq;
import com.techconnection.noone.biz.user.domain.Authority;
import com.techconnection.noone.biz.user.domain.User;
import com.techconnection.noone.biz.user.dto.UserDtoReq;
import jakarta.persistence.*;
import lombok.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "inquiry")
@Builder
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String title;

    @JsonIgnore
    @Column(nullable = false)
    private String description;

    private String answer;

    @Column(nullable = false)
    private String isAnswer;

    private DateTime created_at;

    private DateTime updated_at;

    private String email;
/*

    @JoinColumn(name = "email")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public void setUser(User user) {
        this.user = user;
    }
*/
    public static Inquiry createInquiry(InquiryDtoReq.createDto createDto,User user) {
        final Inquiry inquiry = Inquiry.builder()
                .title(createDto.getTitle())
                .email(user.getEmail())
                .description(createDto.getDescription())
                .isAnswer("N")
                .build();
        return inquiry;
    }
}
