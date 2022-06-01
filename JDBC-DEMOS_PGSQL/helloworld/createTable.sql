drop table if exists jdbcdemo;

create table if not exists jdbcdemo(
	id int,
	value varchar(50)
);

insert into jdbcdemo values (1,'Ola '), (2,'Mundo '), (3,'do '), (4,'JDBC'), (5,'!');