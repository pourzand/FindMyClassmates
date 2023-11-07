package com.example.findmyclassmates;

import java.util.List;

public class ClassData {
    private String className;
    private List<String> students;

    public ClassData(String className, List<String> students) {
        this.className = className;
        this.students = students;
    }

    public String getClassName() {
        return className;
    }

    public List<String> getStudents() {
        return students;
    }
}
