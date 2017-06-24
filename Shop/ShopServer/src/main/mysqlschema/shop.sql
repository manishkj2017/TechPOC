#https://docs.jboss.org/hibernate/orm/5.0/mappingGuide/en-US/html_single/#entity-pojo-final

create table PetInventory 
(
	id  INTEGER not null PRIMARY KEY AUTO_INCREMENT,
    petType  varchar(20) not null,
    stockAvailable INTEGER not null
);

create unique index inv_id_idx on PetInventory (id) using HASH;

create table Pet
(
	id INTEGER not null PRIMARY KEY auto_increment,
    name varchar(20) not null,
    price numeric not null,
    orderNumber INTEGER not null,
    customerNumber INTEGER not null
);

create table PetOrder
(
	id INTEGER not null PRIMARY KEY auto_increment,
    bidPrice numeric not null,
    petType varchar(20) not null,
    status varchar(10) not null,
    customerName varchar(100), 
    orderNumber INTEGER not null,
    customerNumber INTEGER not null,
    statusReason varchar(100),
    orderSource varchar(10) not null,
    petTag INTEGER DEFAULT 0
);


