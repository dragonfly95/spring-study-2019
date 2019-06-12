
create database mydb;

create user 'dbdyd'@'localhost' identified by '1q2w3e4r5t';
grant all privileges on mydb.* to 'dbdyd'@'localhost';

create user 'dbdyd'@'%' identified by '1q2w3e4r5t';
grant all privileges on mydb.* to 'dbdyd'@'%';

flush privileges;


CREATE TABLE `member` (  
    `code` int(11) NOT NULL AUTO_INCREMENT,  
    `name` varchar(255) DEFAULT NULL,  
    `team` varchar(255) DEFAULT NULL,  
    PRIMARY KEY (`code`)
)


insert into member (name, team) values ('홍길동','청팀');
insert into member (name, team) values ('강감찬','백팀');
insert into member (name, team) values ('이순신','청팀');