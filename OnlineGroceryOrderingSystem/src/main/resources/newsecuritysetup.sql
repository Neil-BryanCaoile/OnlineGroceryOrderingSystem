create table CART(
	cartItemId   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
	itemId       BIGINT NOT NULL
);



create table SEC_USER
(
  userId           BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userName         VARCHAR(36) NOT NULL UNIQUE,
  encryptedPassword VARCHAR(128) NOT NULL,
  ENABLED           BIT NOT NULL 
) ;


create table SEC_ROLE
(
  roleId   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
) ;


create table USER_ROLE
(
  ID      BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  roleId BIGINT NOT NULL
);

alter table USER_ROLE
  add constraint USER_ROLE_UK unique (userId, roleId);

alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (userId)
  references SEC_USER (userId);
 
alter table USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (roleId)
  references SEC_ROLE (roleId);

  
/*Password 123*/
   
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('admin', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('TomBrady', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);


 
insert into sec_role (roleName)
values ('ROLE_ADMIN');
 
insert into sec_role (roleName)
values ('ROLE_USER');

insert into sec_role (roleName)
values ('ROLE_GUEST');

 
 

/* roleId = 1 = admin 
 * roleId = 2 = user 
 * roleId = 3 = student*/
insert into user_role (userId, roleId)
values (1, 1);
 
insert into user_role (userId, roleId)
values (1, 2);
 
insert into user_role (userId, roleId)
values (2, 2);

create table ITEMS(
	itemId         BIGINT NOT NULL Primary Key AUTO_INCREMENT,
	itemName       VARCHAR(36) NOT NULL UNIQUE,
	itemPrice      DECIMAL(6,2) NOT NULL,
	itemSrcImg     VARCHAR(255),
	itemType     VARCHAR(255) NOT NULL
);



insert into ITEMS (itemName, itemPrice,itemSrcImg,itemType)
values ('Frosty Fudgy-Bar', 6.99, '/img/items/FrostyFudgyBar.jpg','sweet'),
      ('Ginger Bread Cinamon', 8.99, '/img/items/GingerBreadCinamon.jpg','sweet'),
      ('Choco-Vanilla Snow', 8.99, '/img/items/ChocoVanillaSnow.jpg','sweet'),
      ('Raindeer Ham', 12.99, '/img/items/Raindeer-Ham.jpg','meat'),
      ('Raindeer Meat', 10.99, '/img/items/Raindeer-meat.jpg','meat'),
      ('Raindeer Patty', 11.99, '/img/items/Raindeer-Patty.jpg','meat'),
      ('Avocadoes', 6.99, '/img/items/avocado.jpg','vegetable'),
      ('Cherry Tomatoes', 5, '/img/items/cherry-tomato.jpg','vegetable'),
      ('Spinach', 7.99, '/img/items/spinach.jpg','vegetable');

create table ITEM_TYPES(
	typeId         BIGINT NOT NULL Primary Key AUTO_INCREMENT,
	itemType VARCHAR(36) NOT NULL UNIQUE
);

insert into ITEM_TYPES (itemType)
values ('sweet'),('vegetable'),('meat');
 

COMMIT;

