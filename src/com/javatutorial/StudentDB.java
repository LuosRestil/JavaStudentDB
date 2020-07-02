package com.javatutorial;

import java.util.*;

public final class StudentDB {
    private static Map<Integer, Student> students = new HashMap<>();

    public static void addStudents(int num, Scanner scanner) {
        boolean sayNext = false;
        for (int i = 0; i < num; i++) {
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

    public static Map<Integer, Student> getStudents() {
        return students;
    }
}