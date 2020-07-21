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

### Przykładowa obsługa błędów
Logowanie
```
{
    "errorCode": 403,
    "error": "FORBIDDEN",
    "error": "Niepoprawne dane uwierzytelniające"
}
```
Wygasły token
```
{
    "errorCode": "400",
    "error": "BAD_REQUEST",
    "error": "JWT expired at 2020-07-21T22:06:43Z. Current time: 2020-07-21T23:36:57Z, a difference of 5414657 milliseconds.  Allowed clock skew: 0 milliseconds."
}
```
Niepoprawny obiekt
```
{
    "errorCode": 400,
    "errorName": "BAD_REQUEST",
    "errorMessage": "Object validation failed. Check fields errors.",
    "fieldsErrorList": [
        "Password cannot be null",
        "Email cannot be null",
        "Username cannot be null"
    ]
}
```