package com.techconnection.noone.biz.user.repository;

import com.techconnection.noone.biz.user.domain.Authority;
import com.techconnection.noone.biz.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
