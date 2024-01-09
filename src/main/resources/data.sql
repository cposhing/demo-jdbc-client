delete from customer where 1=1;


insert into customer (name) values ('poshing1');
insert into customer (name) values ('poshing2');
insert into customer (name) values ('poshing3');
insert into customer (name) values ('poshing4');
insert into customer (name) values ('poshing5');


delete from orders where 1 =1 ;
insert into orders (customer_id, content) values ('1', '牙刷');
insert into orders (customer_id, content) values ('1', '毛巾');
insert into orders (customer_id, content) values ('1', '卫生纸');
