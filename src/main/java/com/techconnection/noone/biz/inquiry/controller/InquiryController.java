package com.techconnection.noone.biz.inquiry.controller;

import com.techconnection.noone.biz.inquiry.dto.InquiryDtoReq;
import com.techconnection.noone.biz.inquiry.dto.InquiryDtoRes;
import com.techconnection.noone.biz.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inquiry")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<Object> createInquiry(@RequestBody InquiryDtoReq.createDto createDto) throws Exception {

        inquiryService.createInquiry(createDto);

        return new ResponseEntity<>( "정상적인 접근: 문의글 작성 완료", HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InquiryDtoRes.Res> getAllInquiry() {
        return inquiryService.getAllInquiry();
    }

    @GetMapping("/{inquiryId}")
    @ResponseStatus(HttpStatus.OK)
    public InquiryDtoRes.Detail getInquiryDetail(@PathVariable Long inquiryId) {
        return inquiryService.getInquiryDetail(inquiryId);
    }



}
