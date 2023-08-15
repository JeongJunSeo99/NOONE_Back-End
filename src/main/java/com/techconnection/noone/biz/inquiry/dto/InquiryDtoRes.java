package com.techconnection.noone.biz.inquiry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.techconnection.noone.biz.inquiry.domain.Inquiry;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class InquiryDtoRes {
    @Getter
    public static class Res {
        private Long inquiryId;

        private String title;
        private String description;

        private String isAnswer;
        @Builder
        public Res(Inquiry inquiry) { // -> 파라미터 postfile 추가
            this.inquiryId = inquiry.getId();
            this.title = inquiry.getTitle();
            this.description = inquiry.getDescription();
            this.isAnswer = inquiry.getIsAnswer();
        }
    }

    @Getter
    public static class Detail {
        private Long inquiryId;

        private String title;
        private String description;

        private String isAnswer;

        private String answer;

        @Builder
        public Detail(Inquiry inquiry) { // -> 파라미터 postfile 추가
            this.inquiryId = inquiry.getId();
            this.title = inquiry.getTitle();
            this.description = inquiry.getDescription();
            this.isAnswer = inquiry.getIsAnswer();
            this.answer = inquiry.getAnswer();
        }
    }
}
