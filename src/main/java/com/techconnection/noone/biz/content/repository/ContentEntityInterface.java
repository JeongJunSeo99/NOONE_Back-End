package com.techconnection.noone.biz.content.repository;

import java.time.LocalDateTime;

public interface ContentEntityInterface {
    Long getContentId();
    String getTitle();
    String getDescription();
    String getCategory();
    Integer getViewCount();
    String getCompanyName();
    LocalDateTime getDeadLine();
    String getShortYn();
}
