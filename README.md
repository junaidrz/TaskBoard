# Task Board Application

This is a simple REST service for managing task lists and tasks.

## Prerequisites

- Java 17
- Maven

## Running the Application

1. Clone the repository.
2. Build the project using `mvn clean install`
3. Run `mvn spring-boot:run`.

## API Endpoints

- **GET /lists**: Get all lists.
- **POST /lists**: Create a new list.
- **DELETE /lists/{id}**: Delete a list by ID.
- **POST /tasks/{listId}**: Add a new task to a list.
- **PUT /tasks/{id}**: Update a task by ID.
- **DELETE /tasks/{id}**: Delete a task by ID.
- **POST /tasks/{taskId}/move/{newListId}**: Move a task to a different list.

## Database

The application uses an in-memory H2 database. You can access the H2 console at `http://localhost:8080/h2-console`.
The database url is : `jdbc:h2:mem:taskboard`

## SWAGGER

The swagger available at : `http://localhost:8080/swagger-ui/index.html`

## Docker 
1. Build docker image with `docker-compose build`
2. Run docker with `docker-compose up`
3. Access the TaskBoard Application at : `http://localhost:8080`
4. Access the H2 database console at : `http://localhost:8082`
5. Access the swagger at : `http://localhost:8080/swagger-ui/index.html`

## Testing
Run `mvn test` to execute the unit tests.

