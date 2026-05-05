package org.example;

public class Student extends Person implements Greetable {

    private String studentId;
    private double gpa;
    public String major;

    // ----- Constructors -----
    public Student() {
        super();
        this.studentId = "UNKNOWN";
        this.gpa = 0.0;
        this.major = "Undeclared";
    }

    public Student(String name) {
        super(name, 0);
        this.studentId = "UNKNOWN";
        this.gpa = 0.0;
        this.major = "Undeclared";
    }

    public Student(String name, int age) {
        super(name, age);
        this.studentId = "UNKNOWN";
        this.gpa = 0.0;
        this.major = "Undeclared";
    }

    public Student(String name, int age, String studentId, double gpa, String major) {
        super(name, age);
        this.studentId = studentId;
        this.gpa = gpa;
        this.major = major;
    }

    // ----- Public method -----
    @Override
    public void sayHello() {
        System.out.println("Hello, I am " + name + " (" + studentId + ")");
    }

    public void study(String subject) {
        System.out.println(name + " is studying " + subject);
    }

    // ----- Private method (for Ex 7) -----
    private void secretCalculation() {
        System.out.println("Secret calculation done by " + name + ", gpa=" + gpa);
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age
                + ", studentId='" + studentId + "', gpa=" + gpa
                + ", major='" + major + "'}";
    }
}
