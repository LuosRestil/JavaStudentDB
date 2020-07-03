package com.javatutorial;

import java.io.FileWriter;
import java.io.IOException;
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
    private String password;
    private ArrayList<Integer> studentCourses = new ArrayList<>();

    public Student(String name, int year) {
        this.name = name;
        this.year = year;
        this.id = (idCounter++) + (year * 10000);
        this.password = generateRandomPassword(10);
    }

    public Student(String name, int year, int id, double balance, String password) {
        this.name = name;
        this.year = year;
        this.id = id;
        this.balance = balance;
        this.password = password;
    }

    private String generateRandomPassword(int len) {
        String passSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890$!#@^*";
        char[] password = new char[len];
        for (int i = 0; i < len; i++) {
            int num = (int)(Math.random() * passSet.length());

            password[i] = passSet.charAt(num);
        }
        return new String(password);
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
                            if (studentCourses.contains(courseNum)) {
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
                                studentCourses.add(courseNum);
                                balance += course.getCost();
                                System.out.println("You are now enrolled in " + course.getName() + ".");
                                System.out.println(String.format("$%.2f has been charged to your account. Your balance is now $%.2f.", course.getCost(), balance));
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

    public String getPassword() {
        return password;
    }

    public ArrayList<Integer> getStudentCourses() {
        return studentCourses;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void makePayment(Scanner scanner) {
        System.out.println("~~~ Make Payment ~~~");
        System.out.println("Your outstanding balance is: " + String.format("%.2f", balance));
        boolean validInput = false;
        double amount = 0;
        while (!validInput) {
            System.out.print("Enter payment amount: $");
            String input = scanner.nextLine();
            try {
                amount = Double.parseDouble(input);
                String cents = Double.toString(amount).split("\\.")[1];
                if (cents.length() > 2) {
                    System.out.println("Invalid input.");
                } else {
                    validInput = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Invalid input");
            }
        }
        System.out.println(String.format("Making a payment of $%.2f...", amount));
        boolean confirmed = false;
        while (!confirmed) {
            System.out.println("Are you sure? (y/n) : ");
            String ans = scanner.nextLine().toLowerCase();
            if (ans.equals("y")) {
                confirmed = true;
                double overpay = Math.abs(balance - amount);
                balance -= amount;
                if (balance < 0) {
                    balance = 0;
                    System.out.println("You have overpayed by " + String.format("$%.2f", overpay) + ". This amount will be returned to you.");
                }
                System.out.println("Thank you for your payment. Your new balance is " + String.format("$%.2f", balance));
            } else if (ans.equals("n")) {
                System.out.println("Canceling payment.");
                confirmed = true;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    public void addCourse(int id) {
        studentCourses.add(id);
    }

    @Override
    public String toString() {
        return String.format("ID#%d, %s, year %d, balance: %s", id, name, year, String.format("$%.2f", balance));
    }
}