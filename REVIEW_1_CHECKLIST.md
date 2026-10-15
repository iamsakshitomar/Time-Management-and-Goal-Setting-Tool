# Review 1 Checklist

Team: Sakshi, Aanchal Sinha, Shruti Shree, Palak Mishra  
Department: CSE | Section: 4

## Before submitting
- [ ] Extract the ZIP and open the folder in VS Code.
- [ ] Run `mvn clean compile`.
- [ ] Run `mvn exec:java`.
- [ ] Register a test account and log in.
- [ ] Test adding, updating, and deleting goals.
- [ ] Test adding, updating, and deleting tasks.
- [ ] Test schedule creation.
- [ ] Test progress display.
- [ ] Restart the app and verify that data persists.
- [ ] Capture real screenshots for the presentation.
- [ ] Push the project to a public GitHub repository.
- [ ] Open the repository link in a private/incognito browser to confirm it is public.

## Viva topics
- Encapsulation: fields and getters/setters
- Inheritance: `Goal` and `Task` extend `GoalItem`
- Polymorphism: overridden `getDisplayText()`
- Interface: `Reminder` and `DeadlineReminder`
- Exception handling: `InvalidTaskException` and validation
- Collections/Generics: `List<Goal>`, `List<Task>`
- Multithreading: `ReminderService implements Runnable`
- JDBC: `PreparedStatement` and CRUD operations
- Transactions: `commit()` and `rollback()` in `GoalDAO`
