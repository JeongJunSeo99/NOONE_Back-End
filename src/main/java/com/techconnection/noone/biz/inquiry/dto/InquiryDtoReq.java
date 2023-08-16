package com.techconnection.noone.biz.inquiry.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class InquiryDtoReq {
        @Getter
        @Setter
        @ToString
        public static class createDto{
            private String title;
            private String description;



            @Builder
            public createDto(String title, String description){
                this.title = title;
                this.description = description;
            }

        }

    @Getter
    @Setter
    @ToString
    public static class answerDto{
        private String answer;

    }
}
