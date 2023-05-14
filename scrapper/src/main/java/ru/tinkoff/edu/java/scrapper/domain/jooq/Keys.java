/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq;


import javax.annotation.processing.Generated;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Databasechangeloglock;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.DatabasechangeloglockRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinkRecord;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ChatRecord> CHAT_PKEY = Internal.createUniqueKey(Chat.CHAT, DSL.name("chat_pkey"), new TableField[] { Chat.CHAT.ID }, true);
    public static final UniqueKey<DatabasechangeloglockRecord> DATABASECHANGELOGLOCK_PKEY = Internal.createUniqueKey(Databasechangeloglock.DATABASECHANGELOGLOCK, DSL.name("databasechangeloglock_pkey"), new TableField[] { Databasechangeloglock.DATABASECHANGELOGLOCK.ID }, true);
    public static final UniqueKey<LinkRecord> LINK_PKEY = Internal.createUniqueKey(Link.LINK, DSL.name("link_pkey"), new TableField[] { Link.LINK.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<LinkRecord, ChatRecord> LINK__LINK_CHAT_ID_FK = Internal.createForeignKey(Link.LINK, DSL.name("LINK_CHAT_ID_FK"), new TableField[] { Link.LINK.TG_ID }, Keys.CHAT_PKEY, new TableField[] { Chat.CHAT.ID }, true);
}
