package com.awesome.LittleBankerApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "customers")
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;


    //Name column
    @Column
    @NotBlank(message = "Please specify name.")
    private String name;

    @Column
    @NotBlank(message = "Please specify surname.")
    private String surname;

    @Column
    @NotBlank(message = "Please specify sex.")
    private String sex;

    @Column
    @NotBlank(message = "Please specify nationality.")
    private String nationality;

    @Column
    @NotNull(message = "Please specify date of birth.")
    private Date dateOfBirth;

    @Column
    private String cardNumber;

    @Column
    private Date dateOfCardExpiration;

    @Column
    private Date dateOfCardIssue;

    public CustomerModel() {
    }

    public CustomerModel(String name, String surname, String sex, String nationality, Date dateOfBirth, String cardNumber, Date dateOfCardExpiration, Date dateOfCardIssue) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.cardNumber = cardNumber;
        this.dateOfCardExpiration = dateOfCardExpiration;
        this.dateOfCardIssue = dateOfCardIssue;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSex() {
        return sex;
    }

    public String getNationality() {
        return nationality;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Date getDateOfCardExpiration() {
        return dateOfCardExpiration;
    }

    public Date getDateOfCardIssue() {
        return dateOfCardIssue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setDateOfCardExpiration(Date dateOfCardExpiration) {
        this.dateOfCardExpiration = dateOfCardExpiration;
    }

    public void setDateOfCardIssue(Date dateOfCardIssue) {
        this.dateOfCardIssue = dateOfCardIssue;
    }
}
