package com.budgettracker;

import jakarta.persistence.*;
import java.util.*;

public class RepositoryBudgetUser implements IRepositoryBudgetUser {
    private EntityManager entityManager;
    private String sqlDefaultText = "SELECT u FROM BudgetUser u";

    public RepositoryBudgetUser(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public RepositoryBudgetUser() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProduseJPA");
        this.entityManager = emf.createEntityManager();
    }


    @Override
    public void add(BudgetUser user) {
        try {
            entityManager.getTransaction().begin();
            if (user.getUserId() != null && entityManager.find(BudgetUser.class, user.getUserId()) != null) {
                this.entityManager.merge(user);
                System.out.println(" to update-merge BudgetUser" + user.getUsername());
            } else {
                this.entityManager.persist(user);
                System.out.println(" to insert BudgetUser" + user.getUsername());
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            throw new RuntimeException("Eroare la adăugarea/actualizarea utilizatorului: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void remove(BudgetUser user) {
        BudgetUser userPersistent = entityManager.find(BudgetUser.class, user.getUserId());
        try {
            entityManager.getTransaction().begin();
            if (userPersistent != null) {
                this.entityManager.remove(userPersistent);
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            throw new RuntimeException("Eroare la ștergerea utilizatorului: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void refresh(BudgetUser user) {
        this.entityManager.refresh(user);
    }

    @Override
    public void removeAll() {
        try {
            entityManager.getTransaction().begin();
            entityManager.createQuery("DELETE FROM BudgetUser u").executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            if (entityManager.getTransaction().isActive())
                entityManager.getTransaction().rollback();
            throw new RuntimeException("Eroare la ștergerea tuturor utilizatorilor: " + ex.getMessage(), ex);
        }
    }


    @Override
    public BudgetUser getBudgetUserDupaID(Integer userId) {
        return this.entityManager.find(BudgetUser.class, userId);
    }

    @Override
    public Collection<BudgetUser> getAll() {
        List<BudgetUser> result = this.entityManager
                .createQuery(this.sqlDefaultText, BudgetUser.class)
                .getResultList();
        TreeSet<BudgetUser> entitatiOrdonate = new TreeSet<>(Comparator.comparing(BudgetUser::getUserId));
        entitatiOrdonate.addAll(result);
        return entitatiOrdonate;
    }


    @Override
    public Long getNumarUtilizatori() {

        return this.entityManager
                .createQuery("SELECT COUNT(u) FROM BudgetUser u", Long.class)
                .getSingleResult();
    }

    @Override
    public BudgetUser getBudgetUserDupaUsername(String username) {
        return this.entityManager
                .createQuery(sqlDefaultText + " WHERE u.username = :numeUtilizator", BudgetUser.class)
                .setParameter("numeUtilizator", username)
                .getSingleResult();
    }

    @Override
    public List<BudgetUser> getUtilizatoriDupaNume(String name) {
        return this.entityManager
                .createQuery(sqlDefaultText + " WHERE u.name LIKE :numePersoana", BudgetUser.class)
                .setParameter("numePersoana", "%" + name + "%")
                .getResultList();
    }

    @Override
    public BudgetUser getBudgetUserDupaEmail(String email) {
        return this.entityManager
                .createQuery(sqlDefaultText + " WHERE u.email = :emailUtilizator", BudgetUser.class)
                .setParameter("emailUtilizator", email)
                .getSingleResult();
    }
}