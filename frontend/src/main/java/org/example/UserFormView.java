package org.example;

import com.budgettracker.BudgetUser;
import org.example.services.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.*;

@PageTitle("User Profile | Admin")
@Route(value = "user-details", layout = MainLayout.class)
public class UserFormView extends VerticalLayout implements HasUrlParameter<Integer> {

    private UserService userService = new UserService();
    private BudgetUser currentUser;
    private Binder<BudgetUser> binder = new Binder<>(BudgetUser.class);

    private H1 viewTitle = new H1("Customer Profile Editor");
    private TextField username = new TextField("Username:");
    private TextField name = new TextField("Full Name:");
    private TextField email = new TextField("Email Address:");

    private Button btnCreate = new Button("Create New");
    private Button btnDelete = new Button("Remove");
    private Button btnCancel = new Button("Cancel");
    private Button btnSave = new Button("Save Changes");

    public UserFormView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout card = new VerticalLayout();
        card.setWidth("700px");
        card.getStyle()
                .set("background-color", "white")
                .set("border-radius", "30px")
                .set("padding", "50px")
                .set("box-shadow", "0 25px 55px rgba(35, 82, 82, 0.25)")
                .set("transition", "transform 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275)");

        card.getElement().executeJs("this.onmouseover = () => { this.style.transform = 'translateY(-15px)'; }; " +
                "this.onmouseout = () => { this.style.transform = 'translateY(0)'; };");

        viewTitle.getStyle().set("color", "#235252").set("font-weight", "900");

        FormLayout formLayout = new FormLayout(username, name, email);
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
        toolbar.setWidthFull();
        toolbar.getStyle().set("margin-top", "30px");
        toolbar.setJustifyContentMode(JustifyContentMode.CENTER);

        card.add(viewTitle, formLayout, toolbar);
        add(card);

        binder.forField(username)
                .asRequired("Username cannot be empty")
                .withValidator(un -> un.length() >= 3, "Username must be at least 3 characters")
                .bind(BudgetUser::getUsername, BudgetUser::setUsername);

        binder.forField(name)
                .asRequired("Full name is required")
                .bind(BudgetUser::getName, BudgetUser::setName);

        binder.forField(email)
                .asRequired("Email is mandatory")
                .withValidator(new com.vaadin.flow.data.validator.EmailValidator("Please enter a valid email address"))
                .withValidator(e -> !userService.emailExists(e, currentUser.getUserId()),
                        "This email is already taken!")
                .bind(com.budgettracker.BudgetUser::getEmail, com.budgettracker.BudgetUser::setEmail);

        btnCreate.addClickListener(e -> {
            this.currentUser = new BudgetUser();
            binder.setBean(this.currentUser);
            Notification.show("Form cleared for new entry.");
        });

        btnSave.addClickListener(e -> saveCustomerData());
        btnCancel.addClickListener(e -> navigateBack());
        btnDelete.addClickListener(e -> deleteCustomer());
    }

    private void saveCustomerData() {
        if (binder.validate().isOk()) {
            userService.save(this.currentUser);
            Notification notification = Notification.show("Profile updated successfully!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            navigateBack();
        } else {
            Notification notification = Notification.show("Please correct the errors in the form.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void deleteCustomer() {
        if (this.currentUser != null && this.currentUser.getUserId() != null) {
            userService.delete(this.currentUser);
            Notification.show("User removed from system.");
            navigateBack();
        }
    }

    private void navigateBack() {
        getUI().ifPresent(ui -> ui.navigate(UserGridView.class));
    }

    private void styleButton3D(Button btn, String bg, String text) {
        btn.getStyle()
                .set("background-color", bg).set("color", text)
                .set("border-radius", "12px").set("font-weight", "bold")
                .set("box-shadow", "0 4px 0px rgba(0,0,0,0.2)")
                .set("transition", "all 0.2s ease");

        btn.getElement().executeJs(
                "this.onmouseover = () => { this.style.transform = 'translateY(-2px)'; this.style.boxShadow = '0 6px 0px rgba(0,0,0,0.2)'; };" +
                        "this.onmouseout = () => { this.style.transform = 'translateY(0)'; this.style.boxShadow = '0 4px 0px rgba(0,0,0,0.2)'; };" +
                        "this.onmousedown = () => { this.style.transform = 'translateY(2px)'; this.style.boxShadow = 'none'; };"
        );
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer id) {
        if (id != null && id != 9999) {
            this.currentUser = userService.findById(id);

            if (this.currentUser == null) {
                this.currentUser = new BudgetUser();
            }
        } else {
            this.currentUser = new BudgetUser();
        }
        binder.setBean(this.currentUser);
    }
}