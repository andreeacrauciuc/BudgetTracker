package org.example.services;

import com.budgettracker.BudgetUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class UserService {
    private EntityManagerFactory emf = JpaUtil.getEmf();

    public List<BudgetUser> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM BudgetUser u", BudgetUser.class).getResultList();
        } finally {
            em.close();
        }
    }

    public BudgetUser findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(BudgetUser.class, id);
        } finally {
            em.close();
        }
    }
    public void save(BudgetUser user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void delete(BudgetUser user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            BudgetUser toRemove = em.find(BudgetUser.class, user.getUserId());
            if (toRemove != null) {
                em.remove(toRemove);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public boolean emailExists(String email, Integer currentUserId) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(u) FROM BudgetUser u WHERE u.email = :e AND u.userId != :id", Long.class)
                    .setParameter("e", email)
                    .setParameter("id", currentUserId != null ? currentUserId : -1)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}

