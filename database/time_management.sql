-- The Java application creates these tables automatically.
-- This file is provided for Review 1 documentation/reference.

CREATE TABLE users(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password_hash TEXT NOT NULL
);

CREATE TABLE goals(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    description TEXT,
    start_date TEXT,
    target_date TEXT,
    priority TEXT,
    category TEXT,
    status TEXT,
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE tasks(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    goal_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    description TEXT,
    priority TEXT,
    deadline TEXT,
    estimated_minutes INTEGER,
    status TEXT,
    FOREIGN KEY(goal_id) REFERENCES goals(id)
);

CREATE TABLE schedules(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    task_id INTEGER NOT NULL,
    schedule_date TEXT NOT NULL,
    start_time TEXT NOT NULL,
    end_time TEXT NOT NULL,
    FOREIGN KEY(task_id) REFERENCES tasks(id)
);
