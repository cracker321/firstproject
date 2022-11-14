-- '초기 데이터' 설정. 즉, '더미 데이터' 작성

-- '데이터 테이블 article'에
-- 1.('id 속성', 'title 속성', 'content 속성')을 가지며
-- 2.'그 값(value)'으로 ('1', 'aaaa', '1111')을 가진  (('2', 'bbbb', '2222')와 ('3', 'cccc', '3333') 도 추가)
-- '데이터 3개를 생성'시켜줌
-- 이제, 위 데이터 3개들을 '서버를 (재)시작 할 때마다' 위 '더미 데이터'들을 자동으로 넣어주게 되는 것임
-- 이제, 서버를 새롭게 시작하면, 자동으로 아래 데이터들이 'ht//localhost:9090/articles', 즉 '게시판 글 목록'에 '자동으로 생성되어 있게 됨'


--====================================================================================================================

-- [ 게시글(article) 더미 데이터를 넣어줌]
INSERT INTO article(id, title, content) VALUES (1, 'aaaa', '1111');
INSERT INTO article(id, title, content) VALUES (2, 'bbbb', '2222');
INSERT INTO article(id, title, content) VALUES (3, 'cccc', '3333');

INSERT INTO article(id, title, content) VALUES (4, '당신의 인생 영화는?', '댓글 ㄱ');
INSERT INTO article(id, title, content) VALUES (5, '당신의 소울 푸드는?', '댓글 ㄱㄱ');
INSERT INTO article(id, title, content) VALUES (6, '당신의 취미는?', '댓글 ㄱㄱㄱ');
-- cf)

--====================================================================================================================

-- [ 댓글(comment) 더미 데이터 작성 ]

-- < '여러 개의 댓글(comment)'이 '4번 게시글(article)'에 달려 있는 더미 데이터를 넣어줌 >
INSERT INTO comment(id, article_id, nickname, body) VALUES (1, 4, '내 닉네임은 Park', '내 인생영화는 굳 윌 헌팅');
INSERT INTO comment(id, article_id, nickname, body) VALUES (2, 4, '내 닉네임은 Kim', '내 인생영화는 아이 엠 샘' );
INSERT INTO comment(id, article_id, nickname, body) VALUES (3, 4, '내 닉네임은 Choi', '내 인생영화는 쇼생크탈출');


-- < '여러 개의 댓글(comment)'이 '5번 게시글(article)'에 달려 있는 더미 데이터를 넣어줌 >
INSERT INTO comment(id, article_id, nickname, body) VALUES (4, 5, '내 닉네임은 Park', '내 소울푸드는 치킨');
INSERT INTO comment(id, article_id, nickname, body) VALUES (5, 5, '내 닉네임은 Kim', '내 소울푸드는 샤브샤브');
INSERT INTO comment(id, article_id, nickname, body) VALUES (6, 5, '내 닉네임은 Choi', '내 소울푸드는 초밥');


-- < '여러 개의 댓글(commnet)'이 '6번 게시글(article)'에 달려 있는 더미 데이터를 넣어줌 >
INSERT INTO comment(id, article_id, nickname, body) VALUES (7, 6, '내 닉네임은 Park', '내 취미는 조깅');
INSERT INTO comment(id, article_id, nickname, body) VALUES (8, 6, '내 닉네임은 Kim', '내 취미는 유튜브');
INSERT INTO comment(id, article_id, nickname, body) VALUES (9, 6, '내 닉네임은 Choi', '내 취미는 독서');

--====================================================================================================================

-- < 예제 1) '4번 게시글'에 '달린' '모든 댓글들'을 가져와봐라! >
-- 1. 가장 세부내역(SELECT *. 특정 컬럼의 모든 세부내역)
-- 2.                      ---------> 가장 큰 테이블(FROM comment)
-- 3.                                         --------->중간 내역(WHERE article =4. 어떤 특정 컬럼인지를 파악)

-- SELECT
--       *    //'특정 컬럼(e.g: id, content, title, article_id와 같은 것)' '내부'의
--            //'모든 세부내역(e.g: 'comment 테이블'의 경우에는 'id 컬럼'의 모든 세부내역은 1, 2, 3...9. 총 9개의 댓글(댓글번호 1~9)
--            //                                           'nickname 컬럼'의 모든 세부내역은 총 9개의 댓글(
--            //                                                                         '내 닉네임은 Park'으로 작성된 3개의 댓글
--            //                                                                         '내 닉네임은 Kim'으로 작성된 3개의 댓글
--            //                                                                         '내 닉네임은 Choi'으로 작성된 3개의 댓글
--            //                                                                          )' 을 가져와라!
-- FROM
--       comment  //'테이블 comment'에서
-- WHERE
--      article_id = 4  //조건은, '컬럼 article_id'가'4'인 댓글들만을

--====================================================================================================================

-- < 예제 2) 'nickname이 Park으로 달린' '모든 댓글들'을 가져와봐라! >

-- SELECT
--      *
-- FROM
--      comment
-- WHERE
--      nickname = '내 닉네임은 Park';

--====================================================================================================================
