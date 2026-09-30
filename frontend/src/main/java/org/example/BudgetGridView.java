package org.example;

import com.budgettracker.Budget;
import org.example.services.BudgetService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Budget Management | Admin")
@Route(value = "budgets", layout = MainLayout.class)
public class BudgetGridView extends VerticalLayout {

    private BudgetService budgetService = new BudgetService();
    private List<Budget> budgetList = new ArrayList<>();
    private Grid<Budget> budgetGrid = new Grid<>(Budget.class, false);
    private Budget selectedBudget;

    private TextField searchField = new TextField();
    private Button btnEdit = new Button("Edit", VaadinIcon.EDIT.create());
    private Button btnAdd = new Button("Add Budget", VaadinIcon.PLUS.create());
    private Button btnDelete = new Button("Delete", VaadinIcon.TRASH.create());


    public BudgetGridView() {
        initDataModel();
        initViewLayout();
        initControllerActions();
    }

    private void initDataModel() {
        refreshData();
    }

    private void initViewLayout() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);

        VerticalLayout container = new VerticalLayout();
        container.setWidth("95%");
        container.setMaxWidth("1300px");

        container.getStyle()
                .set("background-color", "white")
                .set("border-radius", "25px")
                .set("padding", "35px")
                .set("box-shadow", "0 20px 50px rgba(35, 82, 82, 0.2)");

        H1 title = new H1("Budget Allocations Management");
        title.getStyle().set("color", "#235252").set("font-weight", "900");

        searchField.setPlaceholder("Filter by allocated amount...");
        searchField.setWidth("420px");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setValueChangeMode(ValueChangeMode.LAZY);

        styleAdminButton(btnAdd, "#235252");
        styleAdminButton(btnEdit, "#3e8e8e");
        styleAdminButton(btnDelete, "#a64444");

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);

        HorizontalLayout toolbar = new HorizontalLayout(searchField, new HorizontalLayout(btnEdit, btnAdd, btnDelete));
        toolbar.setWidthFull();
        toolbar.setAlignItems(Alignment.BASELINE);
        toolbar.setJustifyContentMode(JustifyContentMode.BETWEEN);
        toolbar.getStyle().set("padding", "10px 0");

        budgetGrid.addColumn(Budget::getBudgetId).setHeader("ID").setAutoWidth(true).setSortable(true);
        budgetGrid.addColumn(budget -> budget.getCategory() != null ?
                        budget.getCategory().getCategoryName() : "Unassigned")
                .setHeader("Category").setSortable(true).setAutoWidth(true);
        budgetGrid.addColumn(Budget::getAmount).setHeader("Amount (RON)").setSortable(true);
        budgetGrid.addColumn(Budget::getStartDate).setHeader("Start Date").setAutoWidth(true);
        budgetGrid.addColumn(Budget::getEndDate).setHeader("End Date").setAutoWidth(true);

        budgetGrid.addComponentColumn(this::createRowActions).setHeader("Actions").setAutoWidth(true);

        budgetGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        budgetGrid.getStyle().set("border-radius", "15px").set("overflow", "hidden");

        budgetGrid.asSingleSelect().addValueChangeListener(e -> {
            this.selectedBudget = e.getValue();
            btnEdit.setEnabled(this.selectedBudget != null);
            btnDelete.setEnabled(this.selectedBudget != null);
        });

        container.add(title, toolbar, budgetGrid);
        add(container);
    }

    private void styleAdminButton(Button btn, String color) {
        btn.getStyle()
                .set("background-color", color)
                .set("color", "white")
                .set("border-radius", "10px")
                .set("font-weight", "600")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.15)")
                .set("transition", "all 0.3s ease");

        btn.getElement().executeJs("this.onmouseover = () => { this.style.transform = 'translateY(-2px)'; };" +
                "this.onmouseout = () => { this.style.transform = 'translateY(0)'; };");
    }

    private Component createRowActions(Budget item) {
        Button edit = new Button(VaadinIcon.EDIT.create());
        edit.getStyle().set("color", "#235252");
        edit.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(BudgetFormView.class, item.getBudgetId())));

        Button delete = new Button(VaadinIcon.TRASH.create());
        delete.getStyle().set("color", "#a64444");
        delete.addClickListener(e -> processBudgetDeletion(item));

        HorizontalLayout actions = new HorizontalLayout(edit, delete);
        actions.setSpacing(true);
        return actions;
    }

    private void initControllerActions() {
        searchField.addValueChangeListener(e -> filterGridData());
        btnAdd.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(BudgetFormView.class, 9999)));
        btnEdit.addClickListener(e -> {
            if (this.selectedBudget != null) getUI().ifPresent(ui -> ui.navigate(BudgetFormView.class, selectedBudget.getBudgetId()));
        });
        btnDelete.addClickListener(e -> processBudgetDeletion(this.selectedBudget));
    }

    private void processBudgetDeletion(Budget item) {
        if (item != null) {
            budgetService.delete(item);
            refreshData();
        }
    }

    private void refreshData() {
        this.budgetList = budgetService.findAllBudgets();
        budgetGrid.setItems(budgetList);
    }

    private void filterGridData() {
        String filterValue = searchField.getValue();
        if (filterValue == null || filterValue.isEmpty()) {
            budgetGrid.setItems(budgetList);
        } else {
            budgetGrid.setItems(budgetList.stream()
                    .filter(b -> b.getAmount() != null && b.getAmount().toString().contains(filterValue))
                    .toList());
        }
    }

}