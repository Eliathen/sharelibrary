create table AUTHOR
(
    ID      BIGINT auto_increment,
    NAME    TEXT not null,
    SURNAME TEXT not null,
    primary key (ID)
);

create table BOOK
(
    ID    BIGINT auto_increment,
    TITLE VARCHAR(255) not null,
    primary key (ID)
);

create table BOOK_AUTHORS
(
    BOOK_ID   BIGINT not null,
    AUTHOR_ID BIGINT not null,
    constraint FK78GU95LGLHC6CS2U5JFUDX6E5
        foreign key (AUTHOR_ID) references AUTHOR (ID),
    constraint FKS4XM7Q8I3UXVAISWJ1C35NNXW
        foreign key (BOOK_ID) references BOOK (ID)
);

create table CATEGORY
(
    ID   INTEGER auto_increment,
    NAME VARCHAR(255),
    primary key (ID)
);

create table BOOK_CATEGORIES
(
    BOOK_ID     BIGINT  not null,
    CATEGORY_ID INTEGER not null,
    constraint FKEN2TGSLRQSPFIEN26R5HAGXF9
        foreign key (CATEGORY_ID) references CATEGORY (ID),
    constraint FKRQ5MFTM1EJL023EPQBN42LPA3
        foreign key (BOOK_ID) references BOOK (ID)
);

create table COORDINATES
(
    ID        BIGINT auto_increment,
    LATITUDE  DOUBLE not null,
    LONGITUDE DOUBLE not null,
    primary key (ID)
);

create table ADDRESS
(
    ID              BIGINT auto_increment,
    BUILDING_NUMBER TEXT not null,
    CITY            TEXT not null,
    COUNTRY         TEXT not null,
    POSTAL_CODE     TEXT not null,
    STREET          TEXT not null,
    COORDINATES_ID  BIGINT,
    primary key (ID),
    constraint FK6JK1V3EOI3WQWVCPSQLSIE3UV
        foreign key (COORDINATES_ID) references COORDINATES (ID)
);

create table COVER
(
    ID      VARCHAR(255) auto_increment,
    DATA    BLOB,
    NAME    VARCHAR(255),
    TYPE    VARCHAR(255),
    BOOK_ID BIGINT,
    primary key (ID),
    constraint FK7JOELP33TNSU71VUPV6KRKREM
        foreign key (BOOK_ID) references BOOK (ID)
);

create table BOOK_COVER
(
    BOOK_ID  BIGINT       not null,
    COVER_ID VARCHAR(255) not null,
    constraint UK_A64CMDX1XPBQK0XATIXCC0A0D
        unique (COVER_ID),
    constraint FK2UEQY6JSNSSP3J1WIBPRT68F2
        foreign key (COVER_ID) references COVER (ID),
    constraint FKOEAGBNA31SR7IWPY5UUEFVP0W
        foreign key (BOOK_ID) references BOOK (ID)
);

create table USER
(
    ID             BIGINT auto_increment,
    EMAIL          VARCHAR(255) not null,
    NAME           TEXT         not null,
    PASSWORD       VARCHAR(255) not null,
    SURNAME        TEXT         not null,
    USERNAME       VARCHAR(255) not null,
    COORDINATES_ID BIGINT,
    primary key (ID),
    constraint UK_OB8KQYQQGMEFL0ACO34AKDTPE
        unique (EMAIL),
    constraint UK_SB8BBOUER5WAK8VYIIY4PF2BX
        unique (USERNAME),
    constraint FK1FL6UT5W9XEBF7364DYYQ6W5J
        foreign key (COORDINATES_ID) references COORDINATES (ID)
);

create table CHAT_ROOM
(
    ID           BIGINT auto_increment,
    RECIPIENT_ID BIGINT,
    SENDER_ID    BIGINT,
    primary key (ID),
    constraint FK5OMNAUEWBG4GA8EY3W8U6D0DD
        foreign key (SENDER_ID) references USER (ID),
    constraint FKPK5KXFC5QE64H4F4F4W4M6YYC
        foreign key (RECIPIENT_ID) references USER (ID)
);

create table CHAT_MESSAGE
(
    ID           BIGINT auto_increment,
    CONTENT      VARCHAR(255),
    TIMESTAMP    TIMESTAMP,
    ROOM_ID      BIGINT,
    RECIPIENT_ID BIGINT,
    SENDER_ID    BIGINT,
    primary key (ID),
    constraint FKFVBC4WVHK51Y0QTNJRBMINXFU
        foreign key (ROOM_ID) references CHAT_ROOM (ID),
    constraint FKM156ENE5XJFIA6VE3MWAPCKLA
        foreign key (RECIPIENT_ID) references USER (ID),
    constraint FKM92RH2BMFW19XCN7NJ5VRIXSI
        foreign key (SENDER_ID) references USER (ID)
);

create table EXCHANGE
(
    ID              BIGINT auto_increment,
    DEPOSIT         NUMBER,
    EXCHANGE_STATUS INTEGER not null,
    BOOK_ID         BIGINT,
    COORDINATES_ID  BIGINT,
    FOR_BOOK_ID     BIGINT,
    USER_ID         BIGINT,
    WITH_USER_ID    BIGINT,
    primary key (ID),
    constraint FK2RLNDWL0584JHNKKX3KN19F1S
        foreign key (COORDINATES_ID) references COORDINATES (ID),
    constraint FK3EB1WCN8BB9V5MD8V8P3C2FE
        foreign key (USER_ID) references USER (ID),
    constraint FK6P9EFAQP9H3WGW54W5S922OLT
        foreign key (BOOK_ID) references BOOK (ID),
    constraint FKG74RQI4NPJH06N4X7KRJSQGD3
        foreign key (WITH_USER_ID) references USER (ID),
    constraint FKKGIXVC3RF2S5SCFOJJ2LXMWA2
        foreign key (FOR_BOOK_ID) references BOOK (ID)
);

create table REQUIREMENT
(
    ID          BIGINT auto_increment,
    CREATE_TIME TIMESTAMP,
    IS_ACTUAL   BOOLEAN not null,
    EXCHANGE_ID BIGINT,
    USER_ID     BIGINT,
    primary key (ID),
    constraint FK70OB1ES44DHN8NK8GQH485OW7
        foreign key (USER_ID) references USER (ID),
    constraint FKTGLK51523CWR8PY7PY1HBCKQ
        foreign key (EXCHANGE_ID) references EXCHANGE (ID)
);

create table USER_BOOK
(
    BOOK_ID     BIGINT not null,
    USER_ID     BIGINT not null,
    BOOK_STATUS INTEGER,
    AT_USER_ID  BIGINT,
    primary key (BOOK_ID, USER_ID),
    constraint FK85PWLTN867PJXOG1GK5SMTQCW
        foreign key (BOOK_ID) references BOOK (ID),
    constraint FKBC0BWDNNDNXHCT38SINBEM0N0
        foreign key (USER_ID) references USER (ID),
    constraint FKTFJUFF2HCGMB7D32KAJMGJH95
        foreign key (AT_USER_ID) references USER (ID)
);

create table USER_CHAT_ROOMS
(
    USER_ID       BIGINT not null,
    CHAT_ROOMS_ID BIGINT not null,
    constraint UK_OW07C33V71B7WEBJQ1S9Q1GF9
        unique (CHAT_ROOMS_ID),
    constraint FK6BFNQIQKQLNDJ14S4TU3BLHQU
        foreign key (USER_ID) references USER (ID),
    constraint FKOOXNCW9DUE6H7B2BWF2FTO773
        foreign key (CHAT_ROOMS_ID) references CHAT_ROOM (ID)
);


