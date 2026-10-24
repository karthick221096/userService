# userService

Learning to create a user authentication service using java

### What this does
* User signup with bcrypt password hashing
* User login and logout with token management
* Token validation for other services to verify logged in users
* OAuth2 Authorization Server to issue JWT tokens
* Role based access for users

### Requirements
* User signup, login, logout apis
* Token validation api used by productService and paymentService
* BCrypt password encoding
* OAuth2 jwt token issuing
* Role management for users

### How to run
* Make sure mysql is running on port 3306
* Create database userService in mysql
* Run UserServiceApplication.java from intellij
* Service starts on port 9091
