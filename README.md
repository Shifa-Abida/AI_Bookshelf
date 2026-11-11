# 📚 AI Bookshelf

**AI Bookshelf** is a backend application designed to organize and manage AI tools in one centralized platform. It allows users to store, retrieve, update, delete, and categorize AI tools through RESTful APIs.

The project is being developed using **Java and Spring Boot**, following a layered architecture and backend development best practices.

> 🚧 **Project Status: Under Development**
>
> The initial backend implementation, including CRUD operations, validation, exception handling, and database relationships, has been completed. Additional features and improvements are currently in progress.

---

## 🎯 Project Objectives

- Build a structured platform to organize AI tools.
- Implement RESTful APIs using Java and Spring Boot.
- Design and manage relational database relationships.
- Apply input validation and centralized exception handling.
- Develop a scalable and maintainable backend architecture.
- Gradually introduce advanced backend development concepts.

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming language |
| Spring Boot | Backend application framework |
| Spring Data JPA | Database interaction |
| Hibernate | ORM and entity management |
| MySQL | Relational database |
| Maven | Dependency management and build tool |
| Lombok | Reduce boilerplate code |
| ModelMapper | Entity and DTO mapping |
| Jakarta Bean Validation | Request validation |
| Postman | API testing |
| Git & GitHub | Version control |

---

## 🏗️ Project Architecture

The application follows a layered architecture to separate responsibilities and maintain clean, organized code.

```text
src/main/java/com/Organise/AI_Bookshelf/
│
├── config/
│   └── ModelMapperConfig.java
│
├── controller/
│   └── ToolController.java
│
├── dto/
│   ├── ErrorResponseDto.java
│   ├── ToolRequestDto.java
│   └── ToolResponseDto.java
│
├── entity/
│   ├── ToolEntity.java
│   └── CategoryEntity.java
│
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ToolNotFoundException.java
│   └── ToolAlreadyExistsException.java
│
├── repository/
│   ├── ToolRepository.java
│   └── CategoryRepository.java
│
├── service/
│   └── ToolService.java
│
└── AiBookshelfApplication.java
```

### Architecture Layers

**1. Controller Layer**
- Handles incoming HTTP requests.
- Exposes REST API endpoints.
- Passes requests to the service layer.
- Returns appropriate HTTP responses.

**2. Service Layer**
- Contains business logic.
- Handles tool creation, retrieval, updating, and deletion.
- Manages category creation and assignment.
- Performs duplicate checks.

**3. Repository Layer**
- Communicates with the database.
- Uses Spring Data JPA.
- Performs database operations through `JpaRepository`.

**4. Entity Layer**
- Represents database tables.
- Defines relationships between tools and categories.

**5. DTO Layer**
- Separates API request and response structures from database entities.
- Provides structured data transfer.

**6. Exception Layer**
- Handles application exceptions centrally.
- Returns meaningful error messages and HTTP status codes.

---

## ✅ Implemented Features

### Phase 1: Project Setup & Database Configuration

- Created the Spring Boot application.
- Configured Maven dependencies.
- Connected the application to MySQL.
- Configured database properties using environment variables.
- Established the initial project structure.

### Phase 2: Entity, Repository & Database Persistence

- Created `ToolEntity` to represent AI tools.
- Implemented `ToolRepository` using `JpaRepository`.
- Configured entity identifiers using `@Id` and `@GeneratedValue`.
- Implemented database persistence using Spring Data JPA.
- Verified database operations.

### Phase 3: CRUD REST APIs

Implemented RESTful APIs for managing AI tools.

| HTTP Method | Operation | Description |
|---|---|---|
| POST | Create Tool | Add a new AI tool |
| GET | Get All Tools | Retrieve all stored tools |
| GET | Get Tool by ID | Retrieve a specific tool |
| PUT | Update Tool | Update existing tool details |
| DELETE | Delete Tool | Remove a tool |

Additional implementation:
- Created request and response DTOs.
- Configured ModelMapper.
- Implemented Controller and Service layers.
- Tested API endpoints using Postman.

### Phase 4: Validation & Exception Handling

Implemented request validation and centralized exception handling.

**Input Validation**

Used Jakarta Bean Validation annotations:

- `@NotBlank` – Prevents empty or blank values.
- `@Size` – Restricts minimum and maximum input length.
- `@Pattern` – Validates input against a specified pattern.

**Custom Exception Handling**

Implemented:
- `ToolNotFoundException`
- `GlobalExceptionHandler`
- `ErrorResponseDto`

Handled:
- Invalid request data.
- Missing tool records.
- Internal server errors.

**Structured Error Responses**

Errors are returned in a consistent JSON format.

Example:

```json
{
  "error": "Not Found",
  "message": "Tool not found with id: 100",
  "status": 404,
  "timestamp": "2026-11-11T19:05:47"
}
```

### Phase 5: Entity Relationships & Database Design

Implemented relational database design to connect AI tools with their respective categories.

**1. Category Management**

Created:
- `CategoryEntity`
- `CategoryRepository`

**2. One-to-Many Relationship**

One category can contain multiple AI tools.

Implemented using:

```java
@OneToMany(mappedBy = "category")
private List<ToolEntity> tools = new ArrayList<>();
```

**3. Many-to-One Relationship**

Each AI tool belongs to one category.

Implemented using:

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "category_id", nullable = false)
private CategoryEntity category;
```

**4. Automatic Category Creation**

Implemented `getOrCreateCategory()` in the service layer.

- Checks whether a category already exists.
- Reuses an existing category when available.
- Creates a new category when it does not exist.
- Assigns the category to the corresponding tool.

**5. Duplicate Validation**

Implemented duplicate checks using Spring Data JPA derived query methods:

```java
boolean existsByName(String name);

boolean existsByWebsiteUrl(String websiteUrl);
```

Created `ToolAlreadyExistsException` to prevent duplicate tool names and website URLs.

Duplicate entries return:

```text
409 Conflict
```

**6. Updated CRUD Operations**

Updated the service layer to support category relationships across create, read, update, and delete operations.

**7. Improved Delete Response**

The DELETE endpoint returns a confirmation message:

```text
Tool deleted successfully.
```

---

## 🗄️ Database Design

The application currently uses two main entities.

### Categories Table

| Column | Description |
|---|---|
| id | Primary key |
| name | Unique category name |

### Tools Table

| Column | Description |
|---|---|
| id | Primary key |
| name | AI tool name |
| description | Tool description |
| category_id | Foreign key referencing categories |
| website_url | Official website URL |
| pricing | Pricing information |
| logo_url | Logo URL field |

### Entity Relationship

```text
       CATEGORIES
       ----------
       id (PK)
       name
           |
           | 1
           |
           | 
           | Many
           |
         TOOLS
         -----
         id (PK)
         name
         description
         category_id (FK)
         website_url
         pricing
         logo_url
```

**Relationship:** One Category → Many Tools.

---

## 🔌 API Endpoints

Base URL:

```text
http://localhost:8080/api/tools
```

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/tools` | Create a tool |
| GET | `/api/tools` | Retrieve all tools |
| GET | `/api/tools/{id}` | Retrieve a tool by ID |
| PUT | `/api/tools/{id}` | Update a tool |
| DELETE | `/api/tools/{id}` | Delete a tool |

### Example: Create an AI Tool

**POST** `/api/tools`

Request Body:

```json
{
  "name": "ChatGPT",
  "description": "AI-powered assistant",
  "category": "AI",
  "websiteUrl": "https://chatgpt.com",
  "pricing": "Freemium"
}
```

Example Response:

```json
{
  "id": 1,
  "name": "ChatGPT",
  "description": "AI-powered assistant",
  "category": "AI",
  "websiteUrl": "https://chatgpt.com",
  "pricing": "Freemium"
}
```

### HTTP Status Codes

| Status Code | Meaning |
|---|---|
| 200 | Request successful |
| 400 | Bad request / validation failure |
| 404 | Resource not found |
| 409 | Duplicate resource conflict |
| 500 | Internal server error |

---

## ⚙️ Getting Started

### Prerequisites

Make sure you have the following installed:

- Java JDK
- Maven
- MySQL Server
- IntelliJ IDEA or another Java IDE
- Postman

### 1. Clone the Repository

```bash
git clone https://github.com/Shifa-Abida/AI_Bookshelf.git
```

### 2. Open the Project

Open the cloned project in IntelliJ IDEA.

### 3. Configure MySQL

Create a MySQL database for the application.

```sql
CREATE DATABASE ai_bookshelf;
```

### 4. Configure Environment Variables

The application uses environment variables for database configuration.

Configure the following:

```text
DB_URL=jdbc:mysql://localhost:3306/ai_bookshelf
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
```

Do not commit actual database credentials to GitHub.

### 5. Run the Application

Run `AiBookshelfApplication.java` from your IDE, or use Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The application will run at:

```text
http://localhost:8080
```

---

## 🧪 API Testing

All implemented CRUD operations, validation rules, duplicate checks, and category relationships have been tested using Postman.

Testing includes:

- Creating AI tools.
- Retrieving all tools.
- Retrieving tools by ID.
- Updating tool information.
- Deleting tools.
- Automatic category creation.
- Reusing existing categories.
- Preventing duplicate tool names.
- Preventing duplicate website URLs.
- Handling invalid requests and missing records.

---

## 🚀 Future Enhancements

The project is actively under development. Planned improvements include:

- [ ] Pagination and sorting.
- [ ] Advanced search and filtering.
- [ ] Improved database query optimization.
- [ ] Spring Security integration.
- [ ] JWT-based authentication and authorization.
- [ ] User management.
- [ ] AI tool logo retrieval and management.
- [ ] AWS S3 integration for file storage.
- [ ] API documentation using Swagger / OpenAPI.
- [ ] Unit and integration testing using JUnit and Mockito.
- [ ] Docker containerization.
- [ ] Cloud deployment.
- [ ] Caching and performance optimization.

---

## 📌 Current Development Status

| Phase | Description | Status |
|---|---|---|
| Phase 1 | Project Setup & Database Configuration | Completed |
| Phase 2 | Entity, Repository & Persistence | Completed |
| Phase 3 | CRUD REST APIs | Completed |
| Phase 4 | Validation & Exception Handling | Completed |
| Phase 5 | Entity Relationships & Database Design | Completed |
| Phase 6 | Pagination, Sorting & Filtering | Planned |
| Future Phases | Security, Testing, Cloud & Deployment | Planned |

---

## 👩‍💻 Developer

**Shifa Abida**

- GitHub: [Shifa-Abida](https://github.com/Shifa-Abida)
- LinkedIn: [Shifa Abida](https://www.linkedin.com/in/shifa-a-7b26ba2a6/)

---

⭐ **AI Bookshelf is an ongoing learning and development project. More features and improvements will be added as development progresses.**