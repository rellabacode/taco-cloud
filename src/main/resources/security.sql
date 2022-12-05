create table users(username varchar(255) not null primary key,password varchar(255) not null,enabled  boolean not null);

--INSERT INTO USERS(USERNAME, PASSWORD, ENABLED) VALUES ('buzz', '{noop}infinity', true);
INSERT INTO USERS(USERNAME, PASSWORD, ENABLED) VALUES ('buzz', '$2a$10$XohWVS.IQXf0skjeY/IEwe9Y3fGxsGUTDzMUyr9kPTMa3A6NEKvWK', true);

create table authorities(username  varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key (username) references users (username));

INSERT INTO AUTHORITIES(USERNAME, AUTHORITY) VALUES ('buzz', 'ROLE_USER');

create unique index ix_auth_username on authorities (username, authority);

create table groups(id         bigint generated by default as identity (start with 0) primary key, group_name varchar(50) not null);

create table group_authorities(group_id  bigint      not null,authority varchar(50) not null,constraint fk_group_authorities_group foreign key (group_id) references groups (id));

create table group_members(id       bigint generated by default as identity (start with 0) primary key,username varchar(50) not null,group_id bigint      not null,constraint fk_group_members_group foreign key (group_id) references groups (id));

INSERT INTO USUARIOS(ID, CITY, FULL_NAME, MOBILE, STATE, STREET, USER_NAME, USER_PASSWORD, ZIP) VALUES (0, 'MADRID', 'PACO MARTINEZ', '654987321', 'ES', 'CALLE FALSA 123', 'jesus', '07027205a95c63f90fe2a823c034fb4c3755bc89b59d2e406b229acbdb94fdf0c8bfb0f84bb02610', '30007');