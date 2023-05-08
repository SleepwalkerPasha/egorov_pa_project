/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq;


import javax.annotation.processing.Generated;

import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chat;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Databasechangelog;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Databasechangeloglock;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Link;


/**
 * Convenience access to all tables in public.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.chat</code>.
     */
    public static final Chat CHAT = Chat.CHAT;

    /**
     * The table <code>public.databasechangelog</code>.
     */
    public static final Databasechangelog DATABASECHANGELOG = Databasechangelog.DATABASECHANGELOG;

    /**
     * The table <code>public.databasechangeloglock</code>.
     */
    public static final Databasechangeloglock DATABASECHANGELOGLOCK = Databasechangeloglock.DATABASECHANGELOGLOCK;

    /**
     * The table <code>public.link</code>.
     */
    public static final Link LINK = Link.LINK;
}
