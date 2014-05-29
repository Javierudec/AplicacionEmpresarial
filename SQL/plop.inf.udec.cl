CREATE TABLE movie (
title varchar(255),
synopsis text,
debut_date date,
PRIMARY KEY (title)
);

CREATE TABLE site_user (
name varchar(25),
password varchar(25),
mail varchar(255),
rights int,
PRIMARY KEY (name) 
);

CREATE SEQUENCE rank_id_seq;

CREATE TABLE article(
id int NOT NULL default nextval('rank_id_seq'),
title varchar(255),
content text,
username varchar(255) REFERENCES site_user(name),
PRIMARY KEY (id) 
);

CREATE SEQUENCE tag_id_seq;

CREATE TABLE tag(
id int NOT NULL default nextval('tag_id_seq'),
name varchar(255),
PRIMARY KEY (id) 
);

CREATE SEQUENCE actor_id_seq;

CREATE TABLE actor(
id int NOT NULL default nextval('actor_id_seq'),
name varchar(255),
PRIMARY KEY (id) 
);

CREATE SEQUENCE genre_id_seq;

CREATE TABLE genre(
id int NOT NULL default nextval('genre_id_seq'),
name varchar(255),
PRIMARY KEY (id) 
);

CREATE TABLE rank_article(
username varchar(255) REFERENCES site_user(name),
rankedArticle int REFERENCES article(id),
rank int
); 

CREATE TABLE rank_article(
username varchar(255) REFERENCES site_user(name),
rankedArticle int REFERENCES article(id),
rank int
); 

CREATE TABLE rank_movie(
username varchar(255) REFERENCES site_user(name),
rankedMovie varchar(255) REFERENCES movie(title),
rank int
);
