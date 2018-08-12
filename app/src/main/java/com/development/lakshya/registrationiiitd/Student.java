package com.development.lakshya.registrationiiitd;

import java.io.Serializable;

public class Student implements Serializable {

    private String name,roll,branch,course1,course2,course3,course4;

    public Student(String name, String roll, String branch, String course1, String course2, String course3, String course4) {
        this.name = name;
        this.roll = roll;
        this.branch = branch;
        this.course1 = course1;
        this.course2 = course2;
        this.course3 = course3;
        this.course4 = course4;
    }

    public String getName() {
        return name;
    }

    public String getRoll() {
        return roll;
    }

    public String getBranch() {
        return branch;
    }

    public String getCourse1() {
        return course1;
    }

    public String getCourse2() {
        return course2;
    }

    public String getCourse3() {
        return course3;
    }

    public String getCourse4() {
        return course4;
    }
}
