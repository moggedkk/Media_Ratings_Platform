create table users
(
    user_id       serial
        primary key,
    username      varchar(50) not null
        unique,
    password_hash text        not null,
    token         text
);

alter table users
    owner to postgres;

create table genres
(
    genre_id serial
        primary key,
    name     varchar(50) not null
        unique
);

alter table genres
    owner to postgres;

create table media_entries
(
    media_id        serial
        primary key,
    user_id         integer      not null
        references users
            on delete cascade,
    title           varchar(255) not null,
    description     text,
    media_type      varchar(20)
        constraint media_entries_media_type_check
            check ((media_type)::text = ANY
                   ((ARRAY ['movie'::character varying, 'series'::character varying, 'game'::character varying])::text[])),
    release_year    integer,
    age_restriction integer
);

alter table media_entries
    owner to postgres;

create table ratings
(
    rating_id serial
        primary key,
    media_id  integer not null
        references media_entries
            on delete cascade,
    user_id   integer not null
        references users
            on delete cascade,
    stars     integer
        constraint ratings_stars_check
            check ((stars >= 1) AND (stars <= 5)),
    comment   text,
    confirmed boolean default false
);

alter table ratings
    owner to postgres;

create table favorites
(
    favorites_id serial
        primary key,
    user_id      integer not null
        references users
            on delete cascade,
    media_id     integer not null
        references media_entries
            on delete cascade
);

alter table favorites
    owner to postgres;

create table rating_likes
(
    rating_likes_id serial
        primary key,
    rating_id       integer not null
        references ratings
            on delete cascade,
    user_id         integer not null
        references users
            on delete cascade
);

alter table rating_likes
    owner to postgres;

create table media_genre
(
    media_genre_id serial
        primary key,
    media_id       integer not null
        references media_entries
            on delete cascade,
    genre_id       integer not null
        references genres,
    unique (media_id, genre_id)
);

alter table media_genre
    owner to postgres;

