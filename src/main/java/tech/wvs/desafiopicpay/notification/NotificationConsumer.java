package tech.wvs.desafiopicpay.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tech.wvs.desafiopicpay.authorization.AuthorizerService;
import tech.wvs.desafiopicpay.notification.exception.NotificationException;
import tech.wvs.desafiopicpay.transaction.Transaction;

@Service
public class NotificationConsumer {

    private final static Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);


    private RestClient restClient;

    public NotificationConsumer(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://util.devi.tools/api/v1/notify")
                .build();
    }

    @KafkaListener(topics = "transaction-notification",
            groupId = "desafio-picpay")
    public void receiveNotification(Transaction transaction) {

        logger.info("Received transaction notification: {}", transaction);
        var response = restClient.get()
                .retrieve()
                .toEntity(Notification.class);


        if (response.getStatusCode().isError() || !response.getBody().message()) {
            throw new NotificationException("Error sending notification");
        }

        logger.info("Notification received: {}", transaction);
    }
}
