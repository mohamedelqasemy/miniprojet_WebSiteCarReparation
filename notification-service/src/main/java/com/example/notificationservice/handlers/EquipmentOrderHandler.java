package com.example.notificationservice.handlers;

import com.example.notificationservice.events.EquipmentOrderNotification;
import com.example.notificationservice.services.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class EquipmentOrderHandler {

    private final EmailService emailService;

    public EquipmentOrderHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    @Bean
    public Consumer<EquipmentOrderNotification> equipmentOrderConsumer() {
        return event -> {
            System.out.println("Nouvelle commande d'équipement : " + event);

            String subject = "Confirmation de votre commande d'équipement";
            String equipments = String.join(", ", event.equipmentNames());

            String body = String.format(
                    "Bonjour,\n\nVotre commande (ID : %d) du %s a bien été reçue.\nÉquipements commandés : %s\n\nMerci pour votre confiance.\nAtlas Car Repair.",
                    event.orderId(),
                    event.orderDate(),
                    equipments
            );

            emailService.send(event.email(), subject, body);
        };
    }
}