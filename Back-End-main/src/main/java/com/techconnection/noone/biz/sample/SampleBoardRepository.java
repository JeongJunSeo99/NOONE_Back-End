package com.techconnection.noone.biz.sample;

import com.techconnection.noone.biz.sample.dto.SampleBoardEntity;
import com.techconnection.noone.common.dao.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleBoardRepository extends BaseRepository<SampleBoardEntity, Long> {

}
