
package com.budgettracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import java.util.*;
@Entity
public class BudgetUser {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer userId;

    private String username;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date registerDate;

    private String email;

    private String password;

    @OneToMany(cascade=CascadeType.PERSIST, mappedBy = "budgetUser")
    private List<Account> accounts 			= new ArrayList<>();

    @OneToMany(cascade=CascadeType.PERSIST, mappedBy = "budgetUser")
    private List<Transaction> transactions 	= new ArrayList<>();

    @OneToMany(cascade=CascadeType.PERSIST, mappedBy = "payee")
    private List<Loan> loans 				= new ArrayList<>();

    @OneToMany(cascade=CascadeType.PERSIST, mappedBy = "budgetUser")
    private List<Balance> balances 			= new ArrayList<>();

    public BudgetUser() {
        super();
    }

    public BudgetUser(Integer userId, String username, String name, Date registerDate, String email, String password, List<Account> accounts, List<Transaction> transactions, List<Loan> loans, List<Balance> balances) {
        super();
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.registerDate = registerDate;
        this.email = email;
        this.password = password;
        this.accounts = accounts;
        this.transactions = transactions;
        this.loans = loans;
        this.balances = balances;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, userId, name, password, registerDate, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BudgetUser other = (BudgetUser) obj;
        return Objects.equals(email, other.email) && Objects.equals(userId, other.userId)
                && Objects.equals(name, other.name) && Objects.equals(password, other.password)
                && Objects.equals(registerDate, other.registerDate) && Objects.equals(username, other.username);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", name=" + name + ", registerDate=" + registerDate
                + ", email=" + email + ", password=" + password + "]";
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public void addLoan(Loan loan) {
        this.loans.add(loan);
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public void addBalance(Balance balance) {
        this.balances.add(balance);
    }
}
