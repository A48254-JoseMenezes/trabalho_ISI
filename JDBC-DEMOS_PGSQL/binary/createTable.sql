drop table if exists jdbcblob;

create table jdbcblob
(
	filename varchar(260) not null,
	databin bytea not null,
 	constraint PK_JDBCBLOBDEMO primary key (filename)
);