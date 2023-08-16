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
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody UserDtoReq.SignUpDto signUpDto) throws Exception {

        userService.signUp(signUpDto);

        return new ResponseEntity<>( "정상적인 접근: 회원가입 완료", HttpStatus.CREATED);
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

    @PutMapping()
    public ResponseEntity<Object> updateUser(@RequestBody UserDtoReq.UpdateDto updateDto) throws Exception {

        userService.updateUser(updateDto);

        return new ResponseEntity<>( "회원 정보 수정 완료", HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<Object> deleteUser() throws Exception {

        userService.deleteUser();

        return new ResponseEntity<>( "회원 탈퇴 완료", HttpStatus.OK);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public UserDtoRes.UserRes getUser() throws Exception {
        return userService.getUser();
    }

}

