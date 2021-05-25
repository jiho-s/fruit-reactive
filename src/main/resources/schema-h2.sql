create table ACCOUNT
(
    ID BIGINT auto_increment
        primary key,
    APPLE_IN_BASKET INTEGER,
    APPLE_IN_TREE INTEGER,
    EMAIL VARCHAR(255),
    LAST_LOGIN TIMESTAMP,
    PASSWORD VARCHAR(255)
);

create table HABIT
(
    ID BIGINT auto_increment
        primary key,
    DESCRIPTION VARCHAR(255),
    NAME VARCHAR(255),
--     FRIDAY BOOLEAN not null,
--     MONDAY BOOLEAN not null,
--     SATURDAY BOOLEAN not null,
--     SUNDAY BOOLEAN not null,
--     THURSDAY BOOLEAN not null,
--     TUESDAY BOOLEAN not null,
--     WEDNESDAY BOOLEAN not null,
    ACCOUNT_ID BIGINT,
    constraint FK1B1HL8VO7YKRY2WRH6OVHL5PL
        foreign key (ACCOUNT_ID) references ACCOUNT (ID)
);

create table HABIT_EXECUTION_DATE
(
    ID BIGINT auto_increment
        primary key,
    DATE TIMESTAMP,
    HABIT_ID BIGINT,
    ACCOUNT_ID BIGINT,
    constraint FK8D29QN9OMQ66YR8TMFNNI710D
        foreign key (HABIT_ID) references HABIT (ID),
    constraint foreign_habit_execution_date_account
        foreign key (ACCOUNT_ID) references ACCOUNT (ID)
);

