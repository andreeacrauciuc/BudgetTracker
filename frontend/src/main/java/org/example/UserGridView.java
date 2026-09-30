package org.example;
import org.example.services.UserService;
import com.budgettracker.BudgetUser;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@PageTitle("User Management | Admin")
@Route(value = "users", layout = MainLayout.class)
public class UserGridView extends VerticalLayout implements HasUrlParameter<Integer> {

    private UserService userService = new UserService();
    private List<BudgetUser> userList = new ArrayList<>();
    private BudgetUser selectedUser = null;

    private H1 viewTitle = new H1("User Administration Dashboard");
    private TextField filterText = new TextField();

    private Button btnEditUser = new Button("Edit", VaadinIcon.EDIT.create());
    private Button btnAddUser = new Button("Add User", VaadinIcon.PLUS.create());
    private Button btnDeleteUser = new Button("Delete", VaadinIcon.TRASH.create());

    private Grid<BudgetUser> grid = new Grid<>(BudgetUser.class, false);

    public UserGridView() {
        initDataModel();
        initViewLayout();
        initControllerActions();
    }

    private void initDataModel() {
        refreshGridData();
    }

    private void initViewLayout() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        VerticalLayout card = new VerticalLayout();
        card.setWidth("95%");
        card.setMaxWidth("1200px");
        card.getStyle().set("background-color", "white").set("border-radius", "25px").set("padding", "35px")
                .set("box-shadow", "0 15px 40px rgba(35, 82, 82, 0.15)");

        viewTitle.getStyle().set("color", "#235252").set("font-weight", "800");

        filterText.setPlaceholder("Search by name or username...");
        filterText.setWidth("400px");
        filterText.setPrefixComponent(VaadinIcon.SEARCH.create());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        btnAddUser.getStyle().set("background-color", "#235252").set("color", "white").set("border-radius", "10px");
        btnDeleteUser.getStyle().set("background-color", "#a64444").set("color", "white").set("border-radius", "10px");
        btnEditUser.getStyle().set("color", "#235252");

        HorizontalLayout actionGroup = new HorizontalLayout(btnEditUser, btnAddUser, btnDeleteUser);
        actionGroup.setSpacing(true);

        HorizontalLayout toolbar = new HorizontalLayout(filterText, actionGroup);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(JustifyContentMode.BETWEEN);

        grid.addColumn(BudgetUser::getUserId).setHeader("ID").setAutoWidth(true).setSortable(true);
        grid.addColumn(BudgetUser::getUsername).setHeader("Username").setSortable(true);
        grid.addColumn(BudgetUser::getName).setHeader("Full Name").setSortable(true);
        grid.addColumn(BudgetUser::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addComponentColumn(item -> createGridActionsButtons(item)).setHeader("Actions").setFrozenToEnd(true);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);
        grid.getStyle().set("border-radius", "15px").set("overflow", "hidden");
        com.vaadin.flow.component.html.Span noDataMessage = new com.vaadin.flow.component.html.Span("No users found. Click 'Add User' to start!");
        noDataMessage.getStyle().set("color", "gray").set("font-style", "italic");
        grid.setEmptyStateComponent(noDataMessage);


        card.add(viewTitle, toolbar, grid);
        add(card);
    }

    private Component createGridActionsButtons(BudgetUser item) {
        Button edit = new Button(VaadinIcon.EDIT.create());
        edit.getStyle().set("color", "#235252").set("cursor", "pointer");
        edit.addClickListener(e -> { grid.select(item); editUser(); });

        Button del = new Button(VaadinIcon.TRASH.create());
        del.getStyle().set("color", "#a64444").set("cursor", "pointer");
        del.addClickListener(e -> {
            grid.select(item);
            this.selectedUser = item;
            deleteUser();
        });

        HorizontalLayout layout = new HorizontalLayout(edit, del);
        layout.setSpacing(true);
        return layout;
    }

    private void initControllerActions() {
        filterText.addValueChangeListener(e -> updateList());
        btnEditUser.addClickListener(e -> editUser());
        btnAddUser.addClickListener(e -> addUser());
        btnDeleteUser.addClickListener(e -> deleteUser());

        grid.asSingleSelect().addValueChangeListener(e -> {
            this.selectedUser = e.getValue();
            btnEditUser.setEnabled(this.selectedUser != null);
            btnDeleteUser.setEnabled(this.selectedUser != null);
        });
    }

    private void addUser() { this.getUI().ifPresent(ui -> ui.navigate(UserFormView.class, 9999)); }
    private void editUser() { if (this.selectedUser != null) this.getUI().ifPresent(ui -> ui.navigate(UserFormView.class, this.selectedUser.getUserId())); }

    private void deleteUser() {
        if (this.selectedUser != null) {
            com.vaadin.flow.component.dialog.Dialog confirmDialog = new com.vaadin.flow.component.dialog.Dialog();
            confirmDialog.setHeaderTitle("Delete Confirmation");

            com.vaadin.flow.component.html.Div message = new com.vaadin.flow.component.html.Div();
            message.setText("Are you sure you want to delete user: " + selectedUser.getName() + "? This action cannot be undone.");
            message.getStyle().set("padding", "10px 0");

            Button btnConfirm = new Button("Delete User", e -> {
                userService.delete(this.selectedUser);
                refreshGridData();
                confirmDialog.close();
            });
            btnConfirm.getStyle().set("background-color", "#a64444").set("color", "white");

            Button btnCancel = new Button("Cancel", e -> confirmDialog.close());

            confirmDialog.getFooter().add(btnCancel, btnConfirm);
            confirmDialog.add(message);
            confirmDialog.open();
        }
    }

    private void refreshGridData() {
        userList = userService.findAll();
        grid.setItems(userList);
    }

    private void updateList() {
        String val = filterText.getValue().toLowerCase();
        grid.setItems(userList.stream()
                .filter(u -> u.getName().toLowerCase().contains(val) || u.getUsername().toLowerCase().contains(val))
                .toList());
    }

    @Override public void setParameter(BeforeEvent event, @OptionalParameter Integer id) {}
}