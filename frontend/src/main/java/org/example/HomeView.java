package org.example;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.example.services.PortfolioService;

@PageTitle("Dashboard | Finance Admin")
@Route(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    private PortfolioService portfolioService = new PortfolioService();

    public HomeView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        setPadding(true);
        setMinHeight("100vh");
        setWidthFull();

        VerticalLayout welcomeCard = new VerticalLayout();
        welcomeCard.setWidth("90%");
        welcomeCard.setMaxWidth("900px");
        welcomeCard.setAlignItems(Alignment.CENTER);
        welcomeCard.getStyle()
                .set("background", "white")
                .set("border-radius", "30px")
                .set("padding", "30px")
                .set("margin-top", "20px")
                .set("box-shadow", "0 20px 50px rgba(35, 82, 82, 0.1)");

        Icon dashboardLogo = VaadinIcon.CHART_LINE.create();
        dashboardLogo.setSize("60px");
        dashboardLogo.setColor("#235252");

        H1 viewTitle = new H1("Financial Control Center");
        viewTitle.getStyle().set("color", "#235252").set("font-weight", "900").set("margin", "10px 0").set("font-size", "2rem");

        welcomeCard.add(dashboardLogo, viewTitle);

        // 2. Panoul de Statistici (Clienți și Buget)
        HorizontalLayout statsLayout = new HorizontalLayout();
        statsLayout.setWidthFull();
        statsLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        statsLayout.setSpacing(true);
        statsLayout.getStyle().set("margin-top", "20px");

        long userCount = portfolioService.getTotalUsersCount();
        double totalBudget = portfolioService.getTotalAllocatedBudget();

        statsLayout.add(
                createStatCard("Active Customers", String.valueOf(userCount), VaadinIcon.USERS, "#235252"),
                createStatCard("Total System Liquidity", String.format("%.0f RON", totalBudget), VaadinIcon.MONEY, "#3e8e8e")
        );

        // 3. PIESA DE REZISTENȚĂ: Grafic de Bare Manual (Financial Overview)
        VerticalLayout chartSection = createManualChart(userCount, totalBudget);

        add(welcomeCard, statsLayout, chartSection);
    }

    private VerticalLayout createStatCard(String title, String value, VaadinIcon icon, String color) {
        VerticalLayout card = new VerticalLayout();
        card.setWidth("350px");
        card.getStyle()
                .set("background", "white")
                .set("border-radius", "20px")
                .set("padding", "20px")
                .set("box-shadow", "0 10px 30px rgba(0,0,0,0.05)")
                .set("border-bottom", "6px solid " + color);

        Icon i = icon.create();
        i.setColor(color);
        i.setSize("30px");

        Span label = new Span(title);
        label.getStyle().set("color", "gray").set("font-weight", "bold");

        H2 number = new H2(value);
        number.getStyle().set("margin", "0").set("color", "#235252");

        card.add(i, label, number);
        card.setAlignItems(Alignment.CENTER);
        return card;
    }

    private VerticalLayout createManualChart(long users, double budget) {
        VerticalLayout container = new VerticalLayout();
        container.setWidth("90%");
        container.setMaxWidth("900px");
        container.getStyle()
                .set("background", "white")
                .set("border-radius", "25px")
                .set("padding", "40px")
                .set("margin-top", "30px")
                .set("box-shadow", "0 15px 40px rgba(0,0,0,0.05)");

        H2 chartTitle = new H2("System Resource Distribution");
        chartTitle.getStyle().set("color", "#235252").set("font-size", "1.2rem").set("margin-bottom", "30px");

        container.add(chartTitle, createBar("Users Registered", users, 100, "#235252"));
        container.add(createBar("Budget Utilization (Scale: 100k)", budget, 100000, "#3e8e8e"));

        return container;
    }

    private VerticalLayout createBar(String labelText, double value, double maxScale, String color) {
        VerticalLayout barWrapper = new VerticalLayout();
        barWrapper.setPadding(false);
        barWrapper.setSpacing(false);
        barWrapper.setWidth("100%");

        Span label = new Span(labelText + " (" + (int)value + ")");
        label.getStyle()
                .set("font-size", "0.9rem")
                .set("font-weight", "600")
                .set("color", "#235252")
                .set("margin-bottom", "10px");

        HorizontalLayout backgroundBar = new HorizontalLayout();
        backgroundBar.setWidth("100%");
        backgroundBar.setHeight("10px");
        backgroundBar.getStyle()
                .set("background-color", "rgba(0, 0, 0, 0.05)")
                .set("border-radius", "20px")
                .set("position", "relative")
                .set("overflow", "hidden");


        double percentage = Math.min((value / maxScale) * 100, 100);
        Span filler = new Span();
        filler.setHeight("100%");
        filler.setWidth(percentage + "%");
        filler.getStyle()
                .set("background-color", color)
                .set("border-radius", "10px")
                .set("transition", "width 1s ease-in-out");

        backgroundBar.add(filler);
        barWrapper.add(label, backgroundBar);
        barWrapper.getStyle().set("margin-bottom", "20px");
        return barWrapper;
    }
}