package com.techconnection.noone.biz.content.repository;

import com.techconnection.noone.biz.content.dto.TagEntity;
import com.techconnection.noone.common.dao.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends BaseRepository<TagEntity, Long> {
}
