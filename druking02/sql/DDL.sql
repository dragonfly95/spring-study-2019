CREATE TABLE mydb.SimpleTeam (
	campus INT NULL,
	department INT NULL,
	team1 varchar(100) NULL,
	team2 varchar(100) NULL,
	pastor varchar(100) NULL,
	elder varchar(100) NULL,
	cheif varchar(100) NULL,
	manager varchar(100) NULL,
	excutors TEXT NULL,
	uploader varchar(100) NULL,
	id INT NOT NULL AUTO_INCREMENT,
	CONSTRAINT SimpleTeam_PK PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8;


CREATE TABLE mydb.`User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `user_pass` varchar(100) DEFAULT NULL,
  `user_birth` varchar(100) DEFAULT NULL,
  `role` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
