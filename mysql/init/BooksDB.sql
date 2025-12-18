drop database if exists books;
create database books;
use books;

drop table if exists loans;
drop table if exists books;
drop table if exists users_roles;
drop table if exists users;
drop table if exists roles;

create table roles(
	id int not null primary key auto_increment,
    name nvarchar(15) not null,
    description nvarchar(50) not null
);

insert into roles(name, description) values('ROLE_ADMIN', 'Admin');
insert into roles(name, description) values('ROLE_TESTER', 'Tester');
insert into roles(name, description) values('ROLE_USER', 'User');

create table users(
	id int not null primary key auto_increment,
    username nvarchar(50) not null,
    password nvarchar(255) not null
);

create unique index idx_username on users(username);

create table users_roles(
	id int not null primary key auto_increment,
    user_id int not null,
    rol_id int not null,
    foreign key (user_id) references users(id),
    foreign key (rol_id) references roles(id)
);

create table books(
	id int not null primary key auto_increment,
    title nvarchar(200) not null,
    isbn nvarchar(50) not null,
    authors nvarchar(300) not null,
    published_year int not null,
    available bool not null
);

create table loans(
	id int not null primary key auto_increment,
    id_book int not null,
    id_person nvarchar(20) not null,
    first_name nvarchar(20) not null,
    last_name nvarchar(20) not null,
    date datetime not null,
    reversed bool not null,
    active bool not null,
    foreign key (id_book) references books(id)
);

insert into books(title, isbn, authors, published_year, available) 
values('Cálculo De Una Variable', '001-0001', 'James Stewart', 2011, '1');
insert into books(title, isbn, authors, published_year, available) 
values('Cálculo De Una Variable', '001-0002', 'James Stewart', 2012, '1');
insert into books(title, isbn, authors, published_year, available) 
values('Cálculo De Una Variable', '001-0003', 'James Stewart', 2013, '1');
insert into books(title, isbn, authors, published_year, available) 
values('Probabilidad y Estadística', '021-0099', 'Michael J. Evans, Jeffrey S. Rosenthal', 2016, '1');
insert into books(title, isbn, authors, published_year, available) 
values('Differential Equations', '553-0008', 'Dennis G. Zill', 2020, '1');

-- ----------------------------------------------------------
-- ----------------------------------------------------------
-- admin
-- $2a$10$jtBs710/zfl3vuWsi/AygOYQkFGaqJ4EBZZL17NAyUOkHJztZrVle
insert into users(username, password) values('admin@gmail.com', '$2a$10$jtBs710/zfl3vuWsi/AygOYQkFGaqJ4EBZZL17NAyUOkHJztZrVle');

insert into users_roles(user_id, rol_id) values(1, 1); -- ADMIN
insert into users_roles(user_id, rol_id) values(1, 2); -- TESTER
