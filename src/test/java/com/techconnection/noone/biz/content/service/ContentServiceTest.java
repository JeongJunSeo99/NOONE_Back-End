package com.techconnection.noone.biz.content.service;



import com.techconnection.noone.biz.content.dto.ContentModel;

import com.techconnection.noone.biz.content.dto.ContentPageModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.LoggerFactory.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@SpringBootTest
public class ContentServiceTest {
    @Autowired
    @Mock
    private ContentService contentService;


    @Test
    public void contentSaveTest() throws Exception {
        ContentModel model = new ContentModel();
        model.setUserId(1L);
        model.setTitle("addTest2-filestore");
        model.setDescription("testDesc");
        model.setCategory("testCategory");
        model.setCompanyName("com");
        model.setDeadLine(LocalDateTime.parse("2023-09-06T12:18:02"));
        model.setShortYn("Y");

        ContentPageModel contentPageModel = new ContentPageModel();

        contentPageModel.setUploadFile(new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes()));
        contentPageModel.setDescription("page 1 desc");
        contentPageModel.setPageOrder(1);
        ArrayList<String> list = new ArrayList<>();
        list.add("taglist1");
        list.add("taglist2");
        contentPageModel.setTagList(list);

        model.contentPageList.add(contentPageModel);

        contentService.add(model);
    }
}
