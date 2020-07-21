# Spring JWT Boilerplate
Przykładowa aplikacja z zaimplementowanym uwierzytelnieniem z wykorzystaniem JWT (JSON Web Tokens).

### Technologie
* Spring Boot
* Spring Security
* Spring Data JPA
* H2 Database
* Json Web Tokens

### Endpointy
Logowanie
```
POST /auth/login

Request
{
    "username": "foo",
    "password": "bar"
}

Response
{
    "prefix": "Bearer ",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmb28iLCJleHAiOjE1OTUzNjMzNTV9.lsiBrAffd9hq6v_ICbv6alLmph9Cv1o9UN3eRxeOEzQNs57WtG82i08wvqLOW-nPrU36-ybv7JEysOxb7DX4wA"
}
```
Rejestracja
```
POST /auth/register

Request
{
    "username": "foo",
    "password": "bar",
    "email": "foo@bar"
}

Response
{
    "username": "foo",
    "email": "foo@bar",
    "roles": [
        "ROLE_USER"
    ]
}
```