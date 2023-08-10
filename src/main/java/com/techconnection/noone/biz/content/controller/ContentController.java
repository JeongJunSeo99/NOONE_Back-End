package com.techconnection.noone.biz.content.controller;

import com.techconnection.noone.biz.content.dto.ContentModel;
import com.techconnection.noone.biz.content.service.ContentService;
import com.techconnection.noone.common.dto.PageEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/cms/content")
public class ContentController {
    private final ContentService contentService;

    @ModelAttribute("ynMap")
    public Map<String, String> ynCd() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Y", "Y");
        map.put("N", "N");
        return map;
    }

    @GetMapping(path = {"", "/"})
    public String index(Pageable pageable, Model model) throws Exception {
        PageEntity<ContentModel> pageEntity = contentService.getList(new PageEntity<>(pageable));
        log.info("# pageEntity = {}", pageEntity);
        model.addAttribute("contentList", pageEntity);
        return "noone/content/index";
    }

    @GetMapping("/{contentId}")
    public String detail(@PathVariable("contentId") Long id, Model model) throws Exception {
        ContentModel contentModel = contentService.getDetail(id);
        log.info("# content detail page = {}", contentModel);
        model.addAttribute("contentDetail", contentModel);
        return "noone/content/detail";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute ContentModel contentDetail) throws Exception {
        log.info("# Content modify = {}", contentDetail);
        contentService.modify(contentDetail);
        return "redirect:/content";
    }
}
