package com.jeffreyromero.projectmanager.models;

public interface MaterialQuantifiable {
    double calcQuantity(double area);
    double calcQuantity(double length, double width);
}
