INSERT INTO account (email, user_name, nick_name, password, date_of_birth, role) VALUES ('iksadnorth@gmail.com', 'iksad', 'Iksad', 'q1w2e3r4', NOW(), 'ROLE_USER');
INSERT INTO account (email, user_name, nick_name, password, date_of_birth, role) VALUES ('iksadsouth@gmail.com', 'south', 'South', 'q1w2e3r4', NOW(), 'ROLE_USER');
INSERT INTO account (email, user_name, nick_name, password, date_of_birth, role) VALUES ('dark@gmail.com', 'dark', 'Dark', 'q1w2e3r4', NOW(), 'ROLE_USER');

INSERT INTO image (path) VALUES ('https://cdn.pixabay.com/photo/2017/09/25/13/12/cocker-spaniel-2785074_960_720.jpg');
INSERT INTO image (path) VALUES ('https://cdn.pixabay.com/photo/2016/10/10/14/13/dog-1728494__340.png');
INSERT INTO image (path) VALUES ('https://cdn.pixabay.com/photo/2016/12/13/05/15/puppy-1903313__340.jpg');
INSERT INTO image (path) VALUES ('https://cdn.pixabay.com/photo/2015/11/17/13/13/bulldog-1047518__340.jpg');
INSERT INTO image (path) VALUES ('https://cdn.pixabay.com/photo/2016/10/31/14/55/rottweiler-1785760__340.jpg');
INSERT INTO image (path) VALUES ('https://cdn.pixabay.com/photo/2016/05/26/13/51/dog-1417208__340.png');
INSERT INTO image (path) VALUES ('https://cdn.pixabay.com/photo/2016/07/15/15/55/dachshund-1519374__340.jpg');
INSERT INTO image (path) VALUES ('https://cdn.pixabay.com/photo/2019/04/10/23/51/animal-4118585__340.jpg');
INSERT INTO image (path) VALUES ('https://cdn.pixabay.com/photo/2022/10/08/18/13/dog-7507541__340.jpg');

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

INSERT INTO hashtag (name, article_id) VALUES ('tag1', 1);
INSERT INTO hashtag (name, article_id) VALUES ('tag1', 2);

INSERT INTO hashtag (name, article_id) VALUES ('tag2', 2);
INSERT INTO hashtag (name, article_id) VALUES ('tag2', 3);

INSERT INTO hashtag (name, article_id) VALUES ('tag3', 3);
INSERT INTO hashtag (name, article_id) VALUES ('tag3', 1);
