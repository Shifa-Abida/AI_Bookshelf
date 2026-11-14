# 📚 AI Bookshelf

**AI Bookshelf** is a backend application designed to organize and manage AI tools in one centralized platform. It allows users to store, retrieve, update, delete, and categorize AI tools through RESTful APIs.

The project is being developed using **Java and Spring Boot**, following a layered architecture and backend development best practices.

> 🚧 **Project Status: Under Development**
>
> The backend currently includes CRUD APIs, validation, centralized exception handling, tool-category relationships, pagination, sorting, category filtering, keyword search, and Spring Data JPA projections. Advanced Hibernate and database optimization topics are being implemented incrementally.

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
├── projection/
│   └── ToolSummaryProjection.java
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

### Phase 6: Pagination, Sorting & Category Filtering

Improved the GET all-tools API to handle larger collections efficiently and allow clients to narrow and organize results.

**1. Pagination**
- Used Spring Data's `Pageable` and `Page` abstractions.
- Added page and size query parameters.
- Returned page metadata such as `totalElements`, `totalPages`, `number`, and `size`.

**2. Sorting**
- Supports sorting through Spring's `sort` query parameter.
- Supports ascending and descending order for fields such as `name` and `id`.

**3. Category Filtering**
- Added the derived repository query `findByCategory_Name(...)`.
- Added service logic to retrieve tools for a selected category.
- Kept the category parameter optional, so the same endpoint can return all tools or filter by category.

**4. Pagination Parameter Validation**
- Rejects negative page numbers with `400 Bad Request`.
- Rejects page sizes less than or equal to zero with `400 Bad Request`.
- Returns a clear validation message for invalid pagination parameters.

**Example requests**
```http
GET /api/tools?page=0&size=5
GET /api/tools?page=0&size=5&sort=name,asc
GET /api/tools?page=0&size=5&category=Coding
GET /api/tools?page=0&size=5&sort=name,asc&category=Coding
```

### Phase 7: Search Functionality

Implemented keyword-based search across AI tool names, descriptions, and category names.

**1. Repository Search**

- Added a Spring Data JPA derived query using `Containing` and `IgnoreCase`.
- Searches across the tool name, description, and related category name.
- Uses `OR` conditions, so a match in any of the three fields is returned.

**2. Service Layer**

- Added `searchTools()` to pass the search keyword to the repository.
- Maps matching `ToolEntity` records to `ToolResponseDto`.
- Preserves pagination metadata using `Page.map()`.

**3. Search API**

Added the endpoint:

```http
GET /api/tools/search?keyword=coding&page=0&size=5
```

The endpoint supports:
- Partial keyword matching.
- Case-insensitive matching.
- Searching tool names, descriptions, and category names.
- Pagination and sorting through Spring Data's `Pageable`.

**4. Search Input Validation & Exception Handling**

- Rejects blank search keywords with `400 Bad Request`.
- Trims leading and trailing whitespace from keywords.
- Rejects negative page numbers and page sizes less than or equal to zero.
- Handles missing required query parameters through `MissingServletRequestParameterException` in `GlobalExceptionHandler`, returning `400 Bad Request` instead of an internal server error.

**Example requests**

```http
GET /api/tools/search?keyword=cursor&page=0&size=5
GET /api/tools/search?keyword=coding&page=0&size=5&sort=name,asc
GET /api/tools/search?keyword=research&page=0&size=5
```

---

### Phase 8.1: Spring Data JPA Projections

Implemented and tested three projection approaches to retrieve selected fields without loading complete tool entities for these read operations.

**1. Interface-Based Projection**

- Created `ToolSummaryProjection` with getters for tool name, website URL, and category name.
- Added a JPQL query with aliases matching the projection getter properties.
- Implemented the repository, service, and controller layers.

Endpoint:

```http
GET /api/tools/summaries
```

**2. Class-Based / DTO Projection**

- Created `ToolSummaryDto` as a Java record.
- Used a JPQL constructor expression (`SELECT NEW`) to instantiate the DTO directly from selected query values.
- Implemented the repository, service, and controller layers.

Endpoint:

```http
GET /api/tools/summaries/dto
```

**3. Dynamic Projection**

- Added a generic repository method accepting `Class<T>`.
- Added a service method that accepts the requested projection type.
- Demonstrated selecting a projection type at runtime.

Endpoint:

```http
GET /api/tools/dynamic-summary?keyword=ChatGPT
```

**Testing**

- Successfully tested all three projection endpoints using Postman.
- Verified that the responses contain the requested summary fields: tool name, website URL, and category name.

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
| GET | `/api/tools` | Retrieve tools with optional pagination, sorting, and category filtering |
| GET | `/api/tools/search` | Search tools by name, description, or category with pagination and sorting |
| GET | `/api/tools/summaries` | Retrieve tool summaries using interface-based projection |
| GET | `/api/tools/summaries/dto` | Retrieve tool summaries using DTO projection |
| GET | `/api/tools/dynamic-summary` | Search tool summaries using dynamic projection |
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

### Pagination, Sorting & Filtering

The GET all-tools endpoint accepts these optional query parameters:

| Parameter | Description | Example |
|---|---|---|
| `page` | Zero-based page index | `page=0` |
| `size` | Maximum number of records per page | `size=5` |
| `sort` | Field and direction | `sort=name,asc` |
| `category` | Category name to filter by | `category=Coding` |

Example:

```http
GET http://localhost:8080/api/tools?page=0&size=5&sort=name,asc&category=Coding
```

Invalid pagination values return `400 Bad Request`. A valid query for a category with no matching tools returns an empty page.

### Search API

The search endpoint accepts these query parameters:

| Parameter | Description | Example |
|---|---|---|
| `keyword` | Required search text | `keyword=coding` |
| `page` | Zero-based page index | `page=0` |
| `size` | Maximum number of records per page | `size=5` |
| `sort` | Field and sort direction | `sort=name,asc` |

Example:

```http
GET http://localhost:8080/api/tools/search?keyword=coding&page=0&size=5&sort=name,asc
```

Search is partial and case-insensitive, and checks tool name, description, and category name. Missing or blank keywords and invalid pagination values return `400 Bad Request`.

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

CRUD operations, validation rules, duplicate checks, category relationships, pagination, sorting, category filtering, keyword search, and invalid request parameters have been tested using Postman.

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
- Retrieving results page by page.
- Sorting results in ascending and descending order.
- Filtering tools by category.
- Combining pagination, sorting, and category filtering.
- Returning empty results for categories with no matches.
- Handling out-of-range page requests.
- Rejecting negative page numbers and non-positive page sizes.
- Searching by full and partial keywords.
- Searching tool names, descriptions, and category names.
- Verifying case-insensitive search and whitespace trimming.
- Combining search with pagination and sorting.
- Retrieving selected tool fields using interface-based projection.
- Retrieving selected tool fields using DTO projection and JPQL constructor expressions.
- Searching tool summaries using dynamic projection.
- Handling empty results, blank keywords, and missing keywords.

---

## 🚀 Future Enhancements

The project is actively under development. Planned improvements include:

- [ ] Advanced search enhancements beyond basic keyword search.
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
| Phase 6 | Pagination, Sorting & Filtering | Completed |
| Phase 7 | Search Functionality | Completed |
| Phase 8.1 | Spring Data JPA Projections | Completed |
| Phase 8.2 onward | Hibernate Entity Lifecycle, Persistence Context & Database Optimization | In Progress / Planned |
| Future Enhancements | Security, Testing, Cloud & Deployment | Planned |

---

## 👩‍💻 Developer

**Shifa Abida**

- GitHub: [Shifa-Abida](https://github.com/Shifa-Abida)
- LinkedIn: [Shifa Abida](https://www.linkedin.com/in/shifa-a-7b26ba2a6/)

---

⭐ **AI Bookshelf is an ongoing learning and development project. More features and improvements will be added as development progresses.**