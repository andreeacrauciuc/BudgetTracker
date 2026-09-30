package org.example;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.component.Component;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        getElement().getStyle()
                .set("background", "radial-gradient(circle, #ffffff 0%, #dbe9e9 100%)")
                .set("background-attachment", "fixed")
                .set("min-height", "100vh")
                .set("display", "flex")
                .set("flex-direction", "column");

        getElement().executeJs(
                "document.documentElement.style.backgroundColor = '#dbe9e9';" + // Fixează culoarea de jos
                        "this.shadowRoot.querySelector('[part=\"content\"]').style.backgroundColor = 'transparent';"
        );
    }


    private void createHeader() {
        Icon logoIcon = VaadinIcon.WALLET.create();
        logoIcon.setColor("#235252");

        H2 brandTitle = new H2("FINANCE ADMIN");
        brandTitle.getStyle()
                .set("color", "#235252")
                .set("margin", "0")
                .set("font-weight", "900")
                .set("letter-spacing", "1px");

        HorizontalLayout logoLayout = new HorizontalLayout(logoIcon, brandTitle);
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout navigationMenu = new HorizontalLayout();
        navigationMenu.add(
                createHeaderLink("Dashboard", VaadinIcon.HOME, HomeView.class),
                createHeaderLink("Users", VaadinIcon.USERS, UserGridView.class),
                createHeaderLink("Budgets", VaadinIcon.CHART, BudgetGridView.class),
                createHeaderLink("Portfolio Analysis", VaadinIcon.PIE_CHART, PortfolioAnalysisView.class)
        );

        HorizontalLayout headerContainer = new HorizontalLayout(logoLayout, navigationMenu);
        headerContainer.setWidthFull();
        headerContainer.setPadding(true);
        headerContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        headerContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        headerContainer.getStyle()
                .set("background-color", "rgba(255, 255, 255, 0.8)")
                .set("backdrop-filter", "blur(12px)")
                .set("box-shadow", "0 4px 20px rgba(35, 82, 82, 0.15)")
                .set("border-bottom", "4px solid #235252")
                .set("position", "sticky")
                .set("top", "0")
                .set("z-index", "100");

        addToNavbar(headerContainer);
    }

    private RouterLink createHeaderLink(String text, VaadinIcon iconType, Class<? extends Component> targetView) {
        RouterLink navigationLink = new RouterLink();
        navigationLink.setRoute(targetView);

        Icon linkIcon = iconType.create();
        linkIcon.getStyle().set("width", "18px");

        Span labelText = new Span(text);
        HorizontalLayout linkContent = new HorizontalLayout(linkIcon, labelText);
        navigationLink.add(linkContent);

        navigationLink.getStyle()
                .set("text-decoration", "none")
                .set("color", "#235252")
                .set("padding", "10px 18px")
                .set("border-radius", "12px")
                .set("font-weight", "600")
                .set("transition", "all 0.3s ease-in-out");

        navigationLink.getElement().executeJs(
                "this.onmouseover = () => { " +
                        "  this.style.backgroundColor = '#235252'; " +
                        "  this.style.color = 'white'; " +
                        "  this.style.transform = 'translateY(-2px)'; " +
                        "}; " +
                        "this.onmouseout = () => { " +
                        "  this.style.backgroundColor = 'transparent'; " +
                        "  this.style.color = '#235252'; " +
                        "  this.style.transform = 'translateY(0)'; " +
                        "};"
        );

        return navigationLink;
    }
}