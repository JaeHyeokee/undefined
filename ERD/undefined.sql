
CREATE TABLE authority
(
    authority_id   INT          NOT NULL COMMENT '권한에 대한 id (권한 3개 만들예정)',
    authority_name VARCHAR(100) NOT NULL COMMENT '권한에 대한 이름',
    PRIMARY KEY (authority_id)
) COMMENT '권한';

ALTER TABLE authority
    ADD CONSTRAINT UQ_authority_name UNIQUE (authority_name);

CREATE TABLE comment
(
    comment_id      INT          NOT NULL AUTO_INCREMENT COMMENT '댓글 고유 id',
    comment_text    LONGTEXT     NOT NULL COMMENT '댓글내용',
    comment_regdate DATETIME     NOT NULL DEFAULT now() COMMENT '댓글 작성 시간',
    post_id         INT         NOT NULL COMMENT '후기 교유 id',
    user_id         INT NOT NULL COMMENT '아이디',
    PRIMARY KEY (comment_id)
) COMMENT '댓글';

CREATE TABLE coupon
(
    coupon_code      VARCHAR(100)      NOT NULL COMMENT '쿠폰 코드',
    coupon_name      VARCHAR(100)      NOT NULL COMMENT '쿠폰 이름',
    coupon_location2 VARCHAR(100)      NOT NULL COMMENT '쿠폰 사용 가능 지역',
    coupon_price     INT               NOT NULL COMMENT '쿠폰 할인 가격',
    coupon_max       INT               NOT NULL COMMENT '쿠폰 사용 최소 구매 가격',
    coupon_use       ENUM('YES', 'NO') NOT NULL COMMENT '쿠폰 사용 여부',
    user_id          INT      NOT NULL COMMENT '아이디',
    booking_id   INT              NOT NULL COMMENT '예약 고유 id값',
    PRIMARY KEY (coupon_code)
) COMMENT '쿠폰';

# ALTER TABLE coupon
#     ADD CONSTRAINT UQ_coupon_code UNIQUE (coupon_code);

CREATE TABLE love
(
    lodging_id INT          NOT NULL COMMENT '숙소고유 id값',
    user_id    INT NOT NULL COMMENT '아이디',
    PRIMARY KEY (lodging_id, user_id)
) COMMENT '숙소 좋아요';

CREATE TABLE lodging
(
    lodging_id           INT                             NOT NULL AUTO_INCREMENT COMMENT '숙소고유 id값',
    lodging_picture_1    LONGTEXT                        NOT NULL COMMENT '숙소 사진 1',
    lodging_name         VARCHAR(100)                    NOT NULL COMMENT '숙소이름',
    lodging_type         ENUM('호텔/리조트' ,'펜션/풀빌라' , '모텔') NOT NULL COMMENT '숙소타입(호텔/리조트 , 펜션/ 풀빌라 , 모텔)',
    lodging_location1    VARCHAR(100)                    NOT NULL COMMENT '숙소위치(지역) - 강원도',
    lodging_location2    VARCHAR(100)                    NOT NULL COMMENT '숙소위치(지역) - 가평',
    lodging_address      VARCHAR(100)                    NOT NULL COMMENT '숙소 주소',
    lodging_number       VARCHAR(100)                    NOT NULL COMMENT '속소 전화번호',
    lodging_url          LONGTEXT                        NULL     COMMENT '숙소 url',
    lodging_open         VARCHAR(100)                    NOT NULL COMMENT '숙소 입실시간',
    lodging_close        VARCHAR(100)                    NOT NULL COMMENT '숙소 퇴실시간',
    lodging_introduce    LONGTEXT                        NULL     COMMENT '숙소 소개',
    lodging_service      LONGTEXT                        NULL     COMMENT '숙소 시설 및 서비스',
    lodging_usingInfo    LONGTEXT                        NULL     COMMENT '숙소 이용 안내',
    lodging_notice       LONGTEXT                        NULL     COMMENT '숙소 예약 공지',
    lodging_owner_name   VARCHAR(100)                    NOT NULL COMMENT '숙소 담당자 이름',
    lodging_owner_number VARCHAR(100)                    NOT NULL COMMENT '숙소 담당자 전화번호',
    lodging_owner_email  VARCHAR(100)                    NOT NULL COMMENT '숙소 담당자 이메일',
    lodging_owner_id     VARCHAR(100)                    NOT NULL COMMENT '숙소 담당자 사업자 등록 번호',
    user_id              INT                   NOT NULL COMMENT '아이디',
    PRIMARY KEY (lodging_id)
) COMMENT '숙소정보';

ALTER TABLE lodging
    ADD CONSTRAINT UQ_lodging_owner_id UNIQUE (lodging_owner_id);

CREATE TABLE post
(
    post_id        INT          NOT NULL AUTO_INCREMENT COMMENT '후기 교유 id',
    post_picture   LONGTEXT     NULL     COMMENT '후기 사진',
    post_text      LONGTEXT     NOT NULL COMMENT '후기 내용',
    post_grade     DOUBLE          NOT NULL COMMENT '평점 (1.0~5.0)',
    user_id        INT NOT NULL COMMENT '아이디',
    booking_id     INT         NOT NULL COMMENT '예약 고유 id값',
    lodging_id     INT         NOT NULL COMMENT '숙소고유 id값',
    room_id        INT         NOT NULL COMMENT '객실고유 id값',
    PRIMARY KEY (post_id)
) COMMENT '후기';

CREATE TABLE booking
(
    booking_id        INT                     NOT NULL AUTO_INCREMENT COMMENT '예약 고유 id값',
    booking_time      DATETIME                NOT NULL DEFAULT now() COMMENT '예약한 시간',
    booking_adult     INT                     NOT NULL DEFAULT 0 COMMENT '성인 예약 인원',
    booking_child     INT                     NOT NULL DEFAULT 0 COMMENT '아동 예약 인원',
    visitor_name      VARCHAR(100)            NOT NULL COMMENT '이용자 이름',
    visitor_phonenum  VARCHAR(100)            NOT NULL COMMENT '이용자 전화번호',
    booking_pay       INT                     NOT NULL COMMENT '결제 금액',
    booking_startdate DATE                    NOT NULL COMMENT '체크인 날짜',
    booking_enddate   DATE                    NOT NULL COMMENT '체크아웃 날짜',
    room_id           INT                     NOT NULL COMMENT '객실고유 id 값',
    user_id           INT                     NOT NULL COMMENT '아이디',
    PRIMARY KEY (booking_id)
) COMMENT '예약';

CREATE TABLE room
(
    room_id           INT               NOT NULL AUTO_INCREMENT COMMENT '객실 예약 id값',
    room_picture_1    LONGTEXT          NOT NULL COMMENT '객실 사진1',
    room_name         VARCHAR(100)      NOT NULL COMMENT '객실 이름',
    room_normal_people INT              NOT NULL COMMENT '객실 기준 인원수',
    room_max_people   INT               NOT NULL COMMENT '객실 최대 인원수',
    room_price        INT               NOT NULL COMMENT '객실 가격',
    room_number       VARCHAR(100)      NULL     COMMENT '객실 호수',
    room_area         VARCHAR(100)      NULL     COMMENT '객실 면적',
    room_bed          INT               NOT NULL COMMENT '객실 침대 개수',
    room_bed_grade    VARCHAR(100)      NULL     COMMENT '객실 침대 유형',
    room_bathroom     INT               NULL     DEFAULT 1 COMMENT '객실 욕실 개수',
    room_smoke        ENUM('YES', 'NO') NOT NULL DEFAULT 'NO' COMMENT '객실 흡연가능여부',
    lodging_id        INT               NOT NULL COMMENT '숙소고유 id값',
    PRIMARY KEY (room_id)
) COMMENT '객실정보';

CREATE TABLE user
(
    user_id       INT NOT NULL AUTO_INCREMENT COMMENT '아이디',
    user_password VARCHAR(100) NOT NULL COMMENT '비밀번호',
    user_name     VARCHAR(100) NOT NULL COMMENT '이름',
    user_email    VARCHAR(100) NULL COMMENT '이메일',
    user_regdate  DATETIME     NOT NULL DEFAULT now() COMMENT '등록날짜',
    user_nickname VARCHAR(100) NOT NULL COMMENT '닉네임',
    user_phonenum VARCHAR(100) NULL COMMENT '전화번호',
    PRIMARY KEY (user_id)
) COMMENT '유져';

# CREATE TABLE cancel
# (
#     reason      VARCHAR(100) NOT NULL COMMENT '취소사유',
#     user_id     INT NOT NULL COMMENT '아이디(UQ)',
#     lodging_id  INT         NOT NULL COMMENT  '숙소 고유 id값',
#     PRIMARY KEY (reason)
# ) COMMENT '취소';

ALTER TABLE user
    ADD CONSTRAINT UQ_user_id UNIQUE (user_id);

ALTER TABLE user
    ADD CONSTRAINT UQ_user_nickname UNIQUE (user_nickname);

ALTER TABLE user
    ADD CONSTRAINT UQ_user_phonenum UNIQUE (user_phonenum);

ALTER TABLE user
    ADD CONSTRAINT UQ_user_email UNIQUE (user_email);

CREATE TABLE user_authority
(
    user_id      INT NOT NULL COMMENT '아이디',
    authority_id int          NOT NULL COMMENT '권한에 대한 id (권한 3개 만들예정)',
    PRIMARY KEY (user_id, authority_id)
) COMMENT '유저가 가지고 있는 권한';

ALTER TABLE user_authority
    ADD CONSTRAINT FK_authority_TO_user_authority
        FOREIGN KEY (authority_id)
            REFERENCES authority (authority_id);

ALTER TABLE love
    ADD CONSTRAINT FK_lodging_TO_love
        FOREIGN KEY (lodging_id)
            REFERENCES lodging (lodging_id);

ALTER TABLE room
    ADD CONSTRAINT FK_lodging_TO_room
        FOREIGN KEY (lodging_id)
            REFERENCES lodging (lodging_id);

ALTER TABLE booking
    ADD CONSTRAINT FK_room_TO_booking
        FOREIGN KEY (room_id)
            REFERENCES room (room_id);

ALTER TABLE comment
    ADD CONSTRAINT FK_post_TO_comment
        FOREIGN KEY (post_id)
            REFERENCES post (post_id);

ALTER TABLE post
    ADD CONSTRAINT FK_lodging_TO_post
        FOREIGN KEY (lodging_id)
            REFERENCES lodging (lodging_id);

ALTER TABLE post
    ADD CONSTRAINT FK_room_TO_post
        FOREIGN KEY (room_id)
            REFERENCES room (room_id);


# ALTER TABLE coupon
#     ADD CONSTRAINT FK_booking_TO_coupon
#         FOREIGN KEY (booking_id)
#             REFERENCES booking (booking_id);

ALTER TABLE comment
    ADD CONSTRAINT FK_user_TO_comment
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE booking
    ADD CONSTRAINT FK_user_TO_booking
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE user_authority
    ADD CONSTRAINT FK_user_TO_user_authority
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE love
    ADD CONSTRAINT FK_user_TO_love
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE post
    ADD CONSTRAINT FK_user_TO_post
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

ALTER TABLE post
    ADD CONSTRAINT FK_booking_TO_post
        FOREIGN KEY (booking_id)
            REFERENCES booking (booking_id);

ALTER TABLE lodging
    ADD CONSTRAINT FK_user_TO_lodging
        FOREIGN KEY (user_id)
            REFERENCES user (user_id);

# ALTER TABLE coupon
#     ADD CONSTRAINT FK_user_TO_coupon
#         FOREIGN KEY (user_id)
#             REFERENCES user (user_id);

# ALTER TABLE cancel
#     ADD CONSTRAINT FK_lodging_TO_cancel
#         FOREIGN KEY (lodging_id)
#             REFERENCES lodging (lodging_id);
#
# ALTER TABLE cancel
#     ADD CONSTRAINT FK_user_TO_cancel
#         FOREIGN KEY (user_id)
#             REFERENCES user (user_id);

INSERT INTO authority
VALUES (1, 'ROLE_USER'), (2, 'ROLE_PROVIDER'), (3, 'ROLE_MASTER');

ALTER TABLE user
    ADD COLUMN provider VARCHAR(40);

ALTER TABLE user
    ADD COLUMN providerId VARCHAR(200);