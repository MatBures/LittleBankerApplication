package com.awesome.LittleBankerApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "customers")
public class CustomerModel {

    @Id
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "customer_sequence"
    )
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "name")
    @NotBlank(message = "Please specify name.")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "Please specify surname.")
    private String surname;

    @Column(name = "sex")
    @NotBlank(message = "Please specify sex.")
    private String sex;

    @Column(name = "nationality")
    @NotBlank(message = "Please specify nationality.")
    private String nationality;

    @Column(name = "date_of_birth")
    @NotNull(message = "Please specify date of birth.")
    private LocalDate dateOfBirth;

    @Column(name = "card_number")
    @NotNull(message = "Please specify date of birth.")
    private String cardNumber;

    @Column(name = "date_of_card_issue")
    @NotNull(message = "Please specify date of card issue.")
    private LocalDate dateOfCardIssue;

    @Column(name = "date_of_card_expiration")
    @NotNull(message = "Please specify date of card expiration.")
    private LocalDate dateOfCardExpiration;


    public CustomerModel() {
    }

    public CustomerModel(String name, String surname, String sex, String nationality, LocalDate dateOfBirth, String cardNumber, LocalDate dateOfCardIssue, LocalDate dateOfCardExpiration) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.cardNumber = cardNumber;
        this.dateOfCardIssue = dateOfCardIssue;
        this.dateOfCardExpiration = dateOfCardExpiration;
    }

    public Long getCustomerId() {
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public LocalDate getDateOfCardExpiration() {
        return dateOfCardExpiration;
    }

    public LocalDate getDateOfCardIssue() {
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

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setDateOfCardExpiration(LocalDate dateOfCardExpiration) {
        this.dateOfCardExpiration = dateOfCardExpiration;
    }

    public void setDateOfCardIssue(LocalDate dateOfCardIssue) {
        this.dateOfCardIssue = dateOfCardIssue;
    }
}
