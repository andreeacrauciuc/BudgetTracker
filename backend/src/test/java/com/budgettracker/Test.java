package com.budgettracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import jakarta.persistence.*;
import java.util.*;

public class Test {
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static void main(String[] args) throws ParseException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProduseJPA");
        EntityManager em = emf.createEntityManager();

        List<BudgetUser> users = createUsers();
        List<Category> categories = createCategories();
        List<Account> accounts = createAccounts(users);
        List<SavingsAccount> savingsAccounts = createSavingsAccounts(users);
        List<Budget> budgets = createBudgets(categories);
        List<Loan> loans = createLoans(users);
        List<Balance> balances = createBalances(users);
        List<Transaction> transactions = createTransactions(users, accounts, categories);

        em.getTransaction().begin();

        try {

            em.createQuery("delete from Transaction").executeUpdate();
            em.createQuery("delete from Balance").executeUpdate();
            em.createQuery("delete from Budget").executeUpdate();
            em.createQuery("delete from Loan").executeUpdate();
            em.createQuery("delete from SavingsAccount").executeUpdate();
            em.createQuery("delete from Account").executeUpdate();
            em.createQuery("delete from Category").executeUpdate();
            em.createQuery("delete from BudgetUser").executeUpdate();


            System.out.println("--- Incep Persistarea Datelor Hardcodate ---");

            users.forEach(em::persist);
            categories.forEach(em::persist);
            accounts.forEach(em::persist);
            savingsAccounts.forEach(em::persist);
            budgets.forEach(em::persist);
            loans.forEach(em::persist);
            balances.forEach(em::persist);
            transactions.forEach(em::persist);

            em.getTransaction().commit();
            System.out.println("--- Persistarea a fost finalizată cu succes! ---");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Eroare la persistență: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }


    public static List<BudgetUser> createUsers() throws ParseException {
        List<BudgetUser> users = new ArrayList<>();


        BudgetUser u1 = new BudgetUser();
        u1.setName("Ion Popescu");
        u1.setUsername("ion.p");
        u1.setEmail("ion.p@mail.com");
        u1.setRegisterDate(FORMAT.parse("01-01-2024"));
        u1.setPassword("parola123");
        users.add(u1);


        BudgetUser u2 = new BudgetUser();
        u2.setName("Ana Maria");
        u2.setUsername("ana.m");
        u2.setEmail("ana.m@mail.com");
        u2.setRegisterDate(FORMAT.parse("15-02-2024"));
        u2.setPassword("secret456");
        users.add(u2);

        BudgetUser u3 = new BudgetUser();
        u3.setName("Andreea Crauciuc");
        u3.setUsername("andreea.c");
        u3.setEmail("andreea.c@gmail.com");
        u3.setRegisterDate(FORMAT.parse("13-07-2024"));
        u3.setPassword("andreea6347A");
        users.add(u3);

        BudgetUser u4 = new BudgetUser();
        u4.setName("Ioan B");
        u4.setUsername("ioan.b");
        u4.setEmail("ioan.b@gmail.com");
        u4.setRegisterDate(FORMAT.parse("15-12-2024"));
        u4.setPassword("ioan2311I");
        users.add(u4);

        BudgetUser u5 = new BudgetUser();
        u5.setName("Mihai Pop");
        u5.setUsername("mihai.p");
        u5.setEmail("mihai.p@gmail.com");
        u5.setRegisterDate(FORMAT.parse("10-03-2024"));
        u5.setPassword("mihai789");
        users.add(u5);

        BudgetUser u6 = new BudgetUser();
        u6.setName("Elena D");
        u6.setUsername("elena.d");
        u6.setEmail("elena.d@gmail.com");
        u6.setRegisterDate(FORMAT.parse("20-04-2024"));
        u6.setPassword("elena456");
        users.add(u6);

        BudgetUser u7 = new BudgetUser();
        u7.setName("Radu T");
        u7.setUsername("radu.t");
        u7.setEmail("radu.t@gmail.com");
        u7.setRegisterDate(FORMAT.parse("05-05-2024"));
        u7.setPassword("radu321");
        users.add(u7);

        BudgetUser u8 = new BudgetUser();
        u8.setName("Cristina M");
        u8.setUsername("cristina.m");
        u8.setEmail("cristina.m@gmail.com");
        u8.setRegisterDate(FORMAT.parse("25-06-2024"));
        u8.setPassword("cristina654");
        users.add(u8);

        BudgetUser u9 = new BudgetUser();
        u9.setName("Adrian V");
        u9.setUsername("adrian.v");
        u9.setEmail("adrian.v@gmail.com");
        u9.setRegisterDate(FORMAT.parse("11-08-2024"));
        u9.setPassword("adrian987");
        users.add(u9);

        BudgetUser u10 = new BudgetUser();
        u10.setName("Laura N");
        u10.setUsername("laura.n");
        u10.setEmail("laura.n@gmail.com");
        u10.setRegisterDate(FORMAT.parse("30-09-2024"));
        u10.setPassword("laura123");
        users.add(u10);

        return users;
    }

    public static List<Category> createCategories() {
        List<Category> categories = new ArrayList<>();


        Category c1 = new Category();
        c1.setCategoryName("Salariu");
        c1.setCategoryType(CategoryType.Income);
        categories.add(c1);


        Category c2 = new Category();
        c2.setCategoryName("Alimente");
        c2.setCategoryType(CategoryType.Expense);
        categories.add(c2);

        Category c3 = new Category();
        c3.setCategoryName("Machiaje");
        c3.setCategoryType(CategoryType.Expense);
        categories.add(c3);

        Category c4 = new Category();
        c4.setCategoryName("Imbracaminte");
        c4.setCategoryType(CategoryType.Expense);
        categories.add(c4);

        Category c5 = new Category();
        c5.setCategoryName("Transport");
        c5.setCategoryType(CategoryType.Expense);
        categories.add(c5);

        Category c6 = new Category();
        c6.setCategoryName("Divertisment");
        c6.setCategoryType(CategoryType.Expense);
        categories.add(c6);

        Category c7 = new Category();
        c7.setCategoryName("Investitii");
        c7.setCategoryType(CategoryType.Income);
        categories.add(c7);

        Category c8 = new Category();
        c8.setCategoryName("Cadouri");
        c8.setCategoryType(CategoryType.Expense);
        categories.add(c8);

        Category c9 = new Category();
        c9.setCategoryName("Facturi");
        c9.setCategoryType(CategoryType.Expense);
        categories.add(c9);

        Category c10 = new Category();
        c10.setCategoryName("Bonus");
        c10.setCategoryType(CategoryType.Income);
        categories.add(c10);

        return categories;
    }

    public static List<Account> createAccounts(List<BudgetUser> users) {
        List<Account> accounts = new ArrayList<>();

        Account a1 = new Account();
        a1.setAccountName("Cont Curent ING");
        a1.setActive(true);
        a1.setBudgetUser(users.get(0));
        accounts.add(a1);

        Account a2 = new Account();
        a2.setAccountName("Card de Credit BT");
        a2.setActive(true);
        a2.setBudgetUser(users.get(1));
        accounts.add(a2);

        Account a3 = new Account();
        a3.setAccountName("Cont Revolut");
        a3.setActive(true);
        a3.setBudgetUser(users.get(2));
        accounts.add(a3);

        Account a4 = new Account();
        a4.setAccountName("Card Credit ING");
        a4.setActive(true);
        a4.setBudgetUser(users.get(3));
        accounts.add(a4);

        Account a5 = new Account();
        a5.setAccountName("Cont Raiffeisen");
        a5.setActive(true);
        a5.setBudgetUser(users.get(4));
        accounts.add(a5);

        Account a6 = new Account();
        a6.setAccountName("Card Credit BT");
        a6.setActive(true);
        a6.setBudgetUser(users.get(5));
        accounts.add(a6);

        Account a7 = new Account();
        a7.setAccountName("Cont Alpha Bank");
        a7.setActive(true);
        a7.setBudgetUser(users.get(6));
        accounts.add(a7);

        Account a8 = new Account();
        a8.setAccountName("Card Credit Alpha");
        a8.setActive(true);
        a8.setBudgetUser(users.get(7));
        accounts.add(a8);

        Account a9 = new Account();
        a9.setAccountName("Cont Banca Transilvania");
        a9.setActive(true);
        a9.setBudgetUser(users.get(8));
        accounts.add(a9);

        Account a10 = new Account();
        a10.setAccountName("Card Credit BCR");
        a10.setActive(true);
        a10.setBudgetUser(users.get(9));
        accounts.add(a10);

        return accounts;
    }


    public static List<SavingsAccount> createSavingsAccounts(List<BudgetUser> users) {
        List<SavingsAccount> savings = new ArrayList<>();

        SavingsAccount s1 = new SavingsAccount();
        s1.setAccountName("Cont Economii Ion");
        s1.setActive(true);
        s1.setBudgetUser(users.get(0));
        s1.setBalance(5000.00);
        s1.setInterestRate(3.50);
        s1.setNumberOfAccounts(1);
        savings.add(s1);

        SavingsAccount s2 = new SavingsAccount();
        s2.setAccountName("Cont Economii Ana");
        s2.setActive(true);
        s2.setBudgetUser(users.get(1));
        s2.setBalance(3000.00);
        s2.setInterestRate(2.75);
        s2.setNumberOfAccounts(1);
        savings.add(s2);

        SavingsAccount s3 = new SavingsAccount();
        s3.setAccountName("Cont Economii Andreea");
        s3.setActive(true);
        s3.setBudgetUser(users.get(2));
        s3.setBalance(4000.00);
        s3.setInterestRate(3.00);
        s3.setNumberOfAccounts(1);
        savings.add(s3);

        SavingsAccount s4 = new SavingsAccount();
        s4.setAccountName("Cont Economii Ioan");
        s4.setActive(true);
        s4.setBudgetUser(users.get(3));
        s4.setBalance(3500.00);
        s4.setInterestRate(2.50);
        s4.setNumberOfAccounts(1);
        savings.add(s4);

        SavingsAccount s5 = new SavingsAccount();
        s5.setAccountName("Cont Economii Maria");
        s5.setActive(true);
        s5.setBudgetUser(users.get(4));
        s5.setBalance(4500.00);
        s5.setInterestRate(3.25);
        s5.setNumberOfAccounts(1);
        savings.add(s5);

        SavingsAccount s6 = new SavingsAccount();
        s6.setAccountName("Cont Economii Alex");
        s6.setActive(true);
        s6.setBudgetUser(users.get(5));
        s6.setBalance(2500.00);
        s6.setInterestRate(2.80);
        s6.setNumberOfAccounts(1);
        savings.add(s6);

        SavingsAccount s7 = new SavingsAccount();
        s7.setAccountName("Cont Economii Elena");
        s7.setActive(true);
        s7.setBudgetUser(users.get(6));
        s7.setBalance(3800.00);
        s7.setInterestRate(3.10);
        s7.setNumberOfAccounts(1);
        savings.add(s7);

        SavingsAccount s8 = new SavingsAccount();
        s8.setAccountName("Cont Economii Bogdan");
        s8.setActive(true);
        s8.setBudgetUser(users.get(7));
        s8.setBalance(4200.00);
        s8.setInterestRate(3.20);
        s8.setNumberOfAccounts(1);
        savings.add(s8);

        SavingsAccount s9 = new SavingsAccount();
        s9.setAccountName("Cont Economii Cristina");
        s9.setActive(true);
        s9.setBudgetUser(users.get(8));
        s9.setBalance(3100.00);
        s9.setInterestRate(2.90);
        s9.setNumberOfAccounts(1);
        savings.add(s9);

        SavingsAccount s10 = new SavingsAccount();
        s10.setAccountName("Cont Economii Mihai");
        s10.setActive(true);
        s10.setBudgetUser(users.get(9));
        s10.setBalance(4600.00);
        s10.setInterestRate(3.35);
        s10.setNumberOfAccounts(1);
        savings.add(s10);

        return savings;
    }

    public static List<Budget> createBudgets(List<Category> categories) throws ParseException {
        List<Budget> budgets = new ArrayList<>();

        Budget b1 = new Budget();
        b1.setAmount(1000.00);
        b1.setStartDate(FORMAT.parse("01-11-2025"));
        b1.setEndDate(FORMAT.parse("30-11-2025"));
        b1.setCategory(categories.get(1));
        budgets.add(b1);

        Budget b2 = new Budget();
        b2.setAmount(500.00);
        b2.setStartDate(FORMAT.parse("01-11-2025"));
        b2.setEndDate(FORMAT.parse("30-11-2025"));
        b2.setCategory(categories.get(3));
        budgets.add(b2);

        Budget b3 = new Budget();
        b3.setAmount(300.00);
        b3.setStartDate(FORMAT.parse("01-11-2025"));
        b3.setEndDate(FORMAT.parse("30-11-2025"));
        b3.setCategory(categories.get(5));
        budgets.add(b3);

        Budget b4 = new Budget();
        b4.setAmount(2000.00);
        b4.setStartDate(FORMAT.parse("01-11-2025"));
        b4.setEndDate(FORMAT.parse("30-11-2025"));
        b4.setCategory(categories.get(0));
        budgets.add(b4);

        Budget b5 = new Budget();
        b5.setAmount(150.00);
        b5.setStartDate(FORMAT.parse("01-11-2025"));
        b5.setEndDate(FORMAT.parse("30-11-2025"));
        b5.setCategory(categories.get(8));
        budgets.add(b5);

        Budget b6 = new Budget();
        b6.setAmount(120.00);
        b6.setStartDate(FORMAT.parse("01-11-2025"));
        b6.setEndDate(FORMAT.parse("30-11-2025"));
        b6.setCategory(categories.get(2));
        budgets.add(b6);

        Budget b7 = new Budget();
        b7.setAmount(800.00);
        b7.setStartDate(FORMAT.parse("01-11-2025"));
        b7.setEndDate(FORMAT.parse("30-11-2025"));
        b7.setCategory(categories.get(4));
        budgets.add(b7);

        Budget b8 = new Budget();
        b8.setAmount(600.00);
        b8.setStartDate(FORMAT.parse("01-11-2025"));
        b8.setEndDate(FORMAT.parse("30-11-2025"));
        b8.setCategory(categories.get(7));
        budgets.add(b8);

        Budget b9 = new Budget();
        b9.setAmount(700.00);
        b9.setStartDate(FORMAT.parse("01-11-2025"));
        b9.setEndDate(FORMAT.parse("30-11-2025"));
        b9.setCategory(categories.get(6));
        budgets.add(b9);

        Budget b10 = new Budget();
        b10.setAmount(450.00);
        b10.setStartDate(FORMAT.parse("01-11-2025"));
        b10.setEndDate(FORMAT.parse("30-11-2025"));
        b10.setCategory(categories.get(9));
        budgets.add(b10);

        return budgets;
    }

    public static List<Loan> createLoans(List<BudgetUser> users) throws ParseException {
        List<Loan> loans = new ArrayList<>();

        Loan l1 = new Loan();
        l1.setDescription("Datorie chirie Bogdan");
        l1.setAmount(500.00);
        l1.setPayee(users.get(0));
        l1.setPayer("Bogdan Ionescu");
        l1.setReturnDate(FORMAT.parse("20-12-2025"));
        loans.add(l1);

        Loan l2 = new Loan();
        l2.setDescription("Datorie prieten Ana");
        l2.setAmount(300.00);
        l2.setPayee(users.get(1));
        l2.setPayer("Mihai Popa");
        l2.setReturnDate(FORMAT.parse("15-12-2025"));
        loans.add(l2);

        Loan l3 = new Loan();
        l3.setDescription("Datorie cafea Andreea");
        l3.setAmount(50.00);
        l3.setPayee(users.get(2));
        l3.setPayer("Elena Ionescu");
        l3.setReturnDate(FORMAT.parse("10-12-2025"));
        loans.add(l3);

        Loan l4 = new Loan();
        l4.setDescription("Datorie cadou Ioan");
        l4.setAmount(120.00);
        l4.setPayee(users.get(3));
        l4.setPayer("Alex Popescu");
        l4.setReturnDate(FORMAT.parse("18-12-2025"));
        loans.add(l4);

        Loan l5 = new Loan();
        l5.setDescription("Datorie vacanță Maria");
        l5.setAmount(800.00);
        l5.setPayee(users.get(4));
        l5.setPayer("Andrei Vlad");
        l5.setReturnDate(FORMAT.parse("25-12-2025"));
        loans.add(l5);

        Loan l6 = new Loan();
        l6.setDescription("Datorie transport Alex");
        l6.setAmount(60.00);
        l6.setPayee(users.get(5));
        l6.setPayer("Cristina Dima");
        l6.setReturnDate(FORMAT.parse("12-12-2025"));
        loans.add(l6);

        Loan l7 = new Loan();
        l7.setDescription("Datorie petrecere Elena");
        l7.setAmount(150.00);
        l7.setPayee(users.get(6));
        l7.setPayer("Bogdan Pop");
        l7.setReturnDate(FORMAT.parse("20-12-2025"));
        loans.add(l7);

        Loan l8 = new Loan();
        l8.setDescription("Datorie gadget Bogdan");
        l8.setAmount(400.00);
        l8.setPayee(users.get(7));
        l8.setPayer("Ana Georgescu");
        l8.setReturnDate(FORMAT.parse("22-12-2025"));
        loans.add(l8);

        Loan l9 = new Loan();
        l9.setDescription("Datorie hobby Cristina");
        l9.setAmount(250.00);
        l9.setPayee(users.get(8));
        l9.setPayer("Mihai Georgescu");
        l9.setReturnDate(FORMAT.parse("28-12-2025"));
        loans.add(l9);

        Loan l10 = new Loan();
        l10.setDescription("Datorie gadget Mihai");
        l10.setAmount(350.00);
        l10.setPayee(users.get(9));
        l10.setPayer("Elena Vlad");
        l10.setReturnDate(FORMAT.parse("30-12-2025"));
        loans.add(l10);

        return loans;
    }

    public static List<Transaction> createTransactions(List<BudgetUser> users, List<Account> accounts, List<Category> categories) throws ParseException {
        List<Transaction> transactions = new ArrayList<>();

         Transaction t1 = new Transaction();
        t1.setTransactionType(TransactionType.Income);
        t1.setDescription("Salariu Noiembrie");
        t1.setAmount(4500.00);
        t1.setDate(FORMAT.parse("15-11-2025"));
        t1.setBudgetUser(users.get(0));
        t1.setAccount(accounts.get(0));
        t1.setCategory(categories.get(0));
        transactions.add(t1);

        Transaction t2 = new Transaction();
        t2.setTransactionType(TransactionType.Expense);
        t2.setDescription("Cumpărături Mega Image");
        t2.setAmount(150.50);
        t2.setDate(FORMAT.parse("18-11-2025"));
        t2.setBudgetUser(users.get(1));
        t2.setAccount(accounts.get(1));
        t2.setCategory(categories.get(1));
        transactions.add(t2);

        Transaction t3 = new Transaction();
        t3.setTransactionType(TransactionType.Income);
        t3.setDescription("Bonus Performanță");
        t3.setAmount(600.00);
        t3.setDate(FORMAT.parse("20-11-2025"));
        t3.setBudgetUser(users.get(2));
        t3.setAccount(accounts.get(0));
        t3.setCategory(categories.get(0));
        transactions.add(t3);

        Transaction t4 = new Transaction();
        t4.setTransactionType(TransactionType.Expense);
        t4.setDescription("Restaurant");
        t4.setAmount(120.75);
        t4.setDate(FORMAT.parse("22-11-2025"));
        t4.setBudgetUser(users.get(3));
        t4.setAccount(accounts.get(1));
        t4.setCategory(categories.get(1));
        transactions.add(t4);

        Transaction t5 = new Transaction();
        t5.setTransactionType(TransactionType.Income);
        t5.setDescription("Vânzare obiecte");
        t5.setAmount(350.00);
        t5.setDate(FORMAT.parse("10-11-2025"));
        t5.setBudgetUser(users.get(4));
        t5.setAccount(accounts.get(0));
        t5.setCategory(categories.get(0));
        transactions.add(t5);

        Transaction t6 = new Transaction();
        t6.setTransactionType(TransactionType.Expense);
        t6.setDescription("Transport");
        t6.setAmount(75.00);
        t6.setDate(FORMAT.parse("12-11-2025"));
        t6.setBudgetUser(users.get(5));
        t6.setAccount(accounts.get(1));
        t6.setCategory(categories.get(1));
        transactions.add(t6);

        Transaction t7 = new Transaction();
        t7.setTransactionType(TransactionType.Income);
        t7.setDescription("Investiții");
        t7.setAmount(800.00);
        t7.setDate(FORMAT.parse("05-11-2025"));
        t7.setBudgetUser(users.get(6));
        t7.setAccount(accounts.get(0));
        t7.setCategory(categories.get(7)); // Investitii
        transactions.add(t7);

        Transaction t8 = new Transaction();
        t8.setTransactionType(TransactionType.Expense);
        t8.setDescription("Cadouri Crăciun");
        t8.setAmount(200.00);
        t8.setDate(FORMAT.parse("18-11-2025"));
        t8.setBudgetUser(users.get(7));
        t8.setAccount(accounts.get(1));
        t8.setCategory(categories.get(8)); // Cadouri
        transactions.add(t8);

        Transaction t9 = new Transaction();
        t9.setTransactionType(TransactionType.Income);
        t9.setDescription("Proiect freelance");
        t9.setAmount(1200.00);
        t9.setDate(FORMAT.parse("25-11-2025"));
        t9.setBudgetUser(users.get(8));
        t9.setAccount(accounts.get(0));
        t9.setCategory(categories.get(0));
        transactions.add(t9);

        Transaction t10 = new Transaction();
        t10.setTransactionType(TransactionType.Expense);
        t10.setDescription("Divertisment cinema");
        t10.setAmount(60.00);
        t10.setDate(FORMAT.parse("28-11-2025"));
        t10.setBudgetUser(users.get(9));
        t10.setAccount(accounts.get(1));
        t10.setCategory(categories.get(6)); // Divertisment
        transactions.add(t10);

        return transactions;
    }

    public static List<Balance> createBalances(List<BudgetUser> users) throws ParseException {

        List<Balance> balances = new ArrayList<>();
        Balance b1 = new Balance();
        b1.setBudgetUser(users.get(0));
        b1.setStartDate(FORMAT.parse("01-11-2025"));
        b1.setEndDate(FORMAT.parse("30-11-2025"));
        b1.setCreditAmount(500.00);
        b1.setDebitAmount(4500.00);
        balances.add(b1);

        Balance b2 = new Balance();
        b2.setBudgetUser(users.get(1));
        b2.setStartDate(FORMAT.parse("01-11-2025"));
        b2.setEndDate(FORMAT.parse("30-11-2025"));
        b2.setCreditAmount(200.00);
        b2.setDebitAmount(3000.00);
        balances.add(b2);

        Balance b3 = new Balance();
        b3.setBudgetUser(users.get(2));
        b3.setStartDate(FORMAT.parse("01-11-2025"));
        b3.setEndDate(FORMAT.parse("30-11-2025"));
        b3.setCreditAmount(150.00);
        b3.setDebitAmount(2500.00);
        balances.add(b3);

        Balance b4 = new Balance();
        b4.setBudgetUser(users.get(3));
        b4.setStartDate(FORMAT.parse("01-11-2025"));
        b4.setEndDate(FORMAT.parse("30-11-2025"));
        b4.setCreditAmount(100.00);
        b4.setDebitAmount(2000.00);
        balances.add(b4);

        Balance b5 = new Balance();
        b5.setBudgetUser(users.get(4));
        b5.setStartDate(FORMAT.parse("01-11-2025"));
        b5.setEndDate(FORMAT.parse("30-11-2025"));
        b5.setCreditAmount(250.00);
        b5.setDebitAmount(3500.00);
        balances.add(b5);

        Balance b6 = new Balance();
        b6.setBudgetUser(users.get(5));
        b6.setStartDate(FORMAT.parse("01-11-2025"));
        b6.setEndDate(FORMAT.parse("30-11-2025"));
        b6.setCreditAmount(300.00);
        b6.setDebitAmount(4000.00);
        balances.add(b6);

        Balance b7 = new Balance();
        b7.setBudgetUser(users.get(6));
        b7.setStartDate(FORMAT.parse("01-11-2025"));
        b7.setEndDate(FORMAT.parse("30-11-2025"));
        b7.setCreditAmount(120.00);
        b7.setDebitAmount(2800.00);
        balances.add(b7);

        Balance b8 = new Balance();
        b8.setBudgetUser(users.get(7));
        b8.setStartDate(FORMAT.parse("01-11-2025"));
        b8.setEndDate(FORMAT.parse("30-11-2025"));
        b8.setCreditAmount(180.00);
        b8.setDebitAmount(3200.00);
        balances.add(b8);

        Balance b9 = new Balance();
        b9.setBudgetUser(users.get(8));
        b9.setStartDate(FORMAT.parse("01-11-2025"));
        b9.setEndDate(FORMAT.parse("30-11-2025"));
        b9.setCreditAmount(400.00);
        b9.setDebitAmount(5000.00);
        balances.add(b9);

        Balance b10 = new Balance();
        b10.setBudgetUser(users.get(9));
        b10.setStartDate(FORMAT.parse("01-11-2025"));
        b10.setEndDate(FORMAT.parse("30-11-2025"));
        b10.setCreditAmount(220.00);
        b10.setDebitAmount(3300.00);
        balances.add(b10);

        return balances;
    }
}