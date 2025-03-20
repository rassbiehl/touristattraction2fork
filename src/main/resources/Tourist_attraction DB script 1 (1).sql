CREATE DATABASE IF NOT EXISTS Tourist_Attractions;


DROP TABLE if exists tags_for_attractions;
DROP TABLE if exists attractions;
DROP TABLE if exists tags;
DROP TABLE if exists cities;


CREATE TABLE cities(
attraction_city varchar(200),
city varchar(200),
PRIMARY KEY (attraction_city)
);

CREATE TABLE tags(
tag_id int NOT NULL AUTO_INCREMENT,
tag_name varchar(50),
PRIMARY KEY(tag_id)
);

CREATE TABLE attractions (

attraction_id int NOT NULL AUTO_INCREMENT,

attraction_name varchar(200) NOT NULL,

attraction_description varchar(300) NOT NULL,

attraction_city varchar(100) NOT NULL,

PRIMARY KEY (attraction_id),

FOREIGN KEY (attraction_city) references cities(attraction_city)
);

CREATE TABLE tags_for_attractions (
attraction_id int NOT NULL,
tag_id int NOT NULL,
PRIMARY KEY (attraction_id, tag_id),
FOREIGN KEY (attraction_id) REFERENCES attractions(attraction_id) ON DELETE CASCADE,
FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);








 