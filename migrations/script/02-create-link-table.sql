--liquibase formatted sql

--changeset sleepwalker:02-create-link-sequence
CREATE SEQUENCE IF NOT EXISTS link_id_seq;

--changeset  sleepwalker:02-create-link-table
CREATE TABLE IF NOT EXISTS LINK(
    ID BIGINT UNIQUE NOT NULL PRIMARY KEY DEFAULT nextval('link_id_seq'),
    URL VARCHAR(2048) NOT NULL,
    TG_ID BIGINT UNIQUE,
    CHECKED_AT timestamp,
    UPDATE_AT timestamp,
    CONSTRAINT "LINK_CHAT_ID_FK"
    FOREIGN KEY(TG_ID) REFERENCES CHAT ON DELETE CASCADE
);