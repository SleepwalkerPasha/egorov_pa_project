--liquibase formatted sql

--changeset  sleepwalker:create_link_table
CREATE TABLE IF NOT EXISTS LINK(
    URL VARCHAR(2048) NOT NULL,
    TG_ID BIGINT UNIQUE,
    CONSTRAINT "LINK_CHAT_ID_FK"
    FOREIGN KEY(TG_ID) REFERENCES CHAT
);