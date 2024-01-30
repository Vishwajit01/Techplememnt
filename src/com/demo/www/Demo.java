package com.demo.www;

import java.io.*;
import java.util.*;

class EManagementSystem {

    private FileWriter fileWriter;
    private int eid = 0;
    private String ename = null, edepartment = null;
    private float esalary = 0.0f;
    private Scanner sc = new Scanner(System.in);

    private String filePath = "C:\\Users\\vishw\\eclipse-workspace\\EmployeeManage\\employees.txt";

    public void addFile() {

        try {
                // Open the file in read mode to check for existing employee IDs
                Scanner fileScanner = new Scanner(new File(filePath));
                Set<Integer> existingIds = new HashSet<>();

                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split("\t");
                    existingIds.add(Integer.parseInt(parts[0]));
            }

            fileScanner.close();

            System.out.println("Enter the Employee ID: ");
            int newId = sc.nextInt();

            // Check if the entered ID already exists
            if (existingIds.contains(newId)) {
                System.out.println("Employee ID " + newId + " already exists. Record not added.");
            }
            else {
                // Append mode is set to true
                fileWriter = new FileWriter(filePath, true);

                System.out.println("Enter the Employee Name: ");
                String newName = sc.next();

                System.out.println("Enter the Employee Department: ");
                String newDepartment = sc.next();

                System.out.println("Enter the Employee Salary: ");
                float newSalary = sc.nextFloat();

                fileWriter.write(newId + "\t" + newName + "\t" + newDepartment + "\t" + newSalary + "\n");

                System.out.println("Record inserted Successfully!!!");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Error performing file operation.");
        }
        finally
        {
            try
            {
                if (fileWriter != null)
                {
                    fileWriter.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    public void updateInFile()
    {
        try
        {
            System.out.println("Please enter what you want to update: ");
            System.out.println("1. Name");
            System.out.println("2. Department");
            System.out.println("3. Salary");
            int input = sc.nextInt();

            List<String> lines = new ArrayList<>();
            File inputFile = new File(filePath);

            try (Scanner fileScanner = new Scanner(inputFile))
            {
                switch (input)
                {
                    case 1:
                        updateField(lines, fileScanner, "Name");
                        break;
                    case 2:
                        updateField(lines, fileScanner, "Department");
                        break;
                    case 3:
                        updateField(lines, fileScanner, "Salary");
                        break;
                    default:
                        System.out.println("Invalid choice!!!");
                }
            }
        }
        catch (InputMismatchException e)
        {
            System.err.println("Invalid input. Please enter a valid choice.");
            sc.nextLine(); // Consume the invalid input
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Error updating the file.");
        }
    }

    private void updateField(List<String> lines, Scanner fileScanner, String field) throws IOException
    {
        File inputFile = new File(filePath);
       
        System.out.println("Enter the Employee ID for which you want to update " + field + ": ");
        int idToUpdate = sc.nextInt();

        while (fileScanner.hasNextLine())
        {
            String line = fileScanner.nextLine();
            String[] parts = line.split("\t");

            if (Integer.parseInt(parts[0]) == idToUpdate)
            {
                System.out.println("Enter the new " + field + " for " + parts[1] + ": ");
                String newValue = sc.next();
                switch (field)
                {
                    case "Name":
                        parts[1] = newValue;
                        break;
                    case "Department":
                        parts[2] = newValue;
                        break;
                    case "Salary":
                        parts[3] = newValue;
                        break;
                }
                line = String.join("\t", parts);
            }

            lines.add(line);
        }

        try (FileWriter writer = new FileWriter(inputFile))
        {
            for (String line : lines)
            {
                writer.write(line + "\n");
            }

            System.out.println("Employee record updated successfully.");
        }
    }


    public void deleteInFile() {
        // This method will delete a particular record from the file by taking employee id and deleting its record from the file
        try {
            System.out.println("Enter the Employee ID to delete: ");
            int idToDelete = sc.nextInt();

            List<String> lines = new ArrayList<>();

            File inputFile = new File(filePath);

            try (Scanner fileScanner = new Scanner(inputFile)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split("\t");

                    if (Integer.parseInt(parts[0]) != idToDelete) {
                        lines.add(line);
                    }
                }
            }

            try (FileWriter writer = new FileWriter(inputFile)) {
                for (String line : lines) {
                    writer.write(line + "\n");
                }
                System.out.println("Employee record deleted successfully.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error deleting the file.");
        }
    }

    // Add this method to view the contents of the file
    public void viewFile() {

        // Initialize filePath before using it
        filePath = "C:\\Users\\vishw\\eclipse-workspace\\EmployeeManage\\employees.txt";
        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            while (fileScanner.hasNextLine()) {
                System.out.println(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File not found.");
        }
    }
}

public class Demo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EManagementSystem ob = new EManagementSystem();

        System.out.println("SELECT OPTIONS");
        System.out.println("---------------------");

        while (true) {
            System.out.println("1. ADD INTO FILE");
            System.out.println("2. VIEW IN FILE");
            System.out.println("3. UPDATE FILE");
            System.out.println("4. DELETE FILE");
            System.out.println("5. EXIT");

            System.out.println("Please Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    ob.addFile();
                    break;
                case 2:
                    ob.viewFile();
                    break;
                case 3:
                    ob.updateInFile();
                    break;
                case 4:
                    ob.deleteInFile();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!!!");
            }
        }
    }
}
