DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM restaurants;
DELETE
FROM menus;
DELETE
FROM voting_history;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO restaurants (NAME)
VALUES ('McDonalds'),
       ('KFC'),
       ('Teremok'),
       ('Marketplace'),
       ('Big Kahuna Burger');

INSERT INTO menus (restaurant_id, date_time, dish, price)
VALUES (100002, '2019-08-20 17:00:00', 'Burger', 16000),
       (100002, '2019-08-20 17:00:00', 'Fries', 7000),
       (100002, '2019-08-20 17:00:00', 'Americano', 5000),
       (100003, '2019-08-20 17:00:00', 'Chicken', 18000),
       (100003, '2019-08-20 17:00:00', 'Fries', 6500),
       (100003, '2019-08-20 17:00:00', 'Americano', 4500),
       (100004, '2019-08-20 17:00:00', 'Pancake', 23000),
       (100004, '2019-08-20 17:00:00', 'Sup', 12000),
       (100004, '2019-08-20 17:00:00', 'Americano', 7000),
       (100005, '2019-08-20 17:00:00', 'Sup', 15000),
       (100005, '2019-08-20 17:00:00', 'Meat', 30000),
       (100005, '2019-08-20 17:00:00', 'Americano', 13000),
       (100006, '2019-08-20 17:00:00', 'Burger', 27000),
       (100006, '2019-08-20 17:00:00', 'Fries', 10000),
       (100006, '2019-08-20 17:00:00', 'Americano', 8000);

INSERT INTO voting_history (user_id, restaurant_id)
VALUES (100000, 100005);
