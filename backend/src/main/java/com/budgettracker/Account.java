package com.budgettracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) //valoarea se generează automat
    private Integer accountId;
    private String accountName;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="budgetUser_userid" , referencedColumnName="userid" , nullable=false)
    private BudgetUser budgetUser;
    private Boolean active;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
        super();
    }

    public Account(Integer accountId, String accountName, Boolean active, BudgetUser budgetUser) {
        super();
        this.accountId = accountId;
        this.accountName = accountName;
        this.active = active;
        this.budgetUser = budgetUser;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BudgetUser getBudgetUser() {
        return budgetUser;
    }

    public void setBudgetUser(BudgetUser budgetUser) {
        this.budgetUser = budgetUser;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, accountName, active, budgetUser);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        return Objects.equals(accountId, other.accountId) && Objects.equals(accountName, other.accountName)
                && Objects.equals(active, other.active) && Objects.equals(budgetUser, other.budgetUser);
    }

    @Override
    public String toString() {
        return "Account [accountId=" + accountId + ", accountName=" + accountName + ", user=" + budgetUser + ", active="
                + active + "]";
    }
}
