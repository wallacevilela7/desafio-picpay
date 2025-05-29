package tech.wvs.desafiopicpay.authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tech.wvs.desafiopicpay.authorization.exception.UnauthorizedTransactionException;
import tech.wvs.desafiopicpay.transaction.Transaction;

@Service
public class AuthorizerService {

    private final static Logger logger = LoggerFactory.getLogger(AuthorizerService.class);

    private RestClient restClient;

    public AuthorizerService(RestClient.Builder builder) {
        this.restClient = RestClient
                .builder()
                .baseUrl("https://util.devi.tools/api/v2/authorize")
                .build();
    }

    // 1. Espera a resposta de forma síncrona
    public void authorize(Transaction transaction) {
        logger.info("Authorizing transaction: {}", transaction);

        var response = restClient.get()
                .retrieve()
                .toEntity(AuthorizationEntity.class);


        if (response.getStatusCode().isError() || !response.getBody().isAuthorized()) {
            throw new UnauthorizedTransactionException("Transaction not authorized");
        }

        logger.info("Transaction authorized: {}", transaction);
    }
}
