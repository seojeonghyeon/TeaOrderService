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


## 객체 다이어그램
![스크린샷 2023-11-23 23 50 39](https://github.com/seojeonghyeon/TeaOrderService/assets/24422677/9a9451a4-5267-429c-ba48-3038e1f59146)


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
