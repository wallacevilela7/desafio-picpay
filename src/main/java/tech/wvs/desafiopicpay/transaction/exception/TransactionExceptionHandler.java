package tech.wvs.desafiopicpay.transaction.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionExceptionHandler {

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<Object> handleInvalidTransactionException(InvalidTransactionException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
