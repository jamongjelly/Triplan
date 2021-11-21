INSERT INTO role (name) VALUES ('ADMIN');
INSERT INTO role (name) VALUES ('SELLER');
INSERT INTO role (name) VALUES ('USER');

/*Grade 그룹*/

INSERT INTO grade (grade, min_payment, max_payment)
VALUES ('BRONZE', 0,100000);

INSERT INTO grade (grade, min_payment, max_payment)
VALUES ('SILVER', 100001,300000);

INSERT INTO grade (grade, min_payment, max_payment)
VALUES ('GOLD', 300001, 500000);

INSERT INTO grade (grade, min_payment, max_payment)
VALUES ('PLATINUM', 500001, 2100000000);


INSERT INTO member (email, password, nickname, point)
VALUES ('user@gmail.com', '1111', '사용자1', 9999);

INSERT INTO seller (business_license_number, business_name, tel, owner_name, email, business_account, zip_code, address, member_id)
VALUES ('1', 'Com. Trip', '010-1111-1111', '손명진', 'smj@gmail.com', '00-000-0000', '000-00', '부산광역시 부산진구', 1);


INSERT INTO item_group (name, summary_explain, detail_explain, item_category, address, seller_id)
VALUES ('부산 해운대 하얏트 호텔', '5성급 호텔', '상세 설명', 'ROOM', '부산광역시 해운대구', 1);

INSERT INTO item (name, `explain`, item_category, item_group_id)
VALUES ('스위트룸 오션뷰 2인실', '국내 최고의 오션뷰', 'ROOM', 1);
INSERT INTO item (name, `explain`, item_category, item_group_id)
VALUES ('4인용 오션뷰 객실', '가격은 2배', 'ROOM', 1);
INSERT INTO item (name, `explain`, item_category, item_group_id)
VALUES ('대회의실', '가격은 4배', 'ROOM', 1);

INSERT INTO wishlist (member_id, item_group_id)
VALUES (1, 1);

INSERT INTO wishlist (member_id, item_group_id)
VALUES (1, 2);

INSERT INTO wishlist (member_id, item_group_id)
VALUES (1, 3);



INSERT INTO room (number_of_person, max_person, item_id)
VALUES (3, 5, 1);
INSERT INTO room (number_of_person, max_person, item_id)
VALUES (4, 4, 2);
INSERT INTO room (number_of_person, max_person, item_id)
VALUES (10, 20, 3);


INSERT INTO reservation (item_category, total_price, start_date, end_date, name, phone, member_id, seller_id)
VALUES ('ROOM', 50000, '2021-11-1T01:01:01', '2021-12-31T23:59:59', '양경호', '010-9999-9999', 1, 1);

INSERT INTO item_schedule (price, stock, start_date, end_date, item_id)
VALUES (50000, 1, '2021-11-1T01:01:01', '2021-12-31T23:59:59', 1);

INSERT INTO reservation_item (res_id, item_schedule_id)
VALUES (1, 1);

/* Tag 그룹 */
INSERT INTO Tag (color,name,icon)
VALUES('#ffdbdb','시즌이벤트','1번');
INSERT INTO Tag (color,name,icon)
VALUES('#ffeddb','호텔','2번');
INSERT INTO Tag (color,name,icon)
VALUES('#ffffdb','감성충만','3번');
INSERT INTO Tag (color,name,icon)
VALUES('#edffdb','4번','4번');
INSERT INTO Tag (color,name,icon)
VALUES('#dbffdb','5번','5번');
INSERT INTO Tag (color,name,icon)
VALUES('#dbffed','선착순이벤트','6번');
INSERT INTO Tag (color,name,icon)
VALUES('#dbffff','국내선','7번');
INSERT INTO Tag (color,name,icon)
VALUES('#dbedff','마감임박','8번');
INSERT INTO Tag (color,name,icon)
VALUES('#dbdbff','9번','9번');
INSERT INTO Tag (color,name,icon)
VALUES('#eddbff','10번','10번');
INSERT INTO Tag (color,name,icon)
VALUES('#ffdbff','11번','11번');
INSERT INTO Tag (color,name,icon)
VALUES('#ffdbed','12번','12번');
/* Tag_Group */
INSERT INTO Item_Group_Tag (item_group_id,tag_id)
VALUES(1,1);
INSERT INTO Item_Group_Tag (item_group_id,tag_id)
VALUES(1,2);
INSERT INTO Item_Group_Tag (item_group_id,tag_id)
VALUES(1,3);



INSERT INTO coupon(name, num, price, condition, expired_at)
VALUES ('쿠폰1', 1234, 1000, '숙박대전 이벤트', '2021-12-10T12:00:00');

INSERT INTO member_coupon (member_id, coupon_id)
VALUES (1, 1);

insert into notice (title, created_at, updated_at, content, target)
values ('제목', '2021-11-09T17:00:00', '2021-11-09T17:00:00', '내용', 'SELLER');

insert into notice (title, created_at, updated_at, content, target)
values ('제목', '2021-11-15T17:00:00', '2021-11-15T17:00:00', '내용', 'MEMBER');

/* Flight 아이템 그룹 (식별자: 2번) */
INSERT INTO item_group (name, summary_explain, detail_explain, item_category, address, seller_id)
VALUES ('에어 부산 김해 -> 김포', '안내사항 요약', '상세 안내사항', 'FLIGHT', '부산광역시 해운대구', 1);

INSERT INTO item (name, `explain`, item_category, item_group_id)
VALUES ('소인', '0세 ~ 12세', 'FLIGHT', 2);

INSERT INTO flight (departure, destination, flight_type, seat_class, item_id)
VALUES ('부산', '서울', 'ONE_WAY', 'ECONOMY', 4);

INSERT INTO Item_Group_Tag (item_group_id,tag_id)
VALUES(2,6);
INSERT INTO Item_Group_Tag (item_group_id,tag_id)
VALUES(2,7);
INSERT INTO Item_Group_Tag (item_group_id,tag_id)
VALUES(2,8);

/*payment*/
// 카드결제
INSERT INTO payment
(TYPE, TOTAL_PAYMENT , STATE , CREATED_AT , RES_ID )
VALUES ('CREDIT_CARD', 50000, 'PAID', '2021-11-17T12:13:33', 1);

// 무통장입금(미결제)
INSERT INTO payment
(type, account_bank, account_number, total_payment, payment_deadline, state, created_at, res_id)
VALUES ('TRANSFER_WITHOUT_ACCOUNT', '케이뱅크', '70112004110417', 100000, '2021-12-17T14:59:59', 'UNPAID', '2021-11-17T12:25:33', 2);


// 무통장입금(결제)
INSERT INTO payment
(type, account_bank, account_number, total_payment, payment_deadline, state, created_at, updated_at, res_id)
VALUES ('TRANSFER_WITHOUT_ACCOUNT', '신한(조흥)은행', '56211103867610', 130000, '2021-11-30T14:59:59', 'PAID', '2021-11-17T12:25:33', '2021-11-20T08:17:13', 3);

// 폰결제
INSERT INTO payment
(TYPE, TOTAL_PAYMENT , STATE , CREATED_AT , RES_ID )
VALUES ('CELL_PHONE', 35000, 'PAID', '2021-11-18T09:33:40', 4);

// 네이버페이
INSERT INTO payment
(TYPE, TOTAL_PAYMENT , STATE , CREATED_AT , RES_ID )
VALUES ('NAVER_PAY', 55000, 'PAID', '2021-11-20T18:48:23', 5);

// 카카오페이
INSERT INTO payment
(TYPE, TOTAL_PAYMENT , STATE , CREATED_AT , RES_ID )
VALUES ('KAKAO_PAY', 240000, 'PAID', '2021-11-20T22:10:30', 6);
INSERT INTO question (title, content, hide, member_id, type)
VALUES ('테스트 문의1', '테스트 문의 내용', 0, 1, 'CUSTOMER');
INSERT INTO question (title, content, hide, member_id, type)
VALUES ('테스트 문의2', '테스트 문의 내용', 0, 1, 'CUSTOMER');
INSERT INTO question (title, content, hide, member_id, type)
VALUES ('테스트 문의3', '테스트 문의 내용', 0, 1, 'CUSTOMER');
INSERT INTO question (title, content, hide, member_id, type)
VALUES ('테스트 문의4', '테스트 문의 내용', 0, 1, 'CUSTOMER');


