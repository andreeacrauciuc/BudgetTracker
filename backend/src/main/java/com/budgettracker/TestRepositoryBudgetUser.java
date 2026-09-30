package com.budgettracker;

import java.util.*;

public class TestRepositoryBudgetUser {
    public static void main(String[] args) {
        IRepositoryBudgetUser repositoryBudgetUser = new RepositoryBudgetUser();

        Long numarInitial = repositoryBudgetUser.getNumarUtilizatori();
        System.out.println("Numar initial de utilizatori: " + numarInitial);

        BudgetUser newUser = new BudgetUser();
        newUser.setName("Test User");
        newUser.setUsername("test.user");
        newUser.setEmail("test.user@example.com");
        newUser.setPassword("testpass");
        newUser.setRegisterDate(new Date());

        repositoryBudgetUser.add(newUser);
        System.out.println("Utilizator nou adăugat: " + newUser.getUsername());

        BudgetUser foundUser = repositoryBudgetUser.getBudgetUserDupaUsername("test.user");
        System.out.println("Utilizator găsit după username: " + foundUser);

        if (foundUser != null) {
            foundUser.setEmail("new.test.email@example.com");
            repositoryBudgetUser.add(foundUser);
            System.out.println("Email actualizat: " + foundUser.getEmail());
        }

        System.out.println("Lista finală de utilizatori:");
        Collection<BudgetUser> allUsers = repositoryBudgetUser.getAll();
        for (BudgetUser user : allUsers) {
            System.out.println("ID: " + user.getUserId() + ", Nume: " + user.getName() + ", Email: " + user.getEmail());
        }

        if (foundUser != null) {
            repositoryBudgetUser.remove(foundUser);
            System.out.println("Utilizatorul " + foundUser.getUsername() + " a fost șters.");
        }


        Long numarFinal = repositoryBudgetUser.getNumarUtilizatori();
        System.out.println("Numar final de utilizatori: " + numarFinal);
    }
}