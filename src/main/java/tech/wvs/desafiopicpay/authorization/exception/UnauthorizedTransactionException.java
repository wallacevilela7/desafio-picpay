package tech.wvs.desafiopicpay.authorization.exception;

public class UnauthorizedTransactionException extends RuntimeException {

    public UnauthorizedTransactionException(String message) {
        super(message);
    }
}
