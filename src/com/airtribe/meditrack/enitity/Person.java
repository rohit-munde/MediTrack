package com.airtribe.meditrack.enitity;

public class Person {
    private int id;
    private String name;
    private int age;
    private String email;
    private String contactNo;

    public Person() {
    }

    public Person(int id, String name, int age, String email, String contactNo) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.contactNo = contactNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
