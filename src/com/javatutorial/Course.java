package com.javatutorial;

import java.io.FileWriter;
import java.io.IOException;

public class Course {
    private String name;
    private double cost;
    private static int idCounter = 1;
    private int id;

    public Course(String name, double cost) {
        this.name = name;
        this.cost = cost;
        this.id = idCounter++;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void save() {
        System.out.println("Saving course...");
        try(FileWriter courseWriter = new FileWriter("courses.txt", true)) {
            courseWriter.write(String.format("%s,%s\n", name, cost));
        } catch(IOException e) {
            System.out.println("Error: Unable to save course.");
        }
    }

    public int getId() {
        return this.id;
    }
}