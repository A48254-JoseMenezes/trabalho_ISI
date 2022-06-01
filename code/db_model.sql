/*
*   ISEL-DEETC-ISI
*   ND/MP 2017-2019
*
*   Material didático para apoio 
*   à unidade curricular de 
*   Introdução a Sistemas de Informação
*
*/


begin;

drop table if exists studentcourse;
drop table if exists  student;
drop table if exists  course;

create table if not exists student(
	stdid serial primary key,
	namestd varchar(256) not null unique,
	dateBirth date,
	sex char not null
);

create table if not exists course(
	cid serial primary key,
	namec varchar(256) not null
);

create table if not exists studentcourse(
	stdid integer references  student,
	cid integer references course,
	primary key(stdid,cid)
);


--populate

SET DATESTYLE TO European;

insert into student(namestd,dateBirth,sex) values ('John','21-01-1970','M'),('Joe','12-07-1971','M'),('Mary','4-05-1969','F'), ('Bob','12-12-1970','M'), ('Zoe','12-12-1978','F');
insert into course(namec) values ('Information Systems I'), ('Internet Programming'),('Concurrent programming');
insert into studentcourse values (1,1),(1,2),(1,3),(2,2),(2,3),(3,1),(3,3);	

commit

