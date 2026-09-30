package com.budgettracker;
import java.util.Collection;
import java.util.List;

public interface IRepositoryBudgetUser {
        void add(BudgetUser user);
        void remove(BudgetUser user);
        void refresh(BudgetUser user);
        void removeAll();

        BudgetUser getBudgetUserDupaID(Integer userId);
        Collection<BudgetUser> getAll();
        Long getNumarUtilizatori();
        BudgetUser getBudgetUserDupaUsername(String username);
        List<BudgetUser> getUtilizatoriDupaNume(String name);
        BudgetUser getBudgetUserDupaEmail(String email);
    }

