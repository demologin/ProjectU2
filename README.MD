# Quest App
A lightweight web application for playing interactive text-based quests ("Choose Your Own Adventure" style).
Built with Java Servlets and deployed on Apache Tomcat.

## Tech Stack
* **Language:** Java 21
* **Build Tool:** Maven
* **Application Server:** Apache Tomcat 10.1.x (Jakarta EE 10 / Servlet 6.0)
* **Templating / Frontend:** JSP, JSTL, HTML 5, CSS 3, Bootstrap 5

## Prerequisites
Before running the project, make sure you have the following installed:
1. [JDK 21](https://www.oracle.com/java/technologies/downloads/)
2. [Apache Tomcat 10.1+](https://tomcat.apache.org/download-10.cgi)
3. [Apache Maven 3.9+](https://maven.apache.org/download.cgi)

## Installation and Running
### 1. Clone the repository
```bash
git clone https://github.com/igrn/quest-app.git
cd quest-app
```

### 2. Build the project
Build the `.war` archive using Maven:
```bash
mvn clean package
```
The compiled file `quest-app.war` will appear in the `target/` directory.

### 3. Deploy to Tomcat
1. Copy `target/quest-app.war` into the `webapps/` folder of your Tomcat installation directory.
2. Start the server:
   * **Linux / macOS:** `./bin/startup.sh`
   * **Windows:** `bin\startup.bat`
3. Open the application in your browser at: `http://localhost:8080/quest-app/`

## Main Endpoints (Routes)
| Method | URL          | Description              |
|:-------|:-------------|:-------------------------|
| `GET`  | `/`          | Application home page    |
| `POST` | `/signup`    | Register a new user      |
| `GET`  | `/login`     | User login page          |
| `GET`  | `/users?id=` | User profile page        |
| `GET`  | `/quest?id=` | Quest exposition page    |
| `POST` | `/quest?id=` | Start a quest            |
| `GET`  | `/scene?id=` | Show a quest scene       |

## Project Structure
```text
├── src/
│   ├── main/
│   │   ├── java/...
│   │   │   ├── config/       # Configuration and DI-container
│   │   │   ├── controller/   # Servlets (HTTP request handling)
│   │   │   ├── entity/       # Domain entities
│   │   │   ├── exception/    # Custom exceptions
│   │   │   ├── filter/       # Filters (authentication, exception)
│   │   │   ├── repository/   # Database access
│   │   │   ├── service/      # Business logic
│   │   │   └── util/         # Utility classes
│   │   ├── resources/        # Property files and sample resources
│   │   └── webapp/
│   │       ├── WEB-INF/      # JSP views (hidden from direct access)
│   │       └── assets/       # CSS, images
└── pom.xml                   # Dependencies and build configuration
```

## Author
* **Igor Zelenin** — [GitHub](https://github.com/igrn)
