package com.techconnection.noone.biz.content.repository;

import com.techconnection.noone.biz.content.dto.ContentEntity;
import com.techconnection.noone.common.dao.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends BaseRepository<ContentEntity, Long> {
    List<ContentEntityInterface> findByViewYn(String viewYn);
}
