package org.example.services;

import com.budgettracker.Budget;
import com.budgettracker.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class BudgetService {
    private EntityManagerFactory emf = JpaUtil.getEmf();

    public List<Budget> findAllBudgets() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Budget b ORDER BY b.budgetId", Budget.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Category> findAllCategories() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void save(Budget budget) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(budget);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(Budget budget) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Budget toRemove = em.find(Budget.class, budget.getBudgetId());
            if (toRemove != null) {
                em.remove(toRemove);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Budget findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Budget.class, id);
        } finally {
            em.close();
        }
    }
}