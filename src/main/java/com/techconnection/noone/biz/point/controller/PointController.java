package com.techconnection.noone.biz.point.controller;

import com.techconnection.noone.biz.point.dto.PointReq;
import com.techconnection.noone.biz.point.dto.PointRes;
import com.techconnection.noone.biz.point.service.PointService;
import com.techconnection.noone.biz.user.dto.UserDtoReq;
import com.techconnection.noone.common.controller.BaseApiController;
import com.techconnection.noone.common.controller.BaseApiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/point")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PointController extends BaseApiController<BaseApiDto<?>> {

    private final PointService pointService;

    @PostMapping("/new-content/{contentId}")
    public ResponseEntity<Object> pay(@PathVariable Long contentId) throws Exception {

        pointService.pay(contentId);

        return new ResponseEntity<>( "콘텐츠 등록 및 포인트 지급 완료", HttpStatus.OK);
    }
    // 포인트 적립 또는 사용
    // dto.point 음수 또는 양수 값과 description
    @PostMapping("/trade")
    public ResponseEntity<BaseApiDto<?>> testUsePoint(@RequestBody PointReq.dto dto) {
        try {
            return super.ok(new BaseApiDto<>(pointService.testUseAndEarn(dto)));
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "포인트 적립/사용 실패 : " +e.getMessage());
        }
    }

    @GetMapping("/use")
    public ResponseEntity<BaseApiDto<?>> getPointUsage() throws Exception {
        try {
            return super.ok(new BaseApiDto<>(pointService.getUsage()));
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "포인트 사용 내역 조회 실패 : " + e.getMessage());
        }
    }

    @GetMapping("/earn")
    public ResponseEntity<BaseApiDto<?>> getPointEarn() throws Exception {

        try {
            return super.ok(new BaseApiDto<>(pointService.getEarn()));
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "포인트 적립 내역 조회 실패 : " + e.getMessage());
        }
    }
}
