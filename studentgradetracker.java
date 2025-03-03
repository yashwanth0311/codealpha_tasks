import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class studentgradetracker {

    private ArrayList<String> studentNames;
    private ArrayList<Double> studentGrades;
    private Scanner scanner;

    public studentgradetracker() {
        studentNames = new ArrayList<>();
        studentGrades = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        studentNames.add(name);

        double grade;
        while (true) {  // Input validation loop
            System.out.print("Enter " + name + "'s grade (0-100): ");
            if (scanner.hasNextDouble()) {
                grade = scanner.nextDouble();
                if (grade >= 0 && grade <= 100) {
                    break; // Valid grade, exit the loop
                } else {
                    System.out.println("Invalid grade. Please enter a value between 0 and 100.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
        }
        studentGrades.add(grade);
        scanner.nextLine(); // Consume newline left by nextDouble
        System.out.println(name + "'s grade added successfully.\n");

    }

    public void displayGrades() {
        if (studentNames.isEmpty()) {
            System.out.println("No student grades entered yet.");
            return;
        }

        System.out.println("Student Grades:");
        for (int i = 0; i < studentNames.size(); i++) {
            System.out.println(studentNames.get(i) + ": " + studentGrades.get(i));
        }
        System.out.println();
    }


    public void calculateStatistics() {
        if (studentGrades.isEmpty()) {
            System.out.println("No grades to calculate statistics for.");
            return;
        }

        double sum = 0;
        for (double grade : studentGrades) {
            sum += grade;
        }
        double average = sum / studentGrades.size();

        double highest = Collections.max(studentGrades);
        double lowest = Collections.min(studentGrades);

        System.out.println("Grade Statistics:");
        System.out.printf("Average: %.2f\n", average); // Format to two decimal places
        System.out.println("Highest: " + highest);
        System.out.println("Lowest: " + lowest);
        System.out.println();
    }
    public static void main(String[] args) {
        studentgradetracker tracker = new studentgradetracker();
        Scanner mainScanner = new Scanner(System.in); // Separate scanner for the menu

        int choice;
        do {
            System.out.println("\nStudent Grade Tracker Menu:");
            System.out.println("1. Add Student Grade");
            System.out.println("2. Display Grades");
            System.out.println("3. Calculate Statistics");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");


            if (mainScanner.hasNextInt()) {
                choice = mainScanner.nextInt();
                mainScanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        tracker.addStudent();
                        break;
                    case 2:
                        tracker.displayGrades();
                        break;
                    case 3:
                        tracker.calculateStatistics();
                        break;
                    case 4:
                        System.out.println("Exiting program.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                mainScanner.next(); // Clear the invalid input
                choice = 0; // To keep the loop running
            }

        } while (choice != 4);

        mainScanner.close(); // Close the scanner when done
    }
}