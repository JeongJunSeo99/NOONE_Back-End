package com.techconnection.noone.api.content;

import com.amazonaws.util.IOUtils;
import com.techconnection.noone.biz.content.dto.ContentModel;
import com.techconnection.noone.biz.content.service.ContentService;
import com.techconnection.noone.common.controller.BaseApiController;
import com.techconnection.noone.common.controller.BaseApiDto;
import com.techconnection.noone.common.utils.ResponseEntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/content")
public class ContentApiController extends BaseApiController<BaseApiDto<?>> {
    private final ContentService contentService;

    @GetMapping(path = {"", "/"})
    public ResponseEntity<BaseApiDto<?>> findAll() {
        try {
            List<ContentModel> list = contentService.getListByViewYn(10);
            return super.ok(new BaseApiDto<>(list));
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "조회 실패 : " + e.getMessage());
        }
    }
    @GetMapping("/{contentId}")
    public ResponseEntity<BaseApiDto<?>> detail(@PathVariable("contentId") Long id) throws Exception {

        try {
            ContentModel contentModel = contentService.getDetail(id);
            log.info("# api - content detail page = {}", contentModel);
            return super.ok(new BaseApiDto<>(contentModel));
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "조회 실패 : " + e.getMessage());
        }
    }

    @GetMapping("/image/{imgUrl}")
    public ResponseEntity<BaseApiDto<?>> getImage(@PathVariable("imgUrl") String url) throws Exception {
        try {
            InputStream stream = new FileInputStream(url);
            byte[] bytes = IOUtils.toByteArray(stream);
            stream.close();
            return super.ok(new BaseApiDto<>(bytes));
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "조회 실패 : " + e.getMessage());
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseApiDto<?>> register(@RequestBody ContentModel model) {
        try {
            return super.ok(new BaseApiDto<>(contentService.add(model)));
        } catch (Exception e) {
            return super.fail(BaseApiDto.newBaseApiDto(), "9999", "저장 실패 : " + e.getMessage());
        }
    }
}
