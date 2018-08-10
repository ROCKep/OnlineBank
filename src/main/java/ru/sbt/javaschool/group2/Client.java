package ru.sbt.javaschool.group2;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passport_num", unique = true, nullable = false, length = 10)
    private String passportNum;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    private String address;

    private LocalDate dob;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "client")
    @JsonBackReference
    private Set<Account> accounts;

    public Client() {}

    public Client(final String passportNum, final String lastName, final String firstName, final String address,
                  final LocalDate dob, final String password) {
        setPassportNum(passportNum);
        setLastName(lastName);
        setFirstName(firstName);
        setAddress(address);
        setDob(dob);
        setPassword(password);
    }

    public Long getId() {
        return id;
    }

    public String getPassportNum() {
        return passportNum;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setPassportNum(final String passportNumber) {
        this.passportNum = passportNumber;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setDob(final LocalDate dob) {
        this.dob = dob;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", passportNumber='" + passportNum + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", address='" + address + '\'' +
                ", dob=" + dob +
                ", password='" + password + '\'' +
                '}';
    }
}
