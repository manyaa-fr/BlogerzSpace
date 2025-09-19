# Blogera

Blogera is a lightweight, Spring Boot-based blogging REST API with JWT authentication and role-based access control. It enables users to securely create, manage, and view blog posts through a set of well-defined endpoints for user registration, login, logout, and blog management. The API is designed for scalability, security, and ease of use.

## Features

- **User Authentication**: Secure signup and login with JWT token generation.
- **Secure Logout**: Blacklist JWT tokens on logout to prevent further use.
- **Blog Creation**: Authenticated users can create blog posts with basic Markdown support.
- **Author-Specific Control**: Only the post author can edit or delete their blogs.
- **User Blogs**: View all blogs by a specific user via `/{username}`.
- **Persistent Storage**: Uses PostgreSQL for reliable data storage.
- **Role-Based Access**: Ensures secure access to endpoints with appropriate permissions.

## API Endpoints

| Method | Endpoint                     | Description                                         |
|--------|------------------------------|-----------------------------------------------------|
| `POST` | `/auth/signup`              | Register a new user                                 |
| `POST` | `/auth/login`               | Log in and receive a JWT token                     |
| `POST` | `/auth/logout`              | Log out and blacklist the JWT token                |
| `GET`  | `/blogs`                    | Retrieve all blog posts                            |
| `POST` | `/blogs/post`               | Create a new blog post (requires JWT, USER role)   |
| `PUT`  | `/blogs/update/{id}`        | Update a blog post (requires JWT, author only)     |
| `DELETE` | `/blogs/delete/{id}`        | Delete a blog post (requires JWT, author only)     |
| `GET`  | `/{username}`               | Get all blog posts by a specific user              |

## Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL
- Git

## Setup Instructions

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/manyaa-fr/blogerzSpace.git
   cd blog-app
   ```

2. **Create a PostgreSQL Database**:

   Create a database named `blogera` in PostgreSQL:

   ```sql
   CREATE DATABASE blogera;
   ```

3. **Configure Database Credentials**:

   Update the `application.properties` file in `src/main/resources` with your PostgreSQL credentials:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/blogera
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.datasource.driver-class-name=org.postgresql.Driver

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   spring.h2.console.enabled=false
   ```

4. **Run the Application**:

   Use Maven to start the Spring Boot application:

   ```bash
   mvn spring-boot:run
   ```

## Usage

1. **Register and Authenticate**:
   - Use the `/auth/signup` endpoint to create a new user account.
   - Use the `/auth/login` endpoint to log in and receive a JWT token.

2. **Secure Logout**:
   - Use the `/auth/logout` endpoint with the JWT token in the `Authorization` header to blacklist the token, preventing further use.

3. **Access Secured Endpoints**:
   - Include the JWT token in the `Authorization` header as `Bearer <token>` for protected routes (e.g., creating, updating, or deleting blog posts).

4. **Blog Management**:
   - Create blog posts with basic Markdown formatting (e.g., headers, bold, italic) using the `/blogs/post` endpoint.
   - Only the author of a blog post can modify or delete it via `/blogs/update/{id}` or `/blogs/delete/{id}`.
   - View all blogs (`/blogs`), or blogs by a specific user (`/{username}`).

## New Functionalities

**JWT Blacklist for Logout**:
   - A `BlacklistedToken` entity and repository store invalidated JWT tokens.
   - The `/auth/logout` endpoint adds the token to the blacklist, ensuring it cannot be used for subsequent requests.
   - The `JwtAuthenticationFilter` checks each request's token against the blacklist, rejecting blacklisted tokens with a `401 Unauthorized` response.

## Implementation Details

### JWT Blacklist
- **Entity**: `BlacklistedToken.java` stores invalidated tokens in a PostgreSQL table.
- **Repository**: `TokenBlacklistRepository.java` provides methods to check and save blacklisted tokens.
- **Filter**: `JwtAuthenticationFilter.java` checks tokens against the blacklist before processing requests.
- **Endpoint**: `/auth/logout` extracts the JWT from the `Authorization` header and adds it to the blacklist.


## Testing the Flow

1. **Login**: Send a `POST` request to `/auth/login` to obtain a JWT token.
2. **Create Post**: Use the token in the `Authorization` header to create a post via `POST /blogs/post`.
3. **Logout**: Send a `POST` request to `/auth/logout` with the token to blacklist it.
4. **Test Blacklist**: Attempt to use the blacklisted token for `POST /blogs/post`. It should fail with a `401 Unauthorized` response.

## Tech Stack

- **Spring Boot**: Backend framework for building the REST API.
- **Spring Security + JWT**: For authentication, authorization, and token blacklisting.
- **PostgreSQL**: Relational database for persistent storage.
- **Maven**: Dependency management and build tool.

## Contributing

Contributions are welcome! Please fork the repository, create a feature branch, and submit a pull request with your changes. Ensure your code follows the project’s coding standards and includes appropriate tests.

## Future Enhancements

- Add support for rich media (images, videos) in blog posts.
- Implement comment functionality for blog posts.
- Add API documentation using Swagger/OpenAPI.