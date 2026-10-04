# Review 1 Demonstration Checklist

1. Run the application.
2. Register a user.
3. Login.
4. Add a goal.
5. Add tasks under the goal.
6. Update a task from Pending to Completed.
7. Show the Progress tab.
8. Add a schedule.
9. Show data persists after restarting.
10. Explain:
   - Encapsulation: private fields + getters/setters
   - Inheritance: Goal/Task extend GoalItem
   - Polymorphism: GoalItem references and overridden getDisplayText()
   - Interface: Reminder / DeadlineReminder
   - Exception handling: InvalidTaskException + try/catch
   - Collections/Generics: List<Goal>, List<Task>, ArrayList
   - Multithreading: ReminderService implements Runnable
   - JDBC: PreparedStatement + CRUD
   - Transaction: commit()/rollback() in GoalDAO
