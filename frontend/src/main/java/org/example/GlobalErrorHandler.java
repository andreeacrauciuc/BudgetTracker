package org.example;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.Route;
import jakarta.servlet.http.HttpServletResponse;

@Tag(Tag.DIV)
public class GlobalErrorHandler extends Div implements HasErrorParameter<Exception> {

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<Exception> parameter) {
        System.err.println("EROARE DETECTATĂ: " + parameter.getException().getMessage());
        Notification errorNote = new Notification();
        errorNote.setText("Sistemul este momentan indisponibil. Reîncercați!");
        errorNote.setDuration(5000);
        errorNote.addThemeVariants(NotificationVariant.LUMO_ERROR);
        errorNote.setPosition(Notification.Position.TOP_CENTER);
        errorNote.open();

        event.forwardTo("");

        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }
}