package com.techconnection.noone.biz.point.repository;

import com.techconnection.noone.biz.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {

    List<Point> findAllByEmailAndTradeCd(String email, String tradeCd);
}
