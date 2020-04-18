# noinspection SqlNoDataSourceInspectionForFile

USE jersey03;

DROP TABLE IF EXISTS custom_fields;

CREATE TABLE custom_fields
(
  id          BIGINT       NOT NULL AUTO_INCREMENT,
  entity_id   BIGINT       NOT NULL,
  entity_type VARCHAR(255) NOT NULL,
  `key`       VARCHAR(255) NOT NULL,
  value       VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS donors;

CREATE TABLE donors
(
  id           BIGINT       NOT NULL AUTO_INCREMENT,
  first_name   VARCHAR(255) NOT NULL,
  last_name    VARCHAR(255) NOT NULL,
  age          INTEGER      NULL,
  date_created DATETIME     not null,
  last_updated DATETIME     not null,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS charities;

CREATE TABLE charities
(
  id           BIGINT       NOT NULL AUTO_INCREMENT,
  name         VARCHAR(255) NOT NULL,
  ein          VARCHAR(255) NOT NULL,
  description  TEXT         NULL,
  website      VARCHAR(255) NULL,
  date_created DATETIME     not null,
  last_updated DATETIME     not null,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS donations;

CREATE TABLE donations
(
  id           BIGINT     NOT NULL AUTO_INCREMENT,
  amount       float      not null,
  charity_id   BIGINT(20) NOT NULL,
  donor_id     BIGINT(20) NOT NULL,
  date_created DATETIME   not null,
  last_updated DATETIME   not null,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS clusters;

CREATE TABLE clusters
(
  id                  BIGINT        NOT NULL AUTO_INCREMENT,
  cluster_name        VARCHAR(4096) NULL,
  attributes          TEXT          NULL,
  cluster_description VARCHAR(255)  NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS cluster_entity_mappings;

CREATE TABLE cluster_entity_mappings
(
  id          BIGINT       NOT NULL AUTO_INCREMENT,
  cluster_id  BIGINT       NOT NULL,
  entity_id   BIGINT       NOT NULL,
  entity_type VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO donors(first_name, last_name, age, date_created, last_updated)
VALUES ('Jack', 'Jones', 34, '2017-10-07T12:00:00', '2017-10-07T12:00:00');

INSERT INTO donors(first_name, last_name, date_created, last_updated)
VALUES ('Luke', 'Skywalker', '2017-10-10T12:00:00', '2017-10-10T12:00:00');

INSERT INTO donors(first_name, last_name, age, date_created, last_updated)
VALUES ('Han', 'Solo', 32, '2017-10-22T12:00:00', '2017-10-22T12:00:00');

INSERT INTO charities(name, ein, description, website, date_created, last_updated)
VALUES ('American Cancer Society', '93-1234567', 'medical research', 'http://www.cancer.org', '2017-10-07T12:00:00',
        '2017-10-07T12:00:00');

INSERT INTO charities(name, ein, description, website, date_created, last_updated)
VALUES ('Red Cross', '53-0196605', 'disaster relief', 'http://www.redcross.org', '2017-10-08T12:00:00',
        '2017-10-08T12:00:00');

INSERT INTO donations(amount, charity_id, donor_id, date_created, last_updated)
VALUES (1000.0, 1, 1, '2017-10-07T12:00:00', '2017-10-07T12:00:00');

INSERT INTO custom_fields(entity_type, entity_id, `key`, value)
VALUES ("DONOR", 1, "rating", "Active");

INSERT INTO custom_fields(entity_type, entity_id, `key`, value)
VALUES ("DONOR", 2, "rating", "Recent");

INSERT INTO custom_fields(entity_type, entity_id, `key`, value)
VALUES ("DONOR", 3, "rating", "Active");

INSERT INTO custom_fields(entity_type, entity_id, `key`, value)
VALUES ("CHARITY", 1, "executiveDirector", "Gary Reedy");

INSERT INTO custom_fields(entity_type, entity_id, `key`, value)
VALUES ("CHARITY", 1, "reviewComments", "Pending");

INSERT INTO custom_fields(entity_type, entity_id, `key`, value)
VALUES ("CHARITY", 1, "rating", "High");

INSERT INTO custom_fields(entity_type, entity_id, `key`, value)
VALUES ("CHARITY", 2, "executiveDirector", "Gail McGovern");

INSERT INTO custom_fields(entity_type, entity_id, `key`, value)
VALUES ("CHARITY", 2, "reviewComments", "Completed");

INSERT INTO custom_fields(entity_type, entity_id, `key`, value)
VALUES ("CHARITY", 2, "rating", "High");
