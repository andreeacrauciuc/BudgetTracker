
package com.budgettracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import java.util.*;
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer loanId;
    private String description;
    private String payer;
    @ManyToOne
    private BudgetUser payee;
    private Double amount;
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    public Loan() {
        super();
    }

    public Loan(Integer loanId, String description, String payer, BudgetUser payee, Double amount, Date returnDate) {
        super();
        this.loanId = loanId;
        this.description = description;
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
        this.returnDate = returnDate;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public BudgetUser getPayee() {
        return payee;
    }

    public void setPayee(BudgetUser payee) {
        this.payee = payee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, description, loanId, payee, payer, returnDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Loan other = (Loan) obj;
        return Objects.equals(amount, other.amount) && Objects.equals(description, other.description)
                && Objects.equals(loanId, other.loanId) && Objects.equals(payee, other.payee)
                && Objects.equals(payer, other.payer) && Objects.equals(returnDate, other.returnDate);
    }

    @Override
    public String toString() {
        return "Loan [loanId=" + loanId + ", description=" + description + ", payer=" + payer + ", payee=" + payee
                + ", amount=" + amount + ", returnDate=" + returnDate + "]";
    }
}

