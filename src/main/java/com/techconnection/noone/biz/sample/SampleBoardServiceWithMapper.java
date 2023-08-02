package com.techconnection.noone.biz.sample;

import com.techconnection.noone.biz.sample.dto.SampleBoardEntity;
import com.techconnection.noone.biz.sample.dto.SampleBoardModel;
import com.techconnection.noone.biz.sample.mapper.SampleBoardMapper;
import com.techconnection.noone.common.code.ResultCode;
import com.techconnection.noone.common.exception.BizException;
import com.techconnection.noone.common.service.BaseServiceImplWithMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SampleBoardServiceWithMapper
        extends BaseServiceImplWithMapper<SampleBoardModel, SampleBoardEntity, Long, SampleBoardMapper> {
    //일단 JPA로 되지 않을까 싶음
    public SampleBoardServiceWithMapper(SampleBoardMapper sampleBoardMapper) {
        this.mapper = sampleBoardMapper;
    }


    @Override
    public void remove(Long pk) throws Exception {
        throw new BizException(ResultCode.NOT_SUPPORTED);
    }
}
