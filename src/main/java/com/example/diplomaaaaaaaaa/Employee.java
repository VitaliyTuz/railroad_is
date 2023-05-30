package com.example.diplomaaaaaaaaa;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employee {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleStringProperty name = new SimpleStringProperty("");
    private final SimpleIntegerProperty workExperience = new SimpleIntegerProperty();
    private final SimpleStringProperty gender = new SimpleStringProperty("");
    private final SimpleIntegerProperty age = new SimpleIntegerProperty();
    private final SimpleIntegerProperty childCount = new SimpleIntegerProperty();
    private final SimpleIntegerProperty salary = new SimpleIntegerProperty();
    private final SimpleStringProperty department = new SimpleStringProperty("");  // відділ
    private final SimpleStringProperty position = new SimpleStringProperty("");    // посада
    private final SimpleStringProperty brigade = new SimpleStringProperty("");     // бригада

    Employee(Integer id, String name, Integer workExperience, String gender, Integer age, Integer childCount, Integer salary, String department, String position, String brigade) {
        this.setId(id);
        this.setName(name);
        this.setWorkExperience(workExperience);
        this.setGender(gender);
        this.setAge(age);
        this.setChildCount(childCount);
        this.setSalary(salary);
        this.setDepartment(department);
        this.setPosition(position);
        this.setBrigade(brigade);
    }

    public void setId(Integer id) {this.id.set(id);}

    public void setName(String name) {this.name.set(name);}

    public void setWorkExperience(Integer workExperience) {
        this.workExperience.set(workExperience);
    }

    public void setGender(String gender) {this.gender.set(gender);}

    public void setAge(Integer age) {this.age.set(age);}
    public void setChildCount(Integer childCount) {
        this.childCount.set(childCount);
    }

    public void setSalary(Integer salary) {
        this.salary.set(salary);
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public void setBrigade(String brigade) {
        this.brigade.set(brigade);
    }

    public Integer getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public Integer getWorkExperience() {
        return workExperience.get();
    }

    public String getGender() {
        return gender.get();
    }

    public Integer getAge() {
        return age.get();
    }

    public Integer getChildCount() {
        return childCount.get();
    }

    public Integer getSalary() {
        return salary.get();
    }

    public String getDepartment() {
        return department.get();
    }

    public String getPosition() {
        return position.get();
    }

    public String getBrigade() {
        return brigade.get();
    }

    public String getInfo() {
        return "Ім'я: " + this.getName() + " Досвід роботи: " + this.getWorkExperience() +
                " Стать: " + this.getGender() + " Вік: " + this.getAge() +
                " Кількість дітей: " + this.getChildCount() + " Запрлата: " + this.getSalary() +
                " Відділ: " + this.getDepartment() + " Посада: " + this.getPosition() +
                " Бригада: " + this.getBrigade();
    }
}

