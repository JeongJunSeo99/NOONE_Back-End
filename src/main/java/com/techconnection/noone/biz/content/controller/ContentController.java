package com.techconnection.noone.biz.content.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/content")
public class ContentController {

    @GetMapping(path = {"", "/"})
    public String index(Pageable pageable, Model model) throws Exception {
        return "noone/content/index";
    }
}
