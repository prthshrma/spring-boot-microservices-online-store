package com.prthshrma.onlinestore;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import com.prthshrma.onlinestore.event.OrderPlacedEvent;

@SpringBootApplication
public class NotificationServiceApplication {

    private static final Logger log = Logger.getLogger(NotificationServiceApplication.class.getName());
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        // add logic to send email or message notification
        log.info("Notification for order - "+orderPlacedEvent.getOrderNumber());
    }
}
