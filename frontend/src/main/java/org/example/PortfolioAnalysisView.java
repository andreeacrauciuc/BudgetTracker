package org.example;

import com.budgettracker.Account;
import com.budgettracker.BudgetUser;
import com.budgettracker.Transaction;
import org.example.services.PortfolioService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.progressbar.ProgressBarVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Portfolio Analysis | Admin")
@Route(value = "portfolio-analysis", layout = MainLayout.class)
public class PortfolioAnalysisView extends VerticalLayout {

    private PortfolioService portfolioService = new PortfolioService();
    private ComboBox<BudgetUser> customerSelector = new ComboBox<>("1. Select Customer");
    private ComboBox<Account> accountSelector = new ComboBox<>("2. Select Account");
    private Grid<Transaction> transactionGrid = new Grid<>(Transaction.class, false);

    private Span totalIncomeLabel = new Span("📈 Income: 0.00 RON");
    private Span totalExpenseLabel = new Span("📉 Expense: 0.00 RON");

    private ProgressBar healthBar = new ProgressBar();
    private Span healthText = new Span("Expense Ratio: 0%");

    public PortfolioAnalysisView() {
        setupViewLayout();
        setupBusinessLogic();
    }

    private void setupViewLayout() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);

        VerticalLayout contentCard = new VerticalLayout();
        contentCard.setWidth("95%");
        contentCard.setMaxWidth("1100px");
        contentCard.setPadding(true);
        contentCard.getStyle()
                .set("background-color", "white")
                .set("border-radius", "25px")
                .set("padding", "20px 40px")
                .set("box-shadow", "0 20px 45px rgba(35, 82, 82, 0.2)")
                .set("margin-top", "10px");

        H2 viewTitle = new H2("Master-Detail Transaction Management");
        viewTitle.getStyle()
                .set("color", "#235252")
                .set("font-weight", "900")
                .set("font-size", "1.4rem")
                .set("margin", "0");

        customerSelector.setWidth("300px");
        accountSelector.setWidth("300px");

        HorizontalLayout selectionLayout = new HorizontalLayout(customerSelector, accountSelector);
        selectionLayout.setSpacing(true);
        selectionLayout.setWidthFull();
        selectionLayout.setJustifyContentMode(JustifyContentMode.START);

        styleSummaryLabel(totalIncomeLabel, "#235252", "#eef7f7");
        styleSummaryLabel(totalExpenseLabel, "#a64444", "#fdf2f2");

        HorizontalLayout totalsPanel = new HorizontalLayout(totalIncomeLabel, totalExpenseLabel);
        totalsPanel.setJustifyContentMode(JustifyContentMode.CENTER);
        totalsPanel.setWidthFull();
        totalsPanel.getStyle().set("margin", "5px 0");

        healthBar.setWidth("100%");
        healthBar.setHeight("12px");
        healthBar.setValue(0);
        healthBar.getStyle().set("border-radius", "10px").set("margin-top", "5px");

        healthText.getStyle()
                .set("font-size", "0.85rem")
                .set("font-weight", "700")
                .set("margin-top", "10px")
                .set("color", "#557070");

        VerticalLayout healthSection = new VerticalLayout(healthText, healthBar);
        healthSection.setPadding(false);
        healthSection.setSpacing(false);
        healthSection.setAlignItems(Alignment.CENTER);
        healthSection.getStyle().set("margin-bottom", "15px");

        transactionGrid.addColumn(Transaction::getTransactionId).setHeader("ID").setAutoWidth(true);
        transactionGrid.addColumn(Transaction::getTransactionType).setHeader("Type").setAutoWidth(true);
        transactionGrid.addColumn(t -> t.getCategory() != null ? t.getCategory().getCategoryName() : "Uncategorized").setHeader("Category");
        transactionGrid.addColumn(Transaction::getAmount).setHeader("Amount (RON)").setAutoWidth(true);
        transactionGrid.addColumn(Transaction::getDate).setHeader("Date").setAutoWidth(true);

        transactionGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
        transactionGrid.setHeight("350px");
        transactionGrid.getStyle().set("border-radius", "12px").set("border", "1px solid #eef2f2");

        H3 sectionHeader = new H3("Transaction Records:");
        sectionHeader.getStyle().set("font-size", "1rem").set("margin", "5px 0");

        contentCard.add(viewTitle, selectionLayout, totalsPanel, healthSection, sectionHeader, transactionGrid);
        add(contentCard);

        customerSelector.setItems(portfolioService.findAllCustomers());
        customerSelector.setItemLabelGenerator(BudgetUser::getName);
        accountSelector.setItemLabelGenerator(Account::getAccountName);
        accountSelector.setEnabled(false);
    }

    private void styleSummaryLabel(Span label, String color, String bgColor) {
        label.getStyle()
                .set("font-size", "0.95rem")
                .set("font-weight", "800")
                .set("color", color)
                .set("padding", "8px 25px")
                .set("background-color", bgColor)
                .set("border-radius", "12px")
                .set("border", "1px solid " + color)
                .set("box-shadow", "0 2px 5px rgba(0,0,0,0.05)")
                .set("min-width", "220px")
                .set("text-align", "center");
    }

    private void setupBusinessLogic() {
        customerSelector.addValueChangeListener(event -> {
            BudgetUser selectedUser = event.getValue();
            if (selectedUser != null) {
                accountSelector.setItems(portfolioService.findAccountsByCustomerId(selectedUser.getUserId()));
                accountSelector.setEnabled(true);
                transactionGrid.setItems(new ArrayList<>());
                resetSummaryLabels();
            } else {
                accountSelector.setEnabled(false);
                accountSelector.clear();
            }
        });

        accountSelector.addValueChangeListener(event -> {
            Account selectedAccount = event.getValue();
            if (selectedAccount != null) {
                List<Transaction> transactions = portfolioService.findTransactionsByAccountId(selectedAccount.getAccountId());
                transactionGrid.setItems(transactions);
                updateFinancialTotals(transactions);
            }
        });
    }

    private void updateFinancialTotals(List<Transaction> transactions) {
        PortfolioService.FinancialSummary summary = portfolioService.getFinancialSummary(transactions);
        double income = summary.totalIncome();
        double expense = summary.totalExpense();

        totalIncomeLabel.setText(String.format("📈 Income: %.2f RON", income));
        totalExpenseLabel.setText(String.format("📉 Expense: %.2f RON", expense));

        double ratio = (income > 0) ? (expense / income) : (expense > 0 ? 1.0 : 0.0);
        healthBar.setValue(Math.min(ratio, 1.0));

        healthBar.removeThemeVariants(ProgressBarVariant.LUMO_ERROR, ProgressBarVariant.LUMO_SUCCESS);

        if (ratio >= 0.8) {
            healthBar.addThemeVariants(ProgressBarVariant.LUMO_ERROR);
            healthText.setText(String.format("Critical Ratio: %.0f%% - Over budget risk!", ratio * 100));
            healthText.getStyle().set("color", "#a64444");
        } else if (ratio > 0) {
            healthBar.addThemeVariants(ProgressBarVariant.LUMO_SUCCESS);
            healthText.setText(String.format("Healthy Ratio: %.0f%%", ratio * 100));
            healthText.getStyle().set("color", "#235252");
        } else {
            healthText.setText("Expense Ratio: 0% (No spending detected)");
            healthText.getStyle().set("color", "#557070");
        }
    }

    private void resetSummaryLabels() {
        totalIncomeLabel.setText("📈 Income: 0.00 RON");
        totalExpenseLabel.setText("📉 Expense: 0.00 RON");
        healthBar.setValue(0);
        healthText.setText("Expense Ratio: 0%");
        healthText.getStyle().set("color", "#557070");
        healthBar.removeThemeVariants(ProgressBarVariant.LUMO_ERROR, ProgressBarVariant.LUMO_SUCCESS);
    }
}