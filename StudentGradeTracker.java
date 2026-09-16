/*
 * StudentGradeTracker.java
 * -------------------------------------------------
 * This is the main program file.
 * It shows a menu, and lets the user:
 *   1. Add a student
 *   2. View all students
 *   3. Calculate statistics (average, highest, lowest, pass/fail)
 *   4. Search for a student by ID
 *   5. Exit
 *
 * All student records are stored in an ArrayList<Student>.
 * -------------------------------------------------
 */

import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {

    // This list holds all the students entered by the user.
    private static ArrayList<Student> students = new ArrayList<>();

    // One Scanner is reused for all input in the whole program.
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Load 3 sample students so the program can be tested immediately.
        loadSampleData();

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readMenuChoice();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    calculateStatistics();
                    break;
                case 4:
                    searchStudent();
                    break;
                case 5:
                    running = false;
                    System.out.println("\nThank you for using Student Grade Tracker. Goodbye!");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please enter a number between 1 and 5.");
            }
        }

        scanner.close();
    }

    // ---------------------------------------------------------
    // Adds 3 sample students so the user can test the program
    // right away without typing anything.
    // ---------------------------------------------------------
    private static void loadSampleData() {
        students.add(new Student("101", "Arun", 85));
        students.add(new Student("102", "Priya", 92));
        students.add(new Student("103", "Rahul", 67));
    }

    // ---------------------------------------------------------
    // Prints the main menu.
    // ---------------------------------------------------------
    private static void printMenu() {
        System.out.println("\n===== STUDENT GRADE TRACKER =====");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Calculate Statistics");
        System.out.println("4. Search Student");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");
    }

    // ---------------------------------------------------------
    // Safely reads the menu choice. If the user types something
    // that is not a number, it returns -1 (an invalid choice)
    // instead of crashing the program.
    // ---------------------------------------------------------
    private static int readMenuChoice() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1; // will be caught by "default" in the switch
        }
    }

    // ---------------------------------------------------------
    // Feature 1: ADD STUDENT
    // ---------------------------------------------------------
    private static void addStudent() {
        System.out.println("\n--- Add Student ---");

        // ----- Student ID (must not be empty, must be unique) -----
        String id;
        while (true) {
            System.out.print("Enter Student ID: ");
            id = scanner.nextLine().trim();

            if (id.isEmpty()) {
                System.out.println("Student ID cannot be empty. Please try again.");
                continue;
            }
            if (findStudentById(id) != null) {
                System.out.println("A student with this ID already exists. Please enter a different ID.");
                continue;
            }
            break;
        }

        // ----- Student Name (must not be empty) -----
        String name;
        while (true) {
            System.out.print("Enter Student Name: ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Student name cannot be empty. Please try again.");
                continue;
            }
            break;
        }

        // ----- Marks (must be a number between 0 and 100) -----
        double marks = readValidMarks();

        // Create the Student object and add it to the list.
        Student newStudent = new Student(id, name, marks);
        students.add(newStudent);

        System.out.println("\nStudent added successfully!");
        System.out.println("Name: " + newStudent.getStudentName() +
                " | Marks: " + newStudent.getMarks() +
                " | Grade: " + newStudent.getGrade());
    }

    // ---------------------------------------------------------
    // Keeps asking until the user enters a valid mark
    // (a number between 0 and 100).
    // ---------------------------------------------------------
    private static double readValidMarks() {
        while (true) {
            System.out.print("Enter Marks (0-100): ");
            String input = scanner.nextLine().trim();

            try {
                double marks = Double.parseDouble(input);

                if (marks < 0 || marks > 100) {
                    System.out.println("Marks must be between 0 and 100. Please try again.");
                    continue;
                }
                return marks;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value for marks.");
            }
        }
    }

    // ---------------------------------------------------------
    // Feature 2: VIEW ALL STUDENTS
    // ---------------------------------------------------------
    private static void viewAllStudents() {
        System.out.println("\n--- All Students ---");

        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        printTableHeader();
        for (Student s : students) {
            printStudentRow(s);
        }
        System.out.println("------------------------------------------------");
    }

    private static void printTableHeader() {
        System.out.println("------------------------------------------------");
        System.out.printf("%-8s %-18s %-10s %-6s%n", "ID", "Name", "Marks", "Grade");
        System.out.println("------------------------------------------------");
    }

    private static void printStudentRow(Student s) {
        System.out.printf("%-8s %-18s %-10.1f %-6s%n",
                s.getStudentId(), s.getStudentName(), s.getMarks(), s.getGrade());
    }

    // ---------------------------------------------------------
    // Feature 3: CALCULATE STATISTICS
    // ---------------------------------------------------------
    private static void calculateStatistics() {
        System.out.println("\n--- Statistics / Summary Report ---");

        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        double total = 0;
        Student highestStudent = students.get(0);
        Student lowestStudent = students.get(0);
        int passCount = 0;
        int failCount = 0;

        for (Student s : students) {
            total += s.getMarks();

            if (s.getMarks() > highestStudent.getMarks()) {
                highestStudent = s;
            }
            if (s.getMarks() < lowestStudent.getMarks()) {
                lowestStudent = s;
            }
            if (s.isPassed()) {
                passCount++;
            } else {
                failCount++;
            }
        }

        double average = total / students.size();

        System.out.println("------------------------------------------------");
        System.out.println("Total Number of Students : " + students.size());
        System.out.printf("Average Marks             : %.2f%n", average);
        System.out.println("Highest Marks              : " + highestStudent.getMarks() +
                " (" + highestStudent.getStudentName() + ", ID: " + highestStudent.getStudentId() + ")");
        System.out.println("Lowest Marks                : " + lowestStudent.getMarks() +
                " (" + lowestStudent.getStudentName() + ", ID: " + lowestStudent.getStudentId() + ")");
        System.out.println("Number of Students Passed : " + passCount);
        System.out.println("Number of Students Failed  : " + failCount);
        System.out.println("------------------------------------------------");
    }

    // ---------------------------------------------------------
    // Feature 4: SEARCH STUDENT (by ID)
    // ---------------------------------------------------------
    private static void searchStudent() {
        System.out.println("\n--- Search Student ---");

        if (students.isEmpty()) {
            System.out.println("No student records available.");
            return;
        }

        System.out.print("Enter Student ID to search: ");
        String id = scanner.nextLine().trim();

        Student found = findStudentById(id);

        if (found == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println("\nStudent Found:");
            System.out.println("ID     : " + found.getStudentId());
            System.out.println("Name   : " + found.getStudentName());
            System.out.println("Marks  : " + found.getMarks());
            System.out.println("Grade  : " + found.getGrade());
        }
    }

    // ---------------------------------------------------------
    // Helper method: finds a student in the list by ID.
    // Returns null if no student has that ID.
    // ---------------------------------------------------------
    private static Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }
}
