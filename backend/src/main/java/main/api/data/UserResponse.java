package main.api.data;

import main.database.dto.UserData;

public class UserResponse {
    /*
        Klasa POJO używana w api jako response
    */

    private int id;
    private String name;
    private String surname;
    private String email;

    public UserResponse(UserData data){
        this.id = data.getId();
        this.name = data.getName();
        this.surname = data.getSurname();
        this.email = data.getEmail();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
