package com.javatutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Student {
    private String name;
    private int year;
    private static int idCounter = 1;
    private int id = 0;
    private double balance = 0;
    private ArrayList<Course> studentCourses = new ArrayList<>();

    public Student(String name, int year) {
        this.name = name;
        this.year = year;
        this.id = (idCounter++) + (year * 10000);
    }

    public void enroll(Map<Integer, Course> courses, Scanner scanner) {
        boolean inputValid = false;
        boolean repeat = false;
        while (!inputValid || repeat) {
            int cancel = 1;
            System.out.println("Select a course");
            for (Map.Entry<Integer, Course> entry : courses.entrySet()) {
                cancel++;
                System.out.println(entry.getKey() + " - " + entry.getValue().getName());
            }
            System.out.print("Selection number: ");
            try {
                int courseNum = scanner.nextInt();
                if (courses.containsKey(courseNum)) {
                    inputValid = true;
                    Course course = courses.get(courseNum);
                    System.out.println(course.getName() + " - $" + String.format("%.2f", course.getCost()));
                    scanner.nextLine();
                    boolean yn = false;
                    while (!yn) {
                        System.out.print("Is this correct? (y/n) : ");
                        String answer = scanner.nextLine().toLowerCase();
                        if (answer.equals("y")) {
                            yn = true;
                            // check course isn't in student courses, add course, add to balance
                            if (studentCourses.contains(course)) {
                                System.out.println("You are already enrolled in that course.");
                                boolean repeatInputValid = false;
                                while (!repeatInputValid) {
                                    System.out.println("Enroll in another course? (y/n) : ");
                                    String repeatAnswer = scanner.nextLine().toLowerCase();
                                    if (repeatAnswer.equals("y")) {
                                        repeat = true;
                                        repeatInputValid = true;
                                    } else if (repeatAnswer.equals("n")) {
                                        repeat = false;
                                        repeatInputValid = true;
                                    } else {
                                        System.out.println("Invalid input.");
                                    }
                                }
                            } else {
                                studentCourses.add(course);
                                balance += course.getCost();
                                System.out.println("You are now enrolled in " + course.getName() + ".");
                                boolean repeatInputValid = false;
                                while (!repeatInputValid) {
                                    System.out.println("Enroll in another course? (y/n) : ");
                                    String repeatAnswer = scanner.nextLine().toLowerCase();
                                    if (repeatAnswer.equals("y")) {
                                        repeat = true;
                                        repeatInputValid = true;
                                    } else if (repeatAnswer.equals("n")) {
                                        repeat = false;
                                        repeatInputValid = true;
                                    } else {
                                        System.out.println("Invalid input.");
                                    }
                                }
                            }
                        } else if (answer.equals("n")) {
                            yn = true;
                            System.out.println("That's okay. Let's try again.");
                        } else {
                            System.out.println("Invalid input.");
                        }
                    }
                } else if (courseNum == cancel) {
                    System.out.println("Enrollment canceled.");
                    inputValid = true;
                    break;
                } else {
                    System.out.println("Invalid input.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Course> getStudentCourses() {
        return studentCourses;
    }

    public void payBalance(double amount) {
        balance -= amount;
        if (balance < 0) {
            balance = 0;
            double overpay = balance - amount;
            System.out.println("You have overpayed by " + String.format("$%.2f", overpay) + ". This amount will be returned to your account.");
        }
        System.out.println("Your new balance is " + String.format("%.2f", balance));
    }

    @Override
    public String toString() {
        return String.format("%s, year %d, ID#%d, balance: %s", name, year, id, String.format("$%.2f", balance));
    }
}