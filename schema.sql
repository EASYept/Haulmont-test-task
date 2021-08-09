CREATE TABLE bank (
    bank_uuid UUID not null,
    bank_name varchar(255),
    primary key (bank_uuid));

create table CLIENT (
    uuid UUID not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    /* Probably should be 10 chars long. But for testing i keep it like this*/
    passport_number varchar(255),
    patronymic varchar(255),
    /* Probably should be 16 chars long. But for testing i keep it like this*/
    phone_number varchar(255),
    bank_uuid UUID,
    primary key (uuid));

create table credit (
    uuid UUID not null,
    credit_limit integer not null,
    credit_rate float not null,
    bank_uuid UUID, primary key (uuid));

create table credit_offer (
    uuid UUID not null,
    amount float not null,
    months integer not null,
    starting_date date,
    client_uuid UUID,
    credit_uuid UUID,
    primary key (uuid));

alter table client add constraint client_bank_fk foreign key (bank_uuid) references bank;
alter table credit add constraint credit_bank_fk foreign key (bank_uuid) references bank;
alter table credit_offer add constraint offer_client_fk foreign key (client_uuid) references client;
alter table credit_offer add constraint offer_credit_fk foreign key (credit_uuid) references credit;
