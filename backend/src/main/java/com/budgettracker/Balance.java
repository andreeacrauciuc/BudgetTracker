
package com.budgettracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import java.util.*;
@Entity
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer balanceId;

    private Double creditAmount;

    private Double debitAmount;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne
    private BudgetUser budgetUser;

    public Balance() {
        super();
    }

    public Balance(Integer balanceId, Double creditAmount, Double debitAmount) {
        super();
        this.balanceId = balanceId;
        this.creditAmount = creditAmount;
        this.debitAmount = debitAmount;
    }

    public Integer getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public void getSold() {
        Double creditAmount = 0.00;
        Double debitAmount	= 0.00;
        List<Transaction> userTransactions = this.budgetUser.getTransactions();

        for(Transaction transaction : userTransactions)
        {
            if(transaction.getTransactionType() == TransactionType.Income)
            {
                creditAmount += transaction.getAmount();
            } else if(transaction.getTransactionType() == TransactionType.Expense)
            {
                debitAmount += transaction.getAmount();
            }
        }
        setDebitAmount(debitAmount);
        setCreditAmount(creditAmount);
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BudgetUser getBudgetUser() {
        return budgetUser;
    }

    public void setBudgetUser(BudgetUser budgetUser) {
        this.budgetUser = budgetUser;
    }

    @Override
    public int hashCode() {
        return Objects.hash(balanceId, creditAmount, debitAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Balance other = (Balance) obj;
        return Objects.equals(balanceId, other.balanceId) && Objects.equals(creditAmount, other.creditAmount)
                && Objects.equals(debitAmount, other.debitAmount);
    }

    @Override
    public String toString() {
        return "Balance [balanceId=" + balanceId + ", creditAmount=" + creditAmount + ", debitAmount=" + debitAmount
                + "]";
    }
}
