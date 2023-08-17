package com.techconnection.noone.biz.point.service;

import com.techconnection.noone.biz.content.dto.ContentEntity;
import com.techconnection.noone.biz.content.repository.ContentRepository;
import com.techconnection.noone.biz.point.domain.Point;
import com.techconnection.noone.biz.point.dto.PointReq;
import com.techconnection.noone.biz.point.dto.PointRes;
import com.techconnection.noone.biz.point.repository.PointRepository;
import com.techconnection.noone.biz.user.domain.User;
import com.techconnection.noone.biz.user.dto.UserDtoReq;
import com.techconnection.noone.biz.user.repository.UserRepository;
import com.techconnection.noone.common.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        Point point = Point.createPoint(1000, "EARN","콘텐츠 등록으로 인한 포인트 적립", user.get().getEmail());

        pointRepository.save(point);

    }

    public PointRes.Res testUseAndEarn(PointReq.dto dto) throws Exception {
        try {
            Optional<User> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByEmail);

            if (dto.getPrice() < 0 && user.get().getPoint() < (dto.getPrice() * -1)) {
                throw new Exception("보유 포인트가 부족합니다.");
            }

            user.get().setPoint(user.get().getPoint() + dto.getPrice());
            userRepository.save(user.get());

            String tradeCd = (dto.getPrice() < 0 ) ? "USE" : "EARN";
            Point point = Point.createPoint(dto.getPrice(), tradeCd, dto.getDescription(), user.get().getEmail());

            return new PointRes.Res(pointRepository.save(point));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public List<PointRes.Res> getUsage() throws Exception{
        try{
            Optional<User> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByEmail);
            return pointRepository.findAllByEmailAndTradeCd(user.get().getEmail(), "USE").stream().map(PointRes.Res::new).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<PointRes.Res> getEarn() throws Exception{
        try{
            Optional<User> user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByEmail);
            return pointRepository.findAllByEmailAndTradeCd(user.get().getEmail(), "EARN").stream().map(PointRes.Res::new).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

}
