package com.techconnection.noone.biz.point.service;

import com.techconnection.noone.biz.content.dto.ContentEntity;
import com.techconnection.noone.biz.content.repository.ContentRepository;
import com.techconnection.noone.biz.point.domain.Point;
import com.techconnection.noone.biz.point.repository.PointRepository;
import com.techconnection.noone.biz.user.domain.User;
import com.techconnection.noone.biz.user.dto.UserDtoReq;
import com.techconnection.noone.biz.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor //생성자 주입
public class PointService {

    private final PointRepository pointRepository;

    private final ContentRepository contentRepository;

    private final UserRepository userRepository;

    public void pay(Long contentId){
        Optional<ContentEntity> content = contentRepository.findById(contentId);
        Optional<User> user = userRepository.findById(content.get().getUserId());

        if(content.get().getViewYn() != "Y"){
            content.get().setViewYn("Y");
            contentRepository.save(content.get());
        }

        user.get().setPoint(user.get().getPoint() + 1000);

        userRepository.save(user.get());

        Point point = Point.createPoint(1000, "콘텐츠 등록으로 인한 포인트 적립", user.get().getEmail());

        pointRepository.save(point);

    }
}
