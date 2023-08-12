package com.techconnection.noone.biz.content.service;

import com.amazonaws.services.kms.model.TagResourceRequest;
import com.techconnection.noone.aws.s3.AwsS3Dto;
import com.techconnection.noone.aws.s3.AwsS3Service;
import com.techconnection.noone.biz.content.dto.*;
import com.techconnection.noone.biz.content.repository.*;
import com.techconnection.noone.common.code.ResultCode;
import com.techconnection.noone.common.dto.PageEntity;
import com.techconnection.noone.common.exception.BizException;
import com.techconnection.noone.common.service.BaseServiceImplWithJpa;
import com.techconnection.noone.fileupload.storage.FileSystemStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
public class ContentService extends BaseServiceImplWithJpa<ContentModel, ContentEntity, Long, ContentRepository> {

    private final ContentPageRepository contentPageRepository;

    private final HistoryRepository historyRepository;

    private final AwsS3Service awsS3Service;


    public ContentService(ContentRepository contentRepository, ContentPageRepository contentPageRepository, HistoryRepository historyRepository, AwsS3Service awsS3Service) {
        this.repository = contentRepository;
        this.contentPageRepository = contentPageRepository;
        this.historyRepository = historyRepository;

        this.awsS3Service = awsS3Service;
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
        Sort sort = Sort.by(Sort.Order.desc("shortYn"));


        return repository.findByViewYn("Y", sort).stream().map(ContentModel::new).toList();
    }

    public List<ContentModel> getListByViewCount(int pagePerCnt) throws Exception {
        return repository.findAllByOrderByViewCountDesc().stream().map(ContentModel::new).toList();
//        return historyRepository.countContentIdOccurrence1s().stream().map(HistoryModel::new).toList();
    }

    public List<ContentModel> search(String keyword) throws Exception {
        return repository.findAllByTitleContainingOrDescriptionContaining(keyword, keyword).stream().map(ContentModel::new).toList();
    }

    public List<ContentModel> getListByUserId(Long userId) throws Exception {
        return repository.findByUserId(userId).stream().map(ContentModel::new).toList();
    }

    @Override
    @Transactional
    public ContentModel getDetail(Long pk) throws Exception {
        // 조회수 증가 (쿠키 또는 세션으로 체크?
        repository.updateViewCount(pk);
        // 히스토리 저장
        historyRepository.save(new HistoryEntity(pk));
        return super.getDetail(pk);
    }

    @Transactional
    public ContentModel add(ContentModel model, String dirName) throws Exception {
        ContentEntity contentEntity = model.toEntity();
        List<ContentPageEntity> contentPageEntityList = new ArrayList<>();

        AwsS3Dto awsS3Dto = awsS3Service.upload(model.getUploadFile(), dirName);
        contentEntity.setCompanyImg(awsS3Dto.getPath());
        contentEntity.setCompanyImgName(awsS3Dto.getKey());
//        contentEntity.setCompanyImg(storageService.store(model.getUploadFile(), model.getUserId()));

        //페이지 저장 루프
        for (ContentPageEntity contentPageEntity : contentEntity.getContentPageEntityList()) {
            log.info("In add ContentPage Loop : contentPageEntity = {}", contentPageEntity);
            //페이지 별 파일 저장
//            contentPageEntity.setUrl(storageService.store(contentPageEntity.getUploadFile(), model.getUserId()));
            AwsS3Dto awsS3Dto1 = awsS3Service.upload(contentPageEntity.getUploadFile(), dirName);
            contentPageEntity.setUrl(awsS3Dto1.getPath());
            contentPageEntity.setOriginalName(awsS3Dto1.getKey());
            contentPageEntity.setSize(contentPageEntity.getUploadFile().getSize());
            //저장된 파일 경로 -> contentPageEntity.setUrl();

//            List<TagEntity> tagEntityList = new ArrayList<>();
            // 태그 저장 루프 -> 변경: 태그를 페이지에 스트링으로 저장
//            for (TagEntity tagEntity : contentPageEntity.getTagEntityList()) {
////                contentPageEntity.getTagEntityList().add(tagEntity);
//                tagEntityList.add(tagEntity);
//                tagEntity.setContentPageEntity(contentPageEntity);
//            }
//            contentPageEntity.getTagEntityList().addAll(tagEntityList);

//            contentEntity.getContentPageEntityList().add(contentPageEntity);
            contentPageEntityList.add(contentPageEntity);
            contentPageEntity.setContentEntity(contentEntity);
        }
        contentEntity.getContentPageEntityList().addAll(contentPageEntityList);

        repository.save(contentEntity);
        return new ContentModel();
    }

    @Override
    @Transactional
    public void remove(Long contentId) throws Exception {
        ContentEntity content = repository.findById(contentId)
                .orElseThrow(() -> new BizException(ResultCode.DATA_NOT_FOUND));

        //페이지 이미지 삭제 루프
        for (ContentPageEntity contentPageEntity : content.getContentPageEntityList()) {
            AwsS3Dto awsS3Dto1 = new AwsS3Dto(contentPageEntity.getOriginalName(), "");
            awsS3Service.remove(awsS3Dto1);
        }
        //컨텐츠 회사 이미지 제거
        AwsS3Dto awsS3Dto = new AwsS3Dto(content.getCompanyImgName(), "");
        awsS3Service.remove(awsS3Dto);
        repository.deleteById(contentId);
    }

}
