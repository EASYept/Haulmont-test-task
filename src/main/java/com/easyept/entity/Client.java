package com.easyept.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue
    @Column(unique = true, updatable = false)
    private UUID uuid;

    private String firstName;

    private String lastName;

    private String patronymic; /* фамилия */

    private String phoneNumber;

    private String email;

    private String passportNumber;


    public Client(String firstName, String lastName, String patronymic, String phoneNumber, String email, String passportNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passportNumber = passportNumber;
    }

    public String fullNameToString(){
        return getFirstName()+ " " + getLastName() + " " + getPhoneNumber();
    }


}
