# Kaiburr Assessment 2025 – Task 1  
### Java Backend + REST API + MongoDB Atlas  
**Name:** Vishnu TR  
**Date:** October 2025  

---

##  Overview  
This project implements a REST API in **Java (Spring Boot)** that manages and executes shell commands (called “tasks”).  
Each task is stored in **MongoDB Atlas** and supports CRUD operations, search by name, and command execution with output logs and timestamps.  

---

##  Tech Stack  
- Java 23  
- Spring Boot 3.5.6  
- MongoDB Atlas (Cloud Database)  
- Maven  
- Postman (for API testing)  

---

##  Features  

| Endpoint | Method | Description |
|-----------|---------|-------------|
| `/tasks` | **PUT** | Create a new task |
| `/tasks` | **GET** | Get all tasks |
| `/tasks/{id}` | **GET** | Get a task by ID |
| `/tasks/{id}` | **DELETE** | Delete a task |
| `/tasks/find?name=...` | **GET** | Search tasks by name |
| `/tasks/{id}/execute` | **PUT** | Execute a task’s command and save the output |

---

##  MongoDB Configuration  

```properties
spring.data.mongodb.uri=mongodb+srv://vishnu:<Vishnu123@kaiburrcluster.mongodb.net/?retryWrites=true&w=majority
spring.data.mongodb.database=taskdb
server.port=8080

---

## 🧪 Example Task JSON  

```json
{
  "id": "123",
  "name": "Print Hello",
  "owner": "Vishnu",
  "command": "echo Hello World",
  "taskExecutions": [
    {
      "startTime": "2025-10-15T14:45:21.276Z",
      "endTime": "2025-10-15T14:45:22.276Z",
      "output": "Hello World"
    },
    {
      "startTime": "2025-10-15T14:47:09.276Z",
      "endTime": "2025-10-15T14:47:09.376Z",
      "output": "Hello World"
    }
  ]
}

src/
 └── main/java/com/kaiburr/taskmanager/
     ├── Task.java
     ├── TaskExecution.java
     ├── TaskRepository.java
     └── TaskController.java

