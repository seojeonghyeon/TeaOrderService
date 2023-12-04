# TeaOrderService
Tea 주문 서비스

## 구현 기능
1. 커피 메뉴 목록 조회 API(조건 검색 기능)
2. 포인트 충전하기 API
3. 커피 주문, 결제하기 API
4. 인기메뉴 목록 조회 API

## Swagger
http://localhost:8080/swagger-ui/index.html
![스크린샷 2023-11-24 00 01 13](https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/2d4cc326-8083-4860-9b78-00e04705f1b2)


## Entity 기반 ERD
![스크린샷 2023-12-04 23 51 21](https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/b13e0225-d584-4008-a8d0-33c0483a4d86)


## ERD
![스크린샷 2023-12-04 23 44 39](https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/4e970229-8a8d-46ab-b516-de32788dd917)



## PostgreSQL Settings
```
 zayden@Justin-MacBook-Pro  ~  docker pull postgres:latest
 zayden@Justin-MacBook-Pro  ~  docker run -p 5432:5432 --name local-postgres \
> -e POSTGRES_PASSWORD=00000000 \
> -e TZ=Asia/Seoul \
> -v /Users/zayden/Documents/Work/postgres/data:/var/lib/postgresql/data \
> -d postgres:latest
647a65814db1334f07844b4313b62e51e8522c53b70044a061693f8f8f75e1a2

```

Database 생성
```
 zayden@Justin-MacBook-Pro  ~  docker exec -it 647a bash
root@647a65814db1:/# psql -U postgres

postgres=# create database teaorderdb;
CREATE DATABASE


   Name    |  Owner   | Encoding | Locale Provider |  Collate   |   Ctype    | ICU Locale | ICU Rules |   Access privileges

------------+----------+----------+-----------------+------------+------------+------------+-----------+---------------------
--
 postgres   | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           |
 teaorderdb | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           |
 template0  | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           | =c/postgres
 +
            |          |          |                 |            |            |            |           | postgres=CTc/postgre
s
 template1  | postgres | UTF8     | libc            | en_US.utf8 | en_US.utf8 |            |           | =c/postgres
 +
            |          |          |                 |            |            |            |           | postgres=CTc/postgre
s
```

API 계정 생성 및 권한 부여 
```
postgres=# create role api_user with login password '00000000';
CREATE ROLE
postgres=# alter user api_user with createdb;
ALTER ROLE
postgres=# alter user api_user with superuser;
ALTER ROLE
postgres=# grant all privileges on database teaorderdb to api_user;
GRANT
```

DB Table 조회
```
teaorderdb=# \dt
                List of relations
 Schema |         Name         | Type  |  Owner
--------+----------------------+-------+----------
 public | authority            | table | api_user
 public | category             | table | api_user
 public | member_authorities   | table | api_user
 public | members              | table | api_user
 public | menu_category        | table | api_user
 public | menus                | table | api_user
 public | orders               | table | api_user
 public | points               | table | api_user
 public | product_order        | table | api_user
 public | product_order_counts | table | api_user
(10 rows)
```
