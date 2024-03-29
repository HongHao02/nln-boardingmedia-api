﻿# nln-boardingmedia-api
Spring Boot Application with Spring Security (JWT)
# Description
The social media platform is used for searching affordable rooms in boarding houses. Boarding house owners utilize it for advertising and managing their rooms. It also provides consultancy services for renters seeking accommodation.
# Brief Architecture
- `LANGUAGE`: Java 17
- `FRAMEWORK`: Spring Boot 3
- `VERSION`: 3.1.8
- `APPLICATION SERVER`: Embedded Apache Tomcat Server
- `DATABASE`: SQL SERVER 2022 (160)
- `ORM`: Hibernate with Spring Data Jpa Implementation
- `AUTHENTICATION`: jwt
  - 1 day access token (1000 * 60 * 60 * 24)
  - 7 day refresh token (604800000)
- `TEST TOOL`: 
  - Postman
- `BUILD TOOL`: Maven
# Features
- Login, sign up
- Authorization with jwt with role
  - `ADMIN`: administrator
  - `CHUTRO`: boarding house owner
  - `KHACHTHUE`: renter
  - `USER` : people who have an account
- `For boarding house owner`:
  - Create and delete boarding house
  - Create and delete floor
  - Create, update status and delete a room
  - Create, update and delete a post
  - Update the status 'view' of the consultant
- `For Admin`:
  - Create and delete province, district, commune and road
- `For Renter`:
  - Create a consultation for registering for rental advisory
- `For User who has an account`:
  - Create, update content, delete a comment
  - Change password, information and avatar
  - Like and unlike a post
- `For public request (permit all by Spring Security)`:
  - Retrieve all comments of a post
  - Retrieve all consultant of the boarding house owner
  - Find boarding houses by name, address
  - Retrieve list of posts following page and size
  - Retrieve a post by id (post's info and list of comments)
  - Retrieve list of consultant of a user
  - Retrieve a boarding house by id
  - Retrieve list of boarding house of the username
  - Retrieve user info
# API
-  To test the API, please import the Postman file provided: `https://github.com/HongHao02/nln-boardingmedia-api/blob/d48ac7c88c14ac2d88563effb6e67f8122221c90/NLN_MXHTKNT.postman_collection.json`
- `Default domain`: https://honghaocp-api-boarding-house.azurewebsites.net
  - eg: https://honghaocp-api-boarding-house.azurewebsites.net/api/v1/public/users/@honghaocp@gmail.com to get user info
    ![image](https://github.com/HongHao02/nln-boardingmedia-api/assets/99229574/65c43ae0-f16b-4188-8292-18882e76d8a5)


# In sql script add this statement:
- `CREATE SEQUENCE lau_sequence START WITH 1 INCREMENT BY 1;`
- `CREATE SEQUENCE phong_sequence START WITH 1 INCREMENT BY 1;`

