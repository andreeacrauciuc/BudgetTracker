package org.example;

import com.budgettracker.Budget;
import com.budgettracker.Category;
import org.example.services.BudgetService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;

@PageTitle("Budget Editor | Admin")
@Route(value = "budget", layout = MainLayout.class)
public class BudgetFormView extends VerticalLayout implements HasUrlParameter<Integer> {

    private BudgetService budgetService = new BudgetService();
    private Budget currentBudget;
    private Binder<Budget> binder = new Binder<>(Budget.class);

    private H1 viewTitle = new H1("Budget Configuration");
    private ComboBox<Category> categorySelector = new ComboBox<>("Target Category:");
    private NumberField amountField = new NumberField("Allocated Amount:");
    private DatePicker startDatePicker = new DatePicker("Effective From:");
    private DatePicker endDatePicker = new DatePicker("Effective Until:");

    private Button btnCreate = new Button("Create New");
    private Button btnDelete = new Button("Remove");
    private Button btnCancel = new Button("Cancel");
    private Button btnSave = new Button("Save Changes");

    public BudgetFormView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout cardContainer = new VerticalLayout();
        cardContainer.setWidth("850px");
        cardContainer.getStyle()
                .set("background-color", "white")
                .set("border-left", "12px solid #235252")
                .set("border-radius", "30px")
                .set("padding", "60px")
                .set("box-shadow", "0 30px 60px rgba(35, 82, 82, 0.3)");

        cardContainer.getElement().executeJs("this.onmouseover = () => { this.style.transform = 'translateY(-12px)'; }; " +
                "this.onmouseout = () => { this.style.transform = 'translateY(0)'; };");

        viewTitle.getStyle().set("color", "#235252").set("font-weight", "bold");

        categorySelector.setItems(budgetService.findAllCategories());
        categorySelector.setItemLabelGenerator(Category::getCategoryName);

        FormLayout formLayout = new FormLayout(categorySelector, amountField, startDatePicker, endDatePicker);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("600px", 2)
        );

        styleButton3D(btnSave, "#235252", "white");
        styleButton3D(btnCreate, "#3e8e8e", "white");
        styleButton3D(btnDelete, "#a64444", "white");
        styleButton3D(btnCancel, "#6c757d", "white");

        HorizontalLayout toolbar = new HorizontalLayout(btnCreate, btnDelete, btnCancel, btnSave);
        toolbar.setSpacing(true);
        toolbar.setPadding(true);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(JustifyContentMode.CENTER);
        toolbar.getStyle().set("margin-top", "40px");

        cardContainer.add(viewTitle, formLayout, toolbar);
        add(cardContainer);

        binder.forField(categorySelector)
                .asRequired("Please select a category")
                .bind(Budget::getCategory, Budget::setCategory);

        binder.forField(amountField)
                .asRequired("Amount is mandatory")
                .withValidator(amt -> amt > 0, "Amount must be greater than 0")
                .bind(Budget::getAmount, Budget::setAmount);

        binder.forField(startDatePicker)
                .withConverter(d -> d == null ? null : java.sql.Date.valueOf(d),
                        d -> d == null ? null : ((java.sql.Date)d).toLocalDate())
                .bind("startDate");

        binder.forField(endDatePicker)
                .withConverter(d -> d == null ? null : java.sql.Date.valueOf(d),
                        d -> d == null ? null : ((java.sql.Date)d).toLocalDate())
                .bind("endDate");

        btnCreate.addClickListener(e -> {
            this.currentBudget = new Budget();
            binder.setBean(this.currentBudget);
            Notification.show("Form cleared for new budget entry.");
        });

        btnSave.addClickListener(e -> saveBudget());
        btnCancel.addClickListener(e -> navigateBack());
        btnDelete.addClickListener(e -> deleteBudget());
    }

    private void saveBudget() {
        if (this.currentBudget != null) {
            budgetService.save(this.currentBudget);
            Notification.show("Budget allocation saved successfully!");
            navigateBack();
        }
    }

    private void deleteBudget() {
        if (this.currentBudget != null && this.currentBudget.getBudgetId() != null) {
            budgetService.delete(this.currentBudget);
            Notification.show("Budget record removed.");
            navigateBack();
        }
    }

    private void navigateBack() {
        getUI().ifPresent(ui -> ui.navigate(BudgetGridView.class));
    }

    private void styleButton3D(Button btn, String bg, String text) {
        btn.getStyle()
                .set("background-color", bg).set("color", text)
                .set("border-radius", "10px").set("font-weight", "bold")
                .set("box-shadow", "0 4px 0px rgba(0,0,0,0.15)")
                .set("transition", "all 0.2s ease");

        btn.getElement().executeJs(
                "this.onmouseover = () => { this.style.transform = 'translateY(-2px)'; };" +
                        "this.onmouseout = () => { this.style.transform = 'translateY(0)'; };" +
                        "this.onmousedown = () => { this.style.transform = 'translateY(2px)'; this.style.boxShadow = 'none'; };"
        );
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer id) {
        this.currentBudget = (id != null && id != 9999) ? budgetService.findById(id) : new Budget();
        binder.setBean(this.currentBudget);
    }
}