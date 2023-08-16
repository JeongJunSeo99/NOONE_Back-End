package com.techconnection.noone.biz.point.repository;

import com.techconnection.noone.biz.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
