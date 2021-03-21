create sequence hibernate_sequence start 1 increment 1;
create table cart
(
    id          int8 not null,
    created     timestamp,
    modified    timestamp,
    paid_amount numeric(19, 2),
    paid_time   timestamp,
    total       numeric(19, 2),
    primary key (id)
);
create table cart_items
(
    cart_id  int8 not null,
    items_id int8 not null
);
create table product
(
    id    int8 not null,
    name  varchar(255),
    price numeric(19, 2),
    primary key (id)
);
create table product_line_item
(
    id               int8 not null,
    line_item_status varchar(255),
    product_id       int8,
    primary key (id)
);
alter table if exists cart_items
    add constraint UK_383kkp3af9dpn91t406oqe9n1 unique (items_id);
alter table if exists cart_items
    add constraint FKfypwlqb5ec95k6ple3q6hwgag foreign key (items_id) references product_line_item;
alter table if exists cart_items
    add constraint FK99e0am9jpriwxcm6is7xfedy3 foreign key (cart_id) references cart;
alter table if exists product_line_item
    add constraint FKoj8l343i67b12b6n2wueecu3i foreign key (product_id) references product;
