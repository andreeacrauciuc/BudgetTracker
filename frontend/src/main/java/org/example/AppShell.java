package org.example;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;

@PWA(
        name = "Finance Admin Dashboard",
        shortName = "FinAdmin",
        description = "Enterprise-grade financial portfolio and budget management system.",
        offlinePath = "offline.html",
        offlineResources = {"./images/offline.png"}
)
public class AppShell implements AppShellConfigurator {
}