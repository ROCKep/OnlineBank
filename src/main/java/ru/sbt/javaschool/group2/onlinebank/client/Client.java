package ru.sbt.javaschool.group2.onlinebank.client;

import ru.sbt.javaschool.group2.onlinebank.account.Account;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passport_num")
    private String passportNum;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    private String password;

    @OneToMany(mappedBy = "client")
    private Set<Account> accounts = new HashSet<>();


    public Long getId() {
        return id;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFullName() {
        return lastName + ' ' + firstName + (middleName != null ? ' ' + middleName : "");
    }

    public String getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(String passportNum) {
        this.passportNum = passportNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected Client() {}

    public Client(String passportNum, String lastName, String firstName, String middleName, String password) {
        setPassportNum(passportNum);
        setLastName(lastName);
        setFirstName(firstName);
        setMiddleName(middleName);
        setPassword(password);
    }

    public void addAccount(Account account) {
        accounts.add(account);
        account.setClient(this);
    }
}
