package com.budgettracker;

import java.util.Objects;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer transactionId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name="account_accountid" , referencedColumnName="accountid" , nullable=false)
    private Account account;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name="budgetUser_userid" , referencedColumnName="userid" , nullable=false)
    private BudgetUser budgetUser;

    @ManyToOne
    @JoinColumn(name="category_categoryid" , referencedColumnName="categoryid" , nullable=false)
    private Category category;
    private String description;
    private Double amount;

    public Transaction() {
        super();
    }

    public Transaction(Integer transactionId, TransactionType transactionType, Account account, Date date, BudgetUser budgetUser, Category category, String description, Double amount) {
        super();
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.account = account;
        this.date = date;
        this.budgetUser = budgetUser;
        this.category = category;
        this.description = description;
        this.amount = amount;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BudgetUser getBudgetUser() {
        return budgetUser;
    }

    public void setBudgetUser(BudgetUser budgetUser) {
        this.budgetUser = budgetUser;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, amount, budgetUser, category, date, description, transactionId, transactionType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transaction other = (Transaction) obj;
        return Objects.equals(account, other.account) && Objects.equals(amount, other.amount)
                && Objects.equals(budgetUser, other.budgetUser) && Objects.equals(category, other.category)
                && Objects.equals(date, other.date) && Objects.equals(description, other.description)
                && Objects.equals(transactionId, other.transactionId) && transactionType == other.transactionType;
    }

    @Override
    public String toString() {
        return "Transaction [transactionId=" + transactionId + ", transactionType=" + transactionType + ", account="
                + account + ", date=" + date + ", budgetUser=" + budgetUser + ", category=" + category
                + ", description=" + description + ", amount=" + amount + "]";
    }
}
