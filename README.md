
# userService

Authentication and authorization service for the e-commerce platform
(userService, productService, PaymentService). It issues JWT access tokens
through OAuth2 / OpenID Connect using Spring Authorization Server.

## What it does
- User signup with BCrypt-hashed passwords
- Roles (`ADMIN`, `USER`) stored per user and returned in the JWT `roles` claim
- OAuth2 / OpenID Connect authorization server with JPA-backed clients and authorizations
- Domain exceptions mapped to HTTP responses (for example 409 on a duplicate email)

## Endpoints
| Method | Path | Notes |
  |---|---|---|
| POST | `/users/signup` | Public. Body: `name`, `email`, `password` |
| GET | `/.well-known/openid-configuration` | OIDC discovery, issuer `http://localhost:9091` |
| GET | `/oauth2/authorize` | Authorization endpoint |
| POST | `/oauth2/token` | Token endpoint |
| GET | `/oauth2/jwks` | Public signing keys |
| GET | `/login` | Form login used by the authorization code flow |

## Tech
Java 17, Spring Boot 3.4.x, Spring Security with Spring Authorization Server,
Spring Data JPA, MySQL.

## Run
1. Start MySQL on port 3306 and create the database `userService`.
2. Provide the datasource username and password through environment variables
   or a local, uncommitted `application.properties`.
3. `./mvnw spring-boot:run` (the service starts on port 9091).

## Getting a token
An OAuth2 client must be registered in the `client` table before tokens can be
requested. Use the Authorization Code flow, for example from Postman:
- Authorization URL: `http://localhost:9091/oauth2/authorize`
- Token URL: `http://localhost:9091/oauth2/token`
- Client id and secret: those of your registered client
- Callback URL: the redirect URI registered for that client
- Scope: `openid profile`

Log in with the email and password of a signed-up user. The access token carries
the user's roles in the `roles` claim.
