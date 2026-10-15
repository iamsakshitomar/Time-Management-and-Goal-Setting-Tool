# FocusFlow — Time Management and Goal Setting Tool

**Department:** CSE  
**Section:** 4  
**Project type:** Java GUI-based application

## Team members
- Sakshi — 25scse1010209
- Aanchal Sinha — 25scse1010918
- Shruti Shree — 25scse1010919
- Palak Mishra — 25SCSE1010813

## Project overview
A Java Swing desktop application that helps users create goals, break them into tasks, assign priorities and deadlines, schedule activities, and track progress.

## Features
- User registration and login
- Goal management (add, view, update, delete)
- Task management (add, view, update, delete)
- Task scheduling
- Goal progress calculation
- Deadline reminder background thread
- SQLite database storage
- JDBC with `PreparedStatement`
- Transaction example using `commit()` and `rollback()`

## Technology stack
- Java 17 or newer
- Java Swing
- JDBC
- SQLite
- Maven

## Requirements
1. Install JDK 17 or newer.
2. Install Maven.
3. Internet access is needed the first time Maven downloads the SQLite JDBC library.

## How to run in VS Code
1. Extract the ZIP.
2. Open the extracted project folder in VS Code.
3. Allow Maven to load dependencies.
4. Open the VS Code terminal in the project folder.
5. Run:
   ```bash
   mvn clean compile
   mvn exec:java
   ```
6. On the login window, select **Register**, create an account, and then log in.

The app creates `time_management.db` automatically in the project directory.

## Java concepts for Review 1
- Encapsulation through fields and accessor methods
- Inheritance and polymorphism through `GoalItem`, `Goal`, and `Task`
- Interface implementation through `Reminder`
- Exception handling and input validation
- Collections and generics through `List<Goal>` and `List<Task>`
- Multithreading through `ReminderService`
- JDBC CRUD operations using `PreparedStatement`
- Transaction handling with `commit()` and `rollback()`

## Before submission
Run and test each feature on your own machine and capture real screenshots for the presentation. Do not include real passwords or private credentials in screenshots or source control.

## Interface design

The desktop interface uses a modern navy, blue, and teal theme with a split-panel sign-in screen, clear form labels, a workspace dashboard, and dedicated Goals, Tasks, Schedule, and Progress tabs. It is built with Java Swing and does not require a separate UI framework.

