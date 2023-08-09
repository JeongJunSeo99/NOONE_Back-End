package com.techconnection.noone.biz.user.controller;

import com.techconnection.noone.biz.user.domain.User;
import com.techconnection.noone.biz.user.dto.UserDtoReq;
import com.techconnection.noone.biz.user.dto.UserDtoRes;
import com.techconnection.noone.biz.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "signup")
    public ResponseEntity<Object> signup(@RequestBody UserDtoReq.SignUpDto signUpDto) throws Exception {
        userService.signUp(signUpDto); //, imgFile);
        return new ResponseEntity<>("정상적인 접근: 회원가입 완료", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> login(@RequestBody UserDtoReq.LoginDto loginDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        UserDtoRes.TokenDto token = userService.login(loginDto);
        String accesToken = token.getAccess_token().toString();
        String refreshToken = token.getRefresh_token().toString();

        //httpHeaders.add("Authorization", "Bearer " + accesToken);
        httpHeaders.add("accssAuthorization", "Bearer " + accesToken);
        httpHeaders.add("refreshAuthorization", "Bearer " + refreshToken);
        return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
        //return userService.login(loginDto);
    }

    @GetMapping("/refresh")
    public ResponseEntity<UserDtoRes.TokenDto> refresh(@RequestBody UserDtoRes.TokenDto token) throws Exception {
        return new ResponseEntity<>(userService.refreshAccessToken(token), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() throws Exception {
        Optional<User> user = userService.test();
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }
}
