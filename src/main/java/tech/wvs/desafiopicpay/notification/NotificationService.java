package tech.wvs.desafiopicpay.notification;

import org.springframework.stereotype.Service;
import tech.wvs.desafiopicpay.transaction.Transaction;

@Service
public class NotificationService {

    public void notify(Transaction transaction) {
        // Kafka
        // Abordagem de notificação assíncrona (evitar excessos de rollback)
    }
}
