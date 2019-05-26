package com.company.departments;

import com.company.departments.dao.implementation.mysql.DataInitializer;

public class App {
    public static void main(String[] args) {
        DataInitializer
                .loadData();
    }
}
