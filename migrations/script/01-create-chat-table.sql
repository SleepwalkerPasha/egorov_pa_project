--liquibase formatted sql

--changeset  sleepwalker:create_chat_table
CREATE TABLE IF NOT EXISTS CHAT(
    ID BIGINT UNIQUE PRIMARY KEY
);
