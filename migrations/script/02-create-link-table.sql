--liquibase formatted sql

--changeset sleepwalker:02-create-link-sequence
CREATE SEQUENCE IF NOT EXISTS link_id_seq;

--changeset  sleepwalker:02-create-link-table
CREATE TABLE IF NOT EXISTS LINK(
    ID BIGINT DEFAULT nextval('link_id_seq') UNIQUE NOT NULL  PRIMARY KEY,
    URL VARCHAR(2048) NOT NULL,
    TG_ID BIGINT,
    CHECKED_AT timestamp,
    UPDATE_AT timestamp,
    OPEN_ISSUES_COUNT INT NULL,
    FORKS_COUNT INT NULL,
    ANSWER_COUNT INT NULL,
    COMMENT_COUNT INT NULL,
    CONSTRAINT "LINK_CHAT_ID_FK"
    FOREIGN KEY(TG_ID) REFERENCES CHAT ON DELETE CASCADE
);