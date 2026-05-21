insert into product (name, stock, price) values ('Coca-Cola', 11, 1.23);
insert into product (name, stock, price) values ('Pizza', 17, 2.78);
insert into product (name, stock, price) values ('Beer', 30, 0.70);
insert into product (name, stock, price) values ('Wine', 5, 4.67);
insert into product (name, stock, price) values ('Apple (1 Kg.)', 43, 0.97);
insert into product (name, stock, price) values ('Orange (1 Kg.)', 33, 1.34);
insert into product (name, stock, price) values ('Pasta', 13, 2.34);

insert into user (name, password) values ('buyer', 'buyer');
insert into user (name, password) values ('seller', 'seller');
insert into rol (name) values ('buyer');
insert into rol (name) values ('seller');
insert into user_rol (users_id, roles_id) values (1, 1);
insert into user_rol (users_id, roles_id) values (2, 2);