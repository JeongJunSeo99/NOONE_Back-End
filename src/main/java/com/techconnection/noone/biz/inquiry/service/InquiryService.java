package com.techconnection.noone.biz.inquiry.service;

import com.techconnection.noone.biz.inquiry.domain.Inquiry;
import com.techconnection.noone.biz.inquiry.dto.InquiryDtoReq;
import com.techconnection.noone.biz.inquiry.dto.InquiryDtoRes;
import com.techconnection.noone.biz.inquiry.repository.InquiryRepository;
import com.techconnection.noone.biz.user.domain.Authority;
import com.techconnection.noone.biz.user.domain.User;
import com.techconnection.noone.biz.user.dto.UserDtoReq;
import com.techconnection.noone.biz.user.repository.UserRepository;
import com.techconnection.noone.common.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor //생성자 주입
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    private final UserRepository userRepository;
    public void createInquiry(InquiryDtoReq.createDto createDto){
        Optional<User> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByEmail);

        Inquiry inquiry = Inquiry.createInquiry(createDto,user.get());

        //user.get().setInquirys(Collections.singletonList(inquiry));

        inquiryRepository.save(inquiry);

        //userRepository.save(user.get());

    }

    public List<InquiryDtoRes.Res> getAllInquiry() {
        Optional<User> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByEmail);

        List<InquiryDtoRes.Res> a = new ArrayList<>();

        List<Inquiry> inquiries = inquiryRepository.findAll();

        for (Inquiry inquiry : inquiries) {
            a.add(InquiryDtoRes.Res.builder()
                    .inquiry(inquiry)
                    .build());
        }

        return a;

    }

    public List<InquiryDtoRes.Res> getUserInquiry() {
        Optional<User> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByEmail);
        System.out.println(user.get().getEmail());
        List<InquiryDtoRes.Res> a = new ArrayList<>();

        List<Inquiry> inquiries = inquiryRepository.findAllByEmail(user.get().getEmail());
        System.out.println(inquiries);
        for (Inquiry inquiry : inquiries) {
            a.add(InquiryDtoRes.Res.builder()
                    .inquiry(inquiry)
                    .build());
        }

        return a;

    }

    public InquiryDtoRes.Detail getInquiryDetail(Long inquiryId) {
        Optional<User> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByEmail);

        Optional<Inquiry> inquiry = inquiryRepository.findById(inquiryId);

        return InquiryDtoRes.Detail.builder()
                .inquiry(inquiry.get())
                .build();
    }

    public void answer(Long inquiryId, InquiryDtoReq.answerDto answerDto) {
        Optional<User> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByEmail);

        Optional<Inquiry> inquiry = inquiryRepository.findById(inquiryId);

        inquiry.get().setAnswer(answerDto.getAnswer());
        inquiry.get().setIsAnswer("Y");

        inquiryRepository.save(inquiry.get());
    }


}
