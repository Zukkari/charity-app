create table basket
(
    id          int8 not null,
    created     timestamp,
    modified    timestamp,
    paid_amount numeric(19, 2),
    primary key (id)
);
create table basket_items
(
    basket_id int8 not null,
    items_id int8 not null
);
create table basket_line_item
(
    id         int8 not null,
    product_id int8,
    primary key (id)
);
create table product
(
    id    int8 not null,
    name  varchar(255),
    price numeric(19, 2),
    primary key (id)
);
alter table if exists basket_items
    add constraint UK_d0vxbj4gqklaa3ro4kja2h72x unique (items_id);
alter table if exists basket_items
    add constraint FKmhurnhd8gawv0gd97j599t7vu foreign key (items_id) references basket_line_item;
alter table if exists basket_items
    add constraint FKdy6l1ieu09exdjope704jwo1e foreign key (basket_id) references basket;
alter table if exists basket_line_item
    add constraint FK9ju2t8oi6c26mtfhltwpwhfpv foreign key (product_id) references product;
