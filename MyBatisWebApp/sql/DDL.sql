


drop table TBL_MEMBER;

create table TBL_MEMBER (
  id varchar(50) not null,
  password varchar(50) not null,
  name varchar(100),
  lev varchar(10),
  regdate DATETIME default CURRENT_TIMESTAMP,
  PRIMARY KEY (ID)
);



create table TBL_BOARD (
  id int not null AUTO_INCREMENT,
  title varchar(100),
  content varchar(1000),
  regdate date,
  readcount int,
  PRIMARY KEY (ID)
);

insert into TBL_BOARD (title, content, regdate) values ('title1','content1', now());
insert into TBL_BOARD (title, content, regdate) values ('title2','content2', now());
insert into TBL_BOARD (title, content, regdate) values ('title3','content3', now());
insert into TBL_BOARD (title, content, regdate) values ('title4','content4', now());
insert into TBL_BOARD (title, content, regdate) values ('title5','content5', now());
insert into TBL_BOARD (title, content, regdate) values ('title6','content6', now());
insert into TBL_BOARD (title, content, regdate) values ('title7','content7', now());