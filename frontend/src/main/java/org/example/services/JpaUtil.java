package org.example.services;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class JpaUtil {

    private static final EntityManagerFactory emf = createEmf();

    private static EntityManagerFactory createEmf() {
        Map<String, Object> overrides = new HashMap<>();

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (url != null) overrides.put("jakarta.persistence.jdbc.url", url);
        if (user != null) overrides.put("jakarta.persistence.jdbc.user", user);
        if (password != null) overrides.put("jakarta.persistence.jdbc.password", password);

        return Persistence.createEntityManagerFactory("ProduseJPA", overrides);
    }

    public static EntityManagerFactory getEmf() {
        return emf;
    }
}