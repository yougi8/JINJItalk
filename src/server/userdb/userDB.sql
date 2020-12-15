create database mydb;
use mydb;
show databases;
show tables;

CREATE TABLE member_table (
 uname       VARCHAR(30),
 uemail     VARCHAR(30) not null,
 password    VARCHAR(30), 
  PRIMARY KEY(uemail)
) ENGINE=MYISAM CHARSET=utf8;

ALTER TABLE member_table ADD unickname VARCHAR(30);
ALTER TABLE member_table ADD birth VARCHAR(30);
ALTER TABLE member_table ADD today VARCHAR(50);
ALTER TABLE member_table ADD gmail varCHar(30);

show tables;
desc friendlist;
desc member_table;

INSERT INTO MEMBER_TABLE VALUES('찐찐','a','a');
insert into MEMBER_TABLE values('확찐자','b','b');
insert into MEMBER_TABLE values('노대장','c','c');
insert into MEMBER_TABLE values('정대리여신','d','d');

update member_table set uname='유지연',unickname='지팔이',birth='00.06.29',today='오늘도 과제', gmail='a.com' where uname='찐찐';
update member_table set uname='윤서진',unickname='지니',birth='00.09.15',today='언제 종강?', gmail='b.com' where uname='확찐자';
update member_table set uname='김은서',unickname='은탱이',birth='00.05.19',today='놀고싶어', gmail='c.com' where uname='노대장';
update member_table set uname='이한솔',unickname='소리',birth='00.03.21',today='코로나 물러가라!', gmail='d.com' where uname='정대리여신';

select * from MEMBER_TABLE;

select uname from member_table where uemail='a' and password='a';


create table friendList(
 id int not null AUTO_INCREMENT,
 userEmail varchar(30),
 friendEmail varchar(30),
   PRIMARY KEY(id),
   foreign key (userEmail)
   references member_table (uemail),
   foreign key (friendEmail)
   references member_table (uemail)
) ENGINE=MYISAM CHARSET=utf8;

insert into friendList values(1,'a','b');
insert into friendList values(2,'a','c');
insert into friendList values(3,'a','d');

insert into friendList values(4,'b','a');
insert into friendList values(5,'b','c');
insert into friendList values(6,'b','d');

insert into friendList values(7,'c','b');
insert into friendList values(8,'c','a');
insert into friendList values(9,'c','d');

insert into friendList values(10,'d','b');
insert into friendList values(11,'d','c');
insert into friendList values(12,'d','a');

select * from friendList;