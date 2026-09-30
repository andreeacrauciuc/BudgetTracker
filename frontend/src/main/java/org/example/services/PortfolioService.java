package org.example.services;

import com.budgettracker.Account;
import com.budgettracker.BudgetUser;
import com.budgettracker.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class PortfolioService {
    private EntityManagerFactory emf = JpaUtil.getEmf();

    public List<BudgetUser> findAllCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM BudgetUser u", BudgetUser.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Account> findAccountsByCustomerId(Integer userId) {
        if (userId == null) return new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Account a WHERE a.budgetUser.userId = :uid", Account.class)
                    .setParameter("uid", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Transaction> findTransactionsByAccountId(Integer accountId) {
        if (accountId == null) return new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT t FROM Transaction t WHERE t.account.accountId = :aid", Transaction.class)
                    .setParameter("aid", accountId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public FinancialSummary getFinancialSummary(List<Transaction> transactions) {
        double income = 0;
        double expense = 0;
        if (transactions != null) {
            for (Transaction t : transactions) {
                if (t.getAmount() != null && t.getTransactionType() != null) {
                    String type = String.valueOf(t.getTransactionType()).toUpperCase();
                    if (type.contains("INCOME")) income += t.getAmount();
                    else if (type.contains("EXPENSE")) expense += t.getAmount();
                }
            }
        }
        return new FinancialSummary(income, expense);
    }

    public record FinancialSummary(double totalIncome, double totalExpense) {}

    public long getTotalUsersCount() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT COUNT(u) FROM BudgetUser u", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }

    public double getTotalAllocatedBudget() {
        EntityManager em = emf.createEntityManager();
        try {
            Double result = em.createQuery("SELECT SUM(b.amount) FROM Budget b", Double.class).getSingleResult();
            return result != null ? result : 0.0;
        } finally {
            em.close();
        }
    }
}
