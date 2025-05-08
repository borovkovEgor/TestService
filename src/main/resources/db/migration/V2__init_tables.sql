create table test_service.users (
    id serial primary key,
    first_name varchar,
    last_name varchar,
    age integer check (age >= 18 and age <= 120),
    email varchar unique not null
);

create table test_service.subscriptions (
    id serial primary key,
    sub_name varchar unique not null
);

create table test_service.user_subscriptions (
    user_id bigint not null,
    subscription_id bigint not null,
    primary key (user_id, subscription_id),
    foreign key (user_id) references test_service.users(id) on delete cascade,
    foreign key (subscription_id) references test_service.subscriptions(id) on delete cascade
);