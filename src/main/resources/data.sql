-- '더미 데이터' 작성

-- '데이터 테이블 article'에
-- 1.('id 속성', 'title 속성', 'content 속성')을 가지며
-- 2.'그 값(value)'으로 ('1', 'aaaa', '1111')을 가진  (('2', 'bbbb', '2222')와 ('3', 'cccc', '3333') 도 추가)
-- '데이터 3개를 생성'시켜줌
-- 이제, 위 데이터 3개들을 '서버를 (재)시작 할 때마다' 위 '더미 데이터'들을 자동으로 넣어주게 되는 것임
-- 이제, 서버를 새롭게 시작하면, 자동으로 아래 데이터들이 'ht//localhost:9090/articles', 즉 '게시판 글 목록'에 '자동으로 생성되어 있게 됨'

INSERT INTO article(id, title, content) VALUES (1, 'aaaa', '1111');
INSERT INTO article(id, title, content) VALUES (2, 'bbbb', '2222');
INSERT INTO article(id, title, content) VALUES (3, 'cccc', '3333');

-- cf)