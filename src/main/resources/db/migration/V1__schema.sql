create table basket
(
    id          int8 not null,
    created     timestamp,
    modified    timestamp,
    paid_amount numeric(19, 2),
    paid_time   timestamp,
    primary key (id)
);
create table basket_items
(
    basket_id int8 not null,
    items_id  int8 not null
);
create table product_line_item
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
    add constraint UK_pypji3f11i6fuc9b7tkfo1u8r unique (items_id);
alter table if exists basket_items
    add constraint FKo5jhkyu1c3047it01aer4oeo8 foreign key (items_id) references product_line_item;
alter table if exists basket_items
    add constraint FKfyqrlx96qh891kd8g5y4uc7rp foreign key (basket_id) references basket;
alter table if exists product_line_item
    add constraint FKnh3ei2yrbdv2p1s4n9gisdagv foreign key (product_id) references product;
