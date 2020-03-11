package com.example.registrationlist;

public class ResidentDetails {
    String name;
    private String dateOfBirth;

    public ResidentDetails() {
        // This is default constructor.
    }

    public String getResidentName() {
        return name;
    }

    public void setResidentName(String name) {
        this.name = name;
    }

    public String getResidentDateOfBirth() {
        return dateOfBirth;
    }

    public void setResidentDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
