package models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Reader {

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty
    @Size(min = 1900, max = 2023, message = "Invalid year of birth passed")
    private int yearOfBirth;

    public Reader(String name, int yearOfBirth) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
