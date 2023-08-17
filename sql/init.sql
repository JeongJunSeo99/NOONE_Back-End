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
    company_img     varchar(255)    NOT NULL COMMENT 's3 이미지 url' ,
    company_img_name     varchar(255)    NOT NULL COMMENT '이미지 이름',
    deadline	    datetime	    NULL,
    short_yn    	varchar(1)  	NOT NULL	DEFAULT 'N',
    view_yn	        varchar(1)  	NOT NULL	DEFAULT 'Y'	COMMENT '어드민에서 검토 후  노출 결정',
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
    tag_list        VARCHAR(255)    NULL,
    created_at	    datetime	    NOT NULL	DEFAULT current_timestamp(),
    updated_at	    datetime	    NULL,
    primary key (page_id),
    foreign key (content_id) references content(content_id) on update cascade
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='교육 콘텐츠 페이지 별 파일';

-- 컨텐츠 페이지 별 태그 미사용
CREATE TABLE tag (
    tag_id	    bigint	        NOT NULL    auto_increment,
    page_id	    bigint	        NOT NULL,
    tag_name	varchar(22)	    NOT NULL,
    created_at	datetime	    NOT NULL    DEFAULT current_timestamp(),
    updated_at	datetime	    NULL,
    primary key (tag_id),
    foreign key (page_id) references content_page(page_id) on update cascade
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='컨텐츠 페이지 별 태그';

-- 유저 테이블
CREATE TABLE user (
    id  bigint  NOT NULL auto_increment,
    email varchar(255) NOT NULL,
    password     varchar(255) NOT NULL,
    refreshToken varchar(255) NULL,
    created_at	datetime	    NULL    DEFAULT current_timestamp(),
    updated_at	datetime	    NULL,
    point   integer NULL,
    name varchar(255) NOT NULL,
    phone varchar(255) NOT NULL,
    userType boolean NOT NULL    DEFAULT true,
    primary key (id),
    unique (email)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='유저 테이블';

-- 인증 테이블
CREATE TABLE Authority (
    id  bigint not null auto_increment,
    name varchar(255) null,
    email varchar(255) not null,
    primary key (id),
    foreign key (email) references user(email) on update cascade
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='유저 권한 부여 테이블';

-- 컨텐츠 조회 히스토리 테이블
CREATE TABLE History (
    history_id  bigint      NOT NULL auto_increment,
    content_id  bigint      NOT NULL,
    created_at  datetime	NOT NULL	DEFAULT current_timestamp(),
    primary key (history_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='컨텐츠 조회 히스토리 테이블';

-- 문의글 테이블
CREATE TABLE inquiry (
    id  bigint not null auto_increment,
    title varchar(255) null,
    description varchar(255) not null,
    created_at	datetime	    NULL    DEFAULT current_timestamp(),
    updated_at	datetime	    NULL,
    isAnswer   varchar(1)  	NOT NULL	DEFAULT 'N',
    answer  varchar(255)    NULL,
    email varchar(255) not null,
    primary key (id),
    foreign key (email) references user(email) on update cascade
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='문의글 테이블';

-- 토큰 테이블
CREATE TABLE Token (
    id  bigint not null auto_increment,
    refresh_token varchar(255) null,
    expiration integer not null,
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='토큰 테이블';

-- 핸드폰 인증 테이블
CREATE TABLE phone (
    id  bigint not null auto_increment,
    number varchar(255) null,
    cer varchar(255) not null,
    primary key (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='핸드폰 인증 테이블';

-- 포인트 테이블
CREATE TABLE point (
    id  bigint not null auto_increment,
    price Integer not null,
    trade_cd varchar(20) not null COMMENT '적립(EARN), 사용(USE)',
    description varchar(255) not null,
    created_at	datetime	    NULL    DEFAULT current_timestamp(),
    updated_at	datetime	    NULL,
    email varchar(255) not null,
    primary key (id),
    foreign key (email) references user(email) on update cascade
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='포인트 기록 테이블';