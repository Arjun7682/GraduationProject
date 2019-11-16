#**Выпускной проект**


Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we asume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

-------------------------------------------------------------------------------

#**CURL requests to test web-application:**

**Rest Admin requests:**

Operation: | CURL command: | User:
----------- | ------- | --------
Create new User | curl -s -X POST -d '{"name":"New","email":"new@gmail.com","password":"newPass","role":["ROLE_USER"]}' -H 'Content-Type: application/json' http://localhost:8080/GraduationProject/rest/admin/users/ --user admin@gmail.com:admin | Admin
Get user | curl -s "http://localhost:8080/GraduationProject/rest/admin/users/100001" --user admin@gmail.com:admin | Admin
Get User by Email | curl -s "http://localhost:8080/GraduationProject/rest/admin/users/by?admin@gmail.com" --user admin@gmail.com:admin | Admin
Update User | curl -s -X PUT -d '{"id":100024, "name":"UpdatedName","email":"test2@yandex.ru","password":"password2","registered":"2019-11-01T00:00:00", "enabled":true,"roles":["ROLE_USER"]}' -H 'Content-Type: application/json' http://localhost:8080/GraduationProject/rest/admin/users/100001 --user admin@gmail.com:admin | Admin
Delete User | curl -s -X DELETE http://localhost:8080/GraduationProject/rest/admin/users/100023 --user admin@gmail.com:admin | Admin
Get all users | curl -s "http://localhost:8080/GraduationProject/rest/admin/users" --user admin@gmail.com:admin | Admin

**Rest Profile requests:**

Operation: | CURL command: | User:
----------- | ------- | --------
Get User | curl -s "http://localhost:8080/GraduationProject/rest/profile" --user user@yandex.ru:password | Authorized
Update User | curl -s -X PUT -d '{"name": "UpdatedUser","email": "test2@yandex.ru", "password":"password2"}' -H 'Content-Type: application/json' http://localhost:8080/GraduationProject/rest/profile --user user@yandex.ru:password | Authorized
Delete User | curl -s -X DELETE "http://localhost:8080/GraduationProject/rest/profile" -user test2@yandex.ru:password2 | Authorized
Register User | curl -s -X POST -d '{"name": "NewUser","email": "new@yandex.ru","password": "password"}' -H 'Content-Type: application/json;charset=UTF-8' http://localhost:8080/GraduationProject/rest/profile/register | Anonymous


**Rest Restaurants requests:**

Operation: | CURL command: | User:
----------- | ------- | --------
Create new Restaurant | curl -s -X POST -d '{"id": null, "name": "New"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/GraduationProject/rest/restaurants/ --user admin@gmail.com:admin | Admin
Get Restaurant | curl -s http://localhost:8080/GraduationProject/rest/restaurants/100002 | All
Update Restaurant | curl -s -X PUT -d '{"id":100006, "name":"UpdatedRestaurant"}' -H 'Content-Type: application/json' http://localhost:8080/GraduationProject/rest/restaurants/100006 --user admin@gmail.com:admin | Admin
Delete Restaurant | curl -s -X DELETE http://localhost:8080/GraduationProject/rest/restaurants/100006 --user admin@gmail.com:admin | Admin
Get All Restaurants | curl -s http://localhost:8080/GraduationProject/rest/restaurants/all | All

**Rest Dishes requests:**

Operation: | CURL command: | User:
----------- | ------- | --------
Create Dish | curl -s -X POST -d '{"id": null,"dateTime":"2019-11-01T00:00", "restaurantId": 100002, "description": "New" , "price": 20000}' -H 'Content-Type: application/json;charset=UTF-8' "http://localhost:8080/GraduationProject/rest/dishes/" --user admin@gmail.com:admin | Admin
Get Dish | curl -s http://localhost:8080/GraduationProject/rest/dishes/100007 | All
Update Dish | curl -s -X PUT -d '{"id":100008, "dateTime":"2019-11-01T00:00","restaurantId":100002, "description":"Updated", "price":20010}' -H 'Content-Type: application/json' http://localhost:8080/GraduationProject/rest/dishes/100008 --user admin@gmail.com:admin | Admin
Delete Dish | curl -s -X DELETE http://localhost:8080/GraduationProject/rest/dishes/100008 --user admin@gmail.com:admin | Admin
Get daily menu by restaurant | curl -s 'http://localhost:8080/GraduationProject/rest/dishes/?restaurantId=100002 | All

**Rest Vote requests:**

Operation: | CURL command: | User:
----------- | ------- | --------
Create Vote | curl -s -X POST -d '{"restaurantId":100002}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/GraduationProject/rest/votes/ --user user@yandex.ru:password | Authorized
Get Vote | curl -s http://localhost:8080/GraduationProject/rest/votes/100022 --user user@yandex.ru:tom | Authorized
Update Vote | curl -s -X PUT -d '{"id":100025, "dateTime":"2019-08-21T17:00","restaurantId":100004}' -H 'Content-Type: application/json' http://localhost:8080/GraduationProject/rest/votes/100025 --user user@yandex.ru:tom | Authorized
Delete Vote | curl -s -X DELETE http://localhost:8080/GraduationProject/rest/votes/100026 --user user@yandex.ru:tom | Authorized
Get all Votes | curl -s http://localhost:8080/GraduationProject/rest/votes/ --user user@yandex.ru:tom | Authorized
