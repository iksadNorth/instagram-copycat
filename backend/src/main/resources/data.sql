INSERT INTO account (email, user_name, nick_name, password, date_of_birth, role) VALUES ('iksadnorth@gmail.com', 'iksad', 'Iksad', 'q1w2e3r4', NOW(), 'USER');
INSERT INTO account (email, user_name, nick_name, password, date_of_birth, role) VALUES ('iksadsouth@gmail.com', 'south', 'South', 'q1w2e3r4', NOW(), 'USER');
INSERT INTO account (email, user_name, nick_name, password, date_of_birth, role) VALUES ('dark@gmail.com', 'dark', 'Dark', 'q1w2e3r4', NOW(), 'USER');

INSERT INTO image (path) VALUES ('00000.jpg');
INSERT INTO image (path) VALUES ('00001.jpg');
INSERT INTO image (path) VALUES ('00002.jpg');
INSERT INTO image (path) VALUES ('00003.jpg');
INSERT INTO image (path) VALUES ('00004.jpg');
INSERT INTO image (path) VALUES ('00005.jpg');
INSERT INTO image (path) VALUES ('00006.jpg');
INSERT INTO image (path) VALUES ('00007.jpg');
INSERT INTO image (path) VALUES ('00008.jpg');

INSERT INTO article (account_id, image_id, content) VALUES (1, 1, 'post content1');
INSERT INTO article (account_id, image_id, content) VALUES (1, 2, 'post content2');
INSERT INTO article (account_id, image_id, content) VALUES (1, 3, 'post content3');

INSERT INTO article (account_id, image_id, content) VALUES (2, 4, 'post content1 - following');
INSERT INTO article (account_id, image_id, content) VALUES (2, 5, 'post content2 - following');
INSERT INTO article (account_id, image_id, content) VALUES (2, 6, 'post content3 - following');

INSERT INTO article (account_id, image_id, content) VALUES (3, 7, 'post content1 - follower');
INSERT INTO article (account_id, image_id, content) VALUES (3, 8, 'post content2 - follower');
INSERT INTO article (account_id, image_id, content) VALUES (3, 9, 'post content3 - follower');

INSERT INTO follow (follower_id, followee_id) VALUES (1, 2);
INSERT INTO follow (follower_id, followee_id) VALUES (3, 1);

INSERT INTO hashtag (name, article_id) VALUES ('#tag1', 1);
INSERT INTO hashtag (name, article_id) VALUES ('#tag1', 2);

INSERT INTO hashtag (name, article_id) VALUES ('#tag2', 2);
INSERT INTO hashtag (name, article_id) VALUES ('#tag2', 3);

INSERT INTO hashtag (name, article_id) VALUES ('#tag3', 3);
INSERT INTO hashtag (name, article_id) VALUES ('#tag3', 1);
