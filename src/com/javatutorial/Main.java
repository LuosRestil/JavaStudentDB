package com.javatutorial;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    private static Map<Integer, Course> courses = new LinkedHashMap<>();
    private static Map<Integer, Student> students = new LinkedHashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadCourses();
        loadStudents();

        System.out.println("*** LOG IN ***");
        boolean admin = false;
        boolean validID = false;
        boolean validPassword = false;
        int id = 0;
        String password;
        while (!validID) {
            System.out.print("ID: ");
            try {
                id = scanner.nextInt();
                scanner.nextLine();
                if (students.containsKey(id) || id == 23646) {
                    validID = true;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } catch(NoSuchElementException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }

        while (!validPassword) {
            System.out.print("Password: ");
            password = scanner.nextLine();
            if (id == 23646 && password.equals("password")) {
                validPassword = true;
                admin = true;
            } else {
                if (password.equals(students.get(id).getPassword())) {
                    validPassword = true;
                } else {
                    System.out.println("Invalid password.");
                }
            }
        }

        boolean quit = false;
        if (admin) {
            // admin menu
            System.out.println("Welcome, Admin!");
            while(!quit) {
                System.out.println("*** MENU ***");
                System.out.println("1 - Add students");
                System.out.println("2 - Add courses");
                System.out.println("3 - View students");
                System.out.println("4 - View courses");
                System.out.println("5 - Quit");
                System.out.print("Enter a selection (1-5): ");
                try {
                    int selection = scanner.nextInt();
                    scanner.nextLine();
                    switch(selection) {
                        case 1:
                            addStudents(scanner);
                            saveStudents();
                            break;
                        case 2:
                            addCourses(scanner);
                            break;
                        case 3:
                            System.out.println("############# STUDENTS #############");
                            for (Student student : students.values()) {
                                System.out.println(student.toString());
                                for (int courseID : student.getStudentCourses()) {
                                    Course course = courses.get(courseID);
                                    System.out.println("\t" + course.getName());
                                }
                            }
                            System.out.println("####################################");
                            break;
                        case 4:
                            System.out.println("############## COURSES #############");
                            for (Course course : courses.values()) {
                                System.out.println(String.format("%s, $%.2f", course.getName(), course.getCost()));
                            }
                            System.out.println("####################################");
                            break;
                        case 5:
                            quit = true;
                            break;
                        default:
                            System.out.println("Invalid input.");
                    }
                } catch(NoSuchElementException e) {
                    scanner.nextLine();
                    System.out.println("Invalid input.");
                }
            }

        } else {
            // student menu
            Student student = students.get(id);
            System.out.println("Welcome, " + student.getName().split(" ")[0] + "!");
            while(!quit) {
                System.out.println("*** MENU ***");
                System.out.println("1 - Enroll in courses");
                System.out.println("2 - Make a payment");
                System.out.println("3 - View your courses");
                System.out.println("4 - View balance");
                System.out.println("5 - Quit");
                System.out.print("Enter a selection (1-5): ");
                try {
                    int selection = scanner.nextInt();
                    scanner.nextLine();
                    switch(selection) {
                        case 1:
                            student.enroll(courses, scanner);
                            saveStudents();
                            break;
                        case 2:
                            student.makePayment(scanner);
                            saveStudents();
                            break;
                        case 3:
                            System.out.println("############## COURSES #############");
                            for (int courseID : student.getStudentCourses()) {
                                System.out.println(courses.get(courseID).getName());
                            }
                            System.out.println("####################################");
                            break;
                        case 4:
                            System.out.println(String.format("Your balance is $%.2f", student.getBalance()));
                            break;
                        case 5:
                            quit = true;
                            break;
                        default:
                            System.out.println("Invalid input.");
                    }
                } catch(NoSuchElementException e) {
                    scanner.nextLine();
                    System.out.println("Invalid input.");
                }
            }
        }
    }

    public static void addStudents(Scanner scanner) {
        boolean sayNext = false;
        boolean done = false;
        while (!done) {
            boolean confirmed = false;
            while(!confirmed) {
                if (sayNext) {
                    System.out.println("Next student...");
                }
                System.out.print("Enter student name: ");
                String name = scanner.nextLine();
                int year = 0;
                boolean validYear = false;
                while (!validYear) {
                    System.out.println("Enter student year\nFreshman - 1\nSophomore - 2\nJunior - 3\nSenior - 4");
                    System.out.print("Year: ");
                    try {
                        year = scanner.nextInt();
                        if (year != 1 && year != 2 && year != 3 && year != 4) {
                            System.out.println("Invalid input.");
                            continue;
                        } else {
                            validYear = true;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                    }
                }
                scanner.nextLine();
                boolean yn = false;
                while (!yn) {
                    System.out.println("Student: " + name + ", Year: " + year);
                    System.out.print("Is this correct? (y/n) : ");
                    String correct = scanner.nextLine().toLowerCase();
                    if (correct.equals("y")) {
                        yn = true;
                        confirmed = true;
                        Student student = new Student(name, year);
                        students.put(student.getId(), student);
                        sayNext = true;
                        boolean validMoreStudents = false;
                        while (!validMoreStudents) {
                            System.out.print("Would you like to add another student? (y/n) : ");
                            String moreStudents = scanner.nextLine().toLowerCase();
                            if (moreStudents.equals("y")) {
                                // yes
                                done = false;
                                validMoreStudents = true;
                            } else if (moreStudents.equals("n")) {
                                done = true;
                                validMoreStudents = true;
                            } else {
                                System.out.println("Invalid input.");
                            }
                        }
                    } else if (correct.equals("n")) {
                        System.out.println("That's okay. Let's try again.");
                        sayNext = false;
                        yn = true;
                    } else {
                        System.out.println("Invalid input.");
                    }
                }
            }
        }
    }

    public static void addCourses(Scanner scanner) {
        System.out.println("Add courses...");
        boolean sayNext = false;
        boolean done = false;
        while (!done) {
            boolean confirmed = false;
            while(!confirmed) {
                if (sayNext) {
                    System.out.println("Next course...");
                }
                System.out.print("Enter course name: ");
                String name = scanner.nextLine();
                double cost = 0;
                boolean validCost = false;
                while (!validCost) {
                    System.out.print("Enter course cost: $");
                    String input = scanner.nextLine();
                    try {
                        cost = Double.parseDouble(input);
                        String cents = Double.toString(cost).split("\\.")[1];
                        if (cents.length() > 2) {
                            System.out.println("Invalid input. Cents must be greater than or equal to 1 and less than or equal to 99.");
                        } else {
                            validCost = true;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                    }
                }
                boolean yn = false;
                while (!yn) {
                    System.out.println("Course: " + name + ", Cost: " + String.format("$%.2f", cost));
                    System.out.print("Is this correct? (y/n) : ");
                    String correct = scanner.nextLine().toLowerCase();
                    if (correct.equals("y")) {
                        yn = true;
                        confirmed = true;
                        Course course = new Course(name, cost);
                        courses.put(course.getId(), course);

                        // save course
                        try (FileWriter fileWriter = new FileWriter("courses.txt", true)) {
                            fileWriter.write(course.getName() + "," + course.getCost() + "\n");
                        } catch (IOException e) {
                            System.out.println("Error: Unable to save course. File doesn't exist or is protected.");
                        }

                        sayNext = true;
                        boolean validMoreCourses = false;
                        while (!validMoreCourses) {
                            System.out.print("Would you like to add another course? (y/n) : ");
                            String moreCourses = scanner.nextLine().toLowerCase();
                            if (moreCourses.equals("y")) {
                                // yes
                                done = false;
                                validMoreCourses = true;
                            } else if (moreCourses.equals("n")) {
                                done = true;
                                validMoreCourses = true;
                            } else {
                                System.out.println("Invalid input.");
                            }
                        }
                    } else if (correct.equals("n")) {
                        System.out.println("That's okay. Let's try again.");
                        sayNext = false;
                        yn = true;
                    } else {
                        System.out.println("Invalid input.");
                    }
                }
            }
        }
        // enter course name
        // enter course cost
        // confirm
        // Course course = new Course(name, cost);
        // courses.put(course.getId(), course);
        // append course to file
    }

    public static void loadCourses() {
        try (Scanner fileScanner = new Scanner(new FileReader("courses.txt"))){
            fileScanner.useDelimiter(",");
            while (fileScanner.hasNextLine()) {
                String name = fileScanner.next();
                fileScanner.skip(fileScanner.delimiter());
                double cost = Double.parseDouble(fileScanner.nextLine());
                Course course = new Course(name, cost);
                courses.put(course.getId(), course);
            }
        } catch(IOException e) {
            System.out.println("Error: Unable to load courses. File doesn't exist or is protected.");
        }
    }

    public static void loadStudents() {
        try (Scanner fileScanner = new Scanner(new FileReader("studentDB.txt"))) {
            while (fileScanner.hasNextLine()) {
                String studentString = fileScanner.nextLine();
                String[] studentArray = studentString.split(",");
                String name = studentArray[0];
                int year = Integer.parseInt(studentArray[1]);
                int id = Integer.parseInt(studentArray[2]);
                double balance = Double.parseDouble(studentArray[3]);
                String password = studentArray[4];
                Student student = new Student(name, year, id, balance, password);
                if (studentArray.length > 5) {
                    for (int i = 5; i < studentArray.length; i++) {
                        student.addCourse(Integer.parseInt(studentArray[i]));
                    }
                }
                students.put(id, student);
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to load students. File doesn't exist or is protected.");
        }
    }

    public static void saveStudents() {
        try (FileWriter studentsWriter = new FileWriter("studentDB.txt", false)){
            for (Student student : students.values()) {
                String toWrite = String.format("%s,%d,%d,%f,%s", student.getName(), student.getYear(), student.getId(), student.getBalance(), student.getPassword());
                for (int id : student.getStudentCourses()) {
                    toWrite += "," + id;
                }
                toWrite += "\n";
                studentsWriter.write(toWrite);
            }
        } catch(IOException e) {
            System.out.println("Error: Unable to save student.");
        }
    }
}