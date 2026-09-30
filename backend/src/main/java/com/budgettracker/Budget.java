
package com.budgettracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import jakarta.persistence.*;
import java.util.*;
@Entity
@Table(name = "budget", schema = "public")
public class Budget {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer budgetId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;

    private Double amount;
    @Column(name="enddate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name="startdate")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    public Budget() {
        super();
    }

    public Budget(Integer budgetId, Category category, Double amount, Date endDate, Date startDate) {
        super();
        this.budgetId = budgetId;
        this.category = category;
        this.amount = amount;
        this.endDate = endDate;
        this.startDate = startDate;
    }

    public Integer getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Integer budgetId) {
        this.budgetId = budgetId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, budgetId, category, endDate, startDate);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Budget other = (Budget) obj;
        return Objects.equals(amount, other.amount) && Objects.equals(budgetId, other.budgetId)
                && Objects.equals(category, other.category) && Objects.equals(endDate, other.endDate)
                && Objects.equals(startDate, other.startDate);
    }


    @Override
    public String toString() {
        return "Budget [budgetId=" + budgetId + ", category=" + category + ", amount=" + amount + ", endDate=" + endDate
                + ", startDate=" + startDate + "]";
    }
}
