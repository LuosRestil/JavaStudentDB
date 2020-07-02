package com.javatutorial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static Map<Integer, Course> courses = new HashMap<>();
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        courses.put(1, new Course("History 101", 600.0));
        courses.put(2, new Course("Mathematics 101", 600.0));
        courses.put(3, new Course("English 101", 600.0));
        courses.put(4, new Course("Chemistry 101", 600.0));
        courses.put(5, new Course("Computer Science 101", 600.0));

        StudentDB.addStudents(2, scanner);
        Map<Integer, Student> students =  StudentDB.getStudents();
        for (Map.Entry entry : students.entrySet()) {
            System.out.println(entry.getValue());
        }

        System.out.println();
        System.out.println("#################################################");
        System.out.println();

        students.get(10001).enroll(courses, scanner);

        students = StudentDB.getStudents();
        for (Map.Entry entry : students.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}