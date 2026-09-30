package com.budgettracker;

import java.util.Objects;
import jakarta.persistence.*;
@Entity

public class SavingsAccount extends Account{ //moștenește tot ce are clasa Account
    private Double balance;
    private Double interestRate = 0.00;
    private Integer numberOfAccounts = 0;

    public SavingsAccount() {
        super();
    }

    public SavingsAccount(Double balance, Double interestRate, Integer numberOfAccounts) {
        super();
        this.balance = balance;
        this.interestRate = interestRate;
        this.numberOfAccounts = numberOfAccounts;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getNumberOfAccounts() {
        return numberOfAccounts;
    }

    public void setNumberOfAccounts(Integer numberOfAccounts) {
        this.numberOfAccounts = numberOfAccounts;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(balance, interestRate, numberOfAccounts);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        SavingsAccount other = (SavingsAccount) obj;
        return Objects.equals(balance, other.balance) && Objects.equals(interestRate, other.interestRate)
                && Objects.equals(numberOfAccounts, other.numberOfAccounts);
    }

    @Override
    public String toString() {
        return "SavingsAccount [balance=" + balance + ", interestRate=" + interestRate + ", numberOfAccounts="
                + numberOfAccounts + "]";
    }
}
