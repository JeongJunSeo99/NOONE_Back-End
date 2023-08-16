package com.techconnection.noone.biz.point.controller;

import com.techconnection.noone.biz.point.service.PointService;
import com.techconnection.noone.biz.user.dto.UserDtoReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/point")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @PostMapping("/{contentId}")
    public ResponseEntity<Object> pay(@PathVariable Long contentId) throws Exception {

        pointService.pay(contentId);

        return new ResponseEntity<>( "콘텐츠 등록 및 포인트 지급 완료", HttpStatus.OK);
    }
}
