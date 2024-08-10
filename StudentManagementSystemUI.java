import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String name;
    private int rollNumber;
    private int grade;

    public Student(String name, int rollNumber, int grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public int getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", rollNumber=" + rollNumber +
                ", grade=" + grade +
                '}';
    }
}

class StudentManagementSystem {
    private final List<Student> students;

    public StudentManagementSystem() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(int rollNumber) {
        students.removeIf(student -> student.getRollNumber() == rollNumber);
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        for (Student student : students) {
            System.out.println(student.toString());
        }
    }
}

public class StudentManagementSystemUI {
    private final  StudentManagementSystem studentManagementSystem;
    private final  Scanner scanner;

    public StudentManagementSystemUI(StudentManagementSystem studentManagementSystem) {
        this.studentManagementSystem = studentManagementSystem;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("Student Management System");
            System.out.println("1. Add a new student");
            System.out.println("2. Edit an existing student's information");
            System.out.println("3. Search for a student");
            System.out.println("4. Display all students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> editStudent();
                case 3 -> searchStudent();
                case 4 -> displayAllStudents();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.next();
        System.out.print("Enter student roll number: ");
        int rollNumber = scanner.nextInt();
        System.out.print("Enter student grade: ");
        int grade = scanner.nextInt();

        Student student = new Student(name, rollNumber, grade);
        studentManagementSystem.addStudent(student);
        System.out.println("Student added successfully!");
    }

    private void editStudent() {
        System.out.print("Enter student roll number: ");
        int rollNumber = scanner.nextInt();

        Student student = studentManagementSystem.searchStudent(rollNumber);
        if (student != null) {
            System.out.print("Enter new student name: ");
            String name = scanner.next();
            System.out.print("Enter new student grade: ");
            int grade = scanner.nextInt();

            student.setName(name);
            student.setGrade(grade);
            System.out.println("Student information updated successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }

    private void searchStudent() {
        System.out.print("Enter student roll number: ");
        int rollNumber = scanner.nextInt();

        Student student = studentManagementSystem.searchStudent(rollNumber);
        if (student != null) {
            System.out.println(student.toString());
        } else {
            System.out.println("Student not found!");
        }
    }

    private void displayAllStudents() {
        studentManagementSystem.displayAllStudents();
    }

    public static void main(String[] args) {
        StudentManagementSystem studentManagementSystem = new StudentManagementSystem();
        StudentManagementSystemUI studentManagementSystemUI = new StudentManagementSystemUI(studentManagementSystem);
        studentManagementSystemUI.run();
    }
}