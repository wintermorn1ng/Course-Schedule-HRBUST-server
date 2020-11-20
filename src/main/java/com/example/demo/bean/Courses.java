package com.example.demo.bean;

import java.util.ArrayList;
import java.util.List;

public class Courses {
    public String week;
    public List<Course> data = new ArrayList<>();
    public void setWeek(String week) {
        this.week = week;
    }

}
