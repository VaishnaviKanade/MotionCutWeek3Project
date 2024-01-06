import java.io.*;
import java.util.*;

class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class Expense {
    String date;
    String category;
    double amount;

    Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }
}

public class App {
    private static List<User> users = new ArrayList<>();
    private static List<Expense> expenses = new ArrayList<>();
    private static final String USERS_FILE = "users.txt";
    private static final String EXPENSES_FILE = "expenses.txt";

    public static void main(String[] args) {
        loadUsers();
        loadExpenses();
        showMenu();
    }

    private static void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Expense Tracker Menu:");
            System.out.println("1. Register User");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    logIn();
                    break;
                case 3:
                    saveData();
                    System.out.println("Exiting Expense Tracker. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("User Registration:");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User newUser = new User(username, password);
        users.add(newUser);

        System.out.println("User registered successfully!");
    }

    private static void logIn() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("User Login:");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                System.out.println("Login successful!");
                showExpenseMenu(user);
                return;
            }
        }

        System.out.println("Invalid username or password. Please try again.");
    }

    private static void showExpenseMenu(User user) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Expense Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Log Out");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addExpense(user);
                    break;
                case 2:
                    viewExpenses(user);
                    break;
                case 3:
                    saveData();
                    System.out.println("Logging out. Goodbye, " + user.username + "!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addExpense(User user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Add Expense:");
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter the time as 24hr watch: ");
        String category = scanner.nextLine();
        System.out.print("Enter the amount: ");
        double amount = scanner.nextDouble();

        Expense newExpense = new Expense(date, category, amount);
        expenses.add(newExpense);

        System.out.println("Expense added successfully!");
    }

    private static void viewExpenses(User user) {
        System.out.println("Expense Listing:");
        for (Expense expense : expenses) {
            System.out.println("Date: " + expense.date + ", Category: " + expense.category + ", Amount: $" + expense.amount);
        }
    }

    private static void saveData() {
        try (ObjectOutputStream usersStream = new ObjectOutputStream(new FileOutputStream(USERS_FILE));
             ObjectOutputStream expensesStream = new ObjectOutputStream(new FileOutputStream(EXPENSES_FILE))) {

            usersStream.writeObject(users);
            expensesStream.writeObject(expenses);

            System.out.println("Data saved successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadUsers() {
        try (ObjectInputStream usersStream = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (ArrayList<User>) usersStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Ignore if file doesn't exist or error occurs during reading
        }
    }

    private static void loadExpenses() {
        try (ObjectInputStream expensesStream = new ObjectInputStream(new FileInputStream(EXPENSES_FILE))) {
            expenses = (ArrayList<Expense>) expensesStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Ignore if file doesn't exist or error occurs during reading
        }
    }
}
