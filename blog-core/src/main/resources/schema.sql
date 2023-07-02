create table `PUBLIC`.KEYWORD_ANALYTICS
(
    id           bigint       NOT NULL AUTO_INCREMENT,
    keyword      varchar(255) not null,
    search_count bigint       not null,
    create_at    datetime(3)         not null,
    primary key (id)
);
create index IX01_KEYWORD_ANALYTICS
    on `PUBLIC`.KEYWORD_ANALYTICS (search_count, id);
create index IX02_KEYWORD_ANALYTICS
    on `PUBLIC`.KEYWORD_ANALYTICS (search_count, keyword);