package com.techconnection.noone.biz.sample.mapper;

import com.techconnection.noone.biz.sample.dto.SampleBoardEntity;
import com.techconnection.noone.common.dao.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleBoardMapper extends BaseMapper<SampleBoardEntity, Long> {
}
