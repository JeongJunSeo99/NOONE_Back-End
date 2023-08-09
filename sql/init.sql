-- 테이블 생성 sql 관리

-- 교육 콘텐츠 테이블
CREATE TABLE content (
    content_id	    bigint	        NOT NULL auto_increment,
    user_id	        bigint	        NOT NULL,
    title	        varchar(50) 	NOT NULL,
    description	    varchar(255)	NULL,
    category	    varchar(20)	    NOT NULL,
    view_count	    integer     	NOT NULL	DEFAULT 0,
    company_name	varchar(50) 	NULL,
    deadline	    datetime	    NULL,
    short_yn    	varchar(1)  	NOT NULL	DEFAULT 'N',
    view_yn	        varchar(1)  	NOT NULL	DEFAULT 'N'	COMMENT '어드민에서 검토 후  노출 결정',
    created_at	    datetime	    NOT NULL	DEFAULT current_timestamp(),
    updated_at	    datetime	    NULL,
    primary key (content_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='교육 콘텐츠';

-- 교육 콘텐츠 페이지 별 파일
CREATE TABLE content_page (
    page_id	        bigint	        NOT NULL auto_increment,
    content_id  	bigint	        NOT NULL,
    url	            varchar(255)	NOT NULL,
    size	        integer        	NOT NULL,
    originalName	varchar(255)	NOT NULL,
    description	    varchar(1000)	NULL,
    page_order      smallint        NOT NULL,
    created_at	    datetime	    NOT NULL	DEFAULT current_timestamp(),
    updated_at	    datetime	    NULL,
    primary key (page_id),
    foreign key (content_id) references content(content_id) on update cascade
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='교육 콘텐츠 페이지 별 파일';

-- 컨텐츠 페이지 별 태그
CREATE TABLE tag (
    tag_id	    bigint	        NOT NULL    auto_increment,
    page_id	    bigint	        NOT NULL,
    tag_name	varchar(22)	    NOT NULL,
    created_at	datetime	    NOT NULL    DEFAULT current_timestamp(),
    updated_at	datetime	    NULL,
    primary key (tag_id),
    foreign key (page_id) references content_page(page_id) on update cascade
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='컨텐츠 페이지 별 태그';