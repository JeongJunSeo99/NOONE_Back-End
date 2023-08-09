package com.techconnection.noone.biz.content.service;

import com.amazonaws.services.kms.model.TagResourceRequest;
import com.techconnection.noone.biz.content.dto.*;
import com.techconnection.noone.biz.content.repository.ContentEntityInterface;
import com.techconnection.noone.biz.content.repository.ContentPageRepository;
import com.techconnection.noone.biz.content.repository.ContentRepository;
import com.techconnection.noone.biz.content.repository.TagRepository;
import com.techconnection.noone.common.dto.PageEntity;
import com.techconnection.noone.common.service.BaseServiceImplWithJpa;
import com.techconnection.noone.fileupload.storage.FileSystemStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class ContentService extends BaseServiceImplWithJpa<ContentModel, ContentEntity, Long, ContentRepository> {

    private final ContentPageRepository contentPageRepository;
    private final TagRepository tagRepository;

    private final FileSystemStorageService storageService;

    public ContentService(ContentRepository contentRepository, ContentPageRepository contentPageRepository, TagRepository tagRepository, FileSystemStorageService fileSystemStorageService) {
        this.repository = contentRepository;
        this.contentPageRepository = contentPageRepository;
        this.tagRepository = tagRepository;
        this.storageService = fileSystemStorageService;
    }

    @Override
    public PageEntity<ContentModel> getList(PageEntity<ContentModel> pageEntity) throws Exception {
        Page<ContentEntity> page = repository.findAll(toPageable(pageEntity));

        Stream<ContentModel> stream = page.getContent().stream()
                .map(entity -> entity.toModel());

        pageEntity.setTotalCnt(page.getTotalElements());
        pageEntity.setDtoList(stream.toList());
        return pageEntity;
    }

    public List<ContentModel> getListByViewYn(int pagePerCnt) throws Exception {
        return repository.findByViewYn("Y").stream().map(ContentModel::new).toList();
    }

    @Override
    @Transactional
    public ContentModel add(ContentModel model) throws Exception {
        ContentEntity contentEntity = model.toEntity();
        List<ContentPageEntity> contentPageEntityList = new ArrayList<>();
        //페이지 저장 루프
        for (ContentPageEntity contentPageEntity : contentEntity.getContentPageEntityList()) {
            //페이지 별 파일 저장
            contentPageEntity.setUrl(storageService.store(contentPageEntity.getUploadFile(), model.getUserId()));
            contentPageEntity.setOriginalName(contentPageEntity.getUploadFile().getOriginalFilename());
            contentPageEntity.setSize(contentPageEntity.getUploadFile().getSize());
            //저장된 파일 경로 -> contentPageEntity.setUrl();
            
            List<TagEntity> tagEntityList = new ArrayList<>();
            // 태그 저장 루프
            for (TagEntity tagEntity : contentPageEntity.getTagEntityList()) {
//                contentPageEntity.getTagEntityList().add(tagEntity);
                tagEntityList.add(tagEntity);
                tagEntity.setContentPageEntity(contentPageEntity);
            }
            contentPageEntity.getTagEntityList().addAll(tagEntityList);

//            contentEntity.getContentPageEntityList().add(contentPageEntity);
            contentPageEntityList.add(contentPageEntity);
            contentPageEntity.setContentEntity(contentEntity);
        }
        contentEntity.getContentPageEntityList().addAll(contentPageEntityList);

        repository.save(contentEntity);

        //        ContentModel m = model.onlyContent(model);
//        // 1. 컨텐츠 생성
//        Long id = super.add(m).getContentId();
//        List<ContentPageModel> contentPageModelList = model.getContentPageList();
//        // 2. 컨텐츠 - 컨텐츠 페이지 매핑
//        for (ContentPageModel contentPageModel : contentPageModelList) {
//            contentPageModel.setContentId(id);
//            Long pageId = contentPageRepository.save(contentPageModel.toEntity()).getPageId();
//            // 3. 컨텐츠 페이지 - 태그 매핑
//            for (int j = 0; j < contentPageModel.getTagList().size(); j++) {
//                TagModel tagModel = new TagModel();
//                tagModel.setPageId(pageId);
//                tagModel.setTageName(contentPageModel.getTagList().get(j));
//                tagRepository.save(tagModel.toEntity());
//            }
//        }

        return new ContentModel();
    }

}
