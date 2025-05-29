package tech.wvs.desafiopicpay.notification;

import org.springframework.stereotype.Service;
import tech.wvs.desafiopicpay.transaction.Transaction;

@Service
public class NotificationService {

    private final NotificationProducer notificationProducer;

    public NotificationService(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    public void notify(Transaction transaction) {
        // Kafka
        // Abordagem de notificação assíncrona (evitar excessos de rollback)

        notificationProducer.sendNotification(transaction);
    }
}
