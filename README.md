# Spring JWT Boilerplate
Przykładowa aplikacja z zaimplementowanym uwierzytelnieniem z wykorzystaniem JWT (JSON Web Tokens).

### Technologie
* Spring Boot
* Spring Security
* Spring Data JPA
* H2 Database
* Json Web Tokens

### Endpointy

#### Logowanie
```
POST /auth/login

Request
{
    "username": "foo",
    "password": "bar"
}

Response
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmb28iLCJleHAiOjE2MTI2NTQ2Mzl9.BhwwnmeKkFVplC3iudqi7pATFxjpV9SEVMFqFa-09WEco4MvuD6MBPOKurFt8y8BpCvNSTr8sYRtxrxFEGS0Eg",
    "refreshToken": "FCglofZA0u6PxqZ29mqiQUvjOslNSVZCnPAqrS1Scg76DWuSSPNyZh412LU8bcUe8dx2OuZpQXMhIM0Q3uhNbnWHVKApeej64YvOP32BZumgI6E9Pt2zqaGrPYL30sUu"
}
```

#### Rejestracja
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

#### Odświeżanie tokena
```
POST /auth/token/refresh

Request
{
    "refreshToken": "FCglofZA0u6PxqZ29mqiQUvjOslNSVZCnPAqrS1Scg76DWuSSPNyZh412LU8bcUe8dx2OuZpQXMhIM0Q3uhNbnWHVKApeej64YvOP32BZumgI6E9Pt2zqaGrPYL30sUu"
}

Response
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmb28iLCJleHAiOjE2MTI2NTQ4MTh9.pH_jt4QTGBqLq49wzWFHBtmYGFt7d4n9vY7QoVIKHRCu3bHNpi9BmsjDn8g6sz2uEU_iizcQc99mfg_a9fvHdQ"
}
```

#### Wylogowywanie (usuwanie refresh tokena)
```
POST /auth/logout

Request
{
    "refreshToken": "FCglofZA0u6PxqZ29mqiQUvjOslNSVZCnPAqrS1Scg76DWuSSPNyZh412LU8bcUe8dx2OuZpQXMhIM0Q3uhNbnWHVKApeej64YvOP32BZumgI6E9Pt2zqaGrPYL30sUu"
}

Response
204 - NO CONTENT
```

#### Whoami [do debugowania]
```
GET /auth/me

Response
{
    "id": 2,
    "email": "foo@bar",
    "username": "foo",
    "password": "$2a$10$Hf97x6iEbzfGLHh2Y3VRpOud5OhgUppnbnKto3sVFV6cHPF5BrCXm",
    "roles": [
        {
            "name": "ROLE_USER"
        }
    ],
    "enabled": true,
    "authorities": [
        {
            "authority": "ROLE_USER"
        }
    ],
    "accountNonExpired": true,
    "credentialsNonExpired": true,
    "accountNonLocked": true
}
```


### Przykładowa obsługa błędów

#### Błędne dane logowania
```
{
    "errorCode": 403,
    "errorName": "FORBIDDEN",
    "errorMessage": "Incorrect login details"
}
```

#### Brak autoryzacji
```
{
    "errorCode": "401",
    "errorName": "UNAUTHORIZED",
    "errorMessage": "Full authentication is required to access this resource"
}
```

#### Brak uprawnień
```
{
    "errorCode": 403,
    "errorName": "FORBIDDEN",
    "errorMessage": "Access denied"
}
```

#### Użytkownik nie istnieje
```
{
    "errorCode": "401",
    "errorName": "UNAUTHORIZED",
    "errorMessage": "User not exist"
}
```

#### Walidacja obiektów
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

#### Wygasły token i inne wyjątki JWT [do debugowania]
```
{
    "errorCode": "400",
    "errorName": "BAD_REQUEST",
    "errorMessage": "JWT expired at 2020-07-21T22:06:43Z. Current time: 2020-07-21T23:36:57Z, a difference of 5414657 milliseconds.  Allowed clock skew: 0 milliseconds."
}
```