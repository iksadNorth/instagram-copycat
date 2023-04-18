-- Account 엔티티
CREATE TABLE account (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  created_at TIMESTAMP,
  deleted_at TIMESTAMP,

  email VARCHAR(255) NOT NULL UNIQUE,
  user_name VARCHAR(255) NOT NULL,
  nick_name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  date_of_birth TIMESTAMP NOT NULL,
  role VARCHAR(255),
  introduction VARCHAR(255)
);

CREATE INDEX idx_account_email ON account(email);

-- Article 엔티티
CREATE TABLE article (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  created_at TIMESTAMP,
  deleted_at TIMESTAMP,

  account_id BIGINT NOT NULL,
  image_id BIGINT NOT NULL,
  content VARCHAR(255),
  is_hide_likes_and_views CHAR(1) DEFAULT 0 NOT NULL,
  is_allowed_comments CHAR(1) DEFAULT 0 NOT NULL
);

-- Comment 엔티티
CREATE TABLE comments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  created_at TIMESTAMP,
  deleted_at TIMESTAMP,

  account_id BIGINT NOT NULL,
  article_id BIGINT NOT NULL,
  parent_id BIGINT,
  content VARCHAR(255)
);

-- Follow 엔티티
CREATE TABLE follow (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  created_at TIMESTAMP,
  deleted_at TIMESTAMP,

  followee_id BIGINT NOT NULL,
  follower_id BIGINT NOT NULL
);

-- Image 엔티티
CREATE TABLE image (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  created_at TIMESTAMP,
  deleted_at TIMESTAMP,

  path varchar(255) NOT NULL
);

-- Likes 엔티티
CREATE TABLE likes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  created_at TIMESTAMP,
  deleted_at TIMESTAMP,

  account_id BIGINT,
  article_id BIGINT,
  comment_id BIGINT
);

-- Article 외래키
ALTER TABLE article ADD CONSTRAINT article_fk_account_id FOREIGN KEY (account_id) REFERENCES account(id);
ALTER TABLE article ADD CONSTRAINT article_fk_image_id FOREIGN KEY (image_id) REFERENCES image(id);

-- Comment 외래키
ALTER TABLE comments ADD CONSTRAINT comments_fk_account_id FOREIGN KEY (account_id) REFERENCES account(id);
ALTER TABLE comments ADD CONSTRAINT comments_fk_article_id FOREIGN KEY (article_id) REFERENCES article(id);
ALTER TABLE comments ADD CONSTRAINT comments_fk_parent_id FOREIGN KEY (parent_id) REFERENCES comments(id);

-- Follow 외래키
ALTER TABLE follow ADD CONSTRAINT follow_fk_followee_id FOREIGN KEY (followee_id) REFERENCES account(id);
ALTER TABLE follow ADD CONSTRAINT follow_fk_follower_id FOREIGN KEY (follower_id) REFERENCES account(id);

-- Likes 외래키
ALTER TABLE likes ADD CONSTRAINT likes_fk_account_id FOREIGN KEY (account_id) REFERENCES account(id);
ALTER TABLE likes ADD CONSTRAINT likes_fk_article_id FOREIGN KEY (article_id) REFERENCES article(id);
ALTER TABLE likes ADD CONSTRAINT likes_fk_comment_id FOREIGN KEY (comment_id) REFERENCES comments(id);
