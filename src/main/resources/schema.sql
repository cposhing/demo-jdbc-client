
create table if not exists customer (
    id serial primary key ,
    name varchar(255) not null
);
drop table if exists orders;
create table if not exists orders (
    id serial primary key ,
    customer_id bigint ,
    content varchar(255) not null
);


