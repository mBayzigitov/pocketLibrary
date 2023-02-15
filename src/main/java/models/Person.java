package models;

import javax.validation.constraints.*;

public class Person {

    private int client_id;

    @Pattern(regexp = "[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+", message = "Введите ФИО по форме: Фамилия Имя Отчество")
    private String full_name;

    @NotNull
    @Min(value = 1900, message = "Введите корректный год рождения")
    private int year_of_birth;

    public Person() {}

    public Person(int client_id, String full_name, int year_of_birth) {
        this.client_id = client_id;
        this.full_name = full_name;
        this.year_of_birth = year_of_birth;
    }

    public int getClientId() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }

    @Override
    public String toString() {
        return "Person{" +
                "client_id=" + client_id +
                ", full_name='" + full_name + '\'' +
                ", year_of_birth=" + year_of_birth +
                '}';
    }
}
