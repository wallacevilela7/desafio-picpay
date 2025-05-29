package tech.wvs.desafiopicpay.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tech.wvs.desafiopicpay.notification.exception.NotificationException;
import tech.wvs.desafiopicpay.transaction.Transaction;

@Service
public class NotificationConsumer {

    private RestClient restClient;

    public NotificationConsumer(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://util.devi.tools/api/v1/notify")
                .build();
    }

    @KafkaListener(topics = "transaction-notification",
            groupId = "desafio-picpay")
    public void receiveNotification(Transaction transaction) {
        var response = restClient.get()
                .retrieve()
                .toEntity(Notification.class);


        if (response.getStatusCode().isError() || !response.getBody().message()) {
            throw new NotificationException("Error sending notification");
        }
    }
}
