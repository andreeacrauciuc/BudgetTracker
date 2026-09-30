package org.example.services;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ProduseJPA");

    public static EntityManagerFactory getEmf() {
        return emf;
    }
}