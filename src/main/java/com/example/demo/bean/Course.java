package com.example.demo.bean;

public class Course {
    private String date;
    private String name;
    private String weekday;
    private String section; //节次
    private String classroom;

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getSection() {
        return section;
    }

    public String getClassroom() {
        return classroom;
    }
}
