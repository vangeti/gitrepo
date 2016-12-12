
CREATE TABLE `employee` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `dob` varchar(20) DEFAULT NULL,
  `age` int(5) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `deptno` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `deptno` (`deptno`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`deptno`) REFERENCES `department` (`deptno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `department` (
  `deptno` int(5) NOT NULL,
  `dname` varchar(14) DEFAULT NULL,
  `loc` varchar(13) DEFAULT NULL,
  PRIMARY KEY (`deptno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `suncoastpoc`.`employee`
(`id`,
`name`,
`dob`,
`age`,
`gender`,
`deptno`)
VALUES
(100,'Jhon','11-2-1989',27,'male',10),
(200,'Mike','11-2-1988',28,'male',20);

INSERT INTO `suncoastpoc`.`department`
(`deptno`,
`dname`,
`loc`)
VALUES
(10,'Dev','CA'),
(20,'Test','New York');
commit;