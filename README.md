# Battleship Game

```text
                                     # #  ( )
                                  ___#_#___|__
                              _  |____________|  _
                       _=====| | |            | | |==== _
                 =====| |.---------------------------. | |====
   <--------------------'   .  .  .  .  .  .  .  .   '--------------/
     \                                                             /
      \___________________________________________________________/
  wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww
   wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww 
```


This is a **University Project** from the 2022-2023 course. It implements a classic Battleship game using Java Swing for the GUI and a MySQL database for persistence.

## Project Description

The application is a desktop game where users can register, login, and play Battleship against an AI. It supports:
- User authentication (Register/Login).
- Statistics tracking (Wins, Games Played).
- Game persistence (Save/Load games).
- Interactive GUI with preparation and play phases.
- AI opponent.

## Prerequisites

- **Java Development Kit (JDK)**: Java 17 or higher recommended.
- **MySQL Server**: A local instance of MySQL is required.
- **External Libraries**:
    - `mysql-connector-java` (JDBC driver)
    - `gson` (Google Gson)
    - `jackson-databind` (Jackson)

> [!IMPORTANT]
> This project requires a MySQL database to function. You must set up the database before running the application.

## Setup

### 1. Database Setup

Create a database named `battleshipProject` and run the following SQL script to create the necessary tables:

```sql
CREATE DATABASE IF NOT EXISTS battleshipProject;
USE battleshipProject;

CREATE TABLE IF NOT EXISTS user (
    Username VARCHAR(255) PRIMARY KEY,
    Email VARCHAR(255) UNIQUE,
    Password VARCHAR(255),
    wins INT DEFAULT 0,
    gamesPlayed INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS game (
    GameName VARCHAR(255),
    Username VARCHAR(255),
    CreationDate VARCHAR(255),
    CurrentTime VARCHAR(255),
    JsonFile VARCHAR(255),
    GameState VARCHAR(50),
    hits VARCHAR(255),
    PRIMARY KEY (GameName),
    FOREIGN KEY (Username) REFERENCES user(Username) ON DELETE CASCADE
);
```

### 2. Configuration

Modify `files/config.json` to match your database credentials:

```json
{
  "db_user": "root",
  "db_password": "YOUR_PASSWORD",
  "db_ip": "localhost",
  "db_port": 3306,
  "db_name": "battleshipProject",
  "time": 1000
}
```

### 3. Dependencies

Ensure the following Jar files are added to your classpath/project dependencies:
- `mysql-connector-java-x.x.x.jar`
- `gson-x.x.x.jar`
- `jackson-databind-x.x.x.jar` (and dependencies: `jackson-core`, `jackson-annotations`)

## How to Run

1.  Open the project in IntelliJ IDEA (or your preferred IDE).
2.  Add the required JARs to the project library.
3.  Run the `main.Main` class.

## Disclaimer

This software was developed as part of a university course. It is provided "as is" without warranty of any kind.
