
CREATE TABLE `member` (  
    `code` int(11) NOT NULL AUTO_INCREMENT,  
    `name` varchar(255) DEFAULT NULL,  
    `team` varchar(255) DEFAULT NULL,  
    PRIMARY KEY (`code`)
)


insert into member (name, team) values ('홍길동','청팀');
insert into member (name, team) values ('강감찬','백팀');
insert into member (name, team) values ('이순신','청팀');
