import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Task class
class Task {
    private String description;
    private String startTime;
    private String endTime;
    private int priority;

    public Task(String description, String startTime, String endTime, int priority) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", priority=" + priority +
                '}';
    }
}

// TaskManager class
class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task added successfully.");
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            System.out.println("Task removed successfully.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + ": " + tasks.get(i));
            }
        }
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        boolean exit = false;

        while (!exit) {
            System.out.println("Astronaut Scheduler:");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. View Tasks");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter start time: ");
                    String startTime = scanner.nextLine();
                    System.out.print("Enter end time: ");
                    String endTime = scanner.nextLine();
                    System.out.print("Enter priority (1-5): ");
                    int priority = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    taskManager.addTask(new Task(description, startTime, endTime, priority));
                    break;

                case 2:
                    System.out.print("Enter task index to remove: ");
                    int index = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    taskManager.removeTask(index);
                    break;

                case 3:
                    taskManager.viewTasks();
                    break;

                case 4:
                    exit = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
        System.out.println("Scheduler closed.");
    }
}