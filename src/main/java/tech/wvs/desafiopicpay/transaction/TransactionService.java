package tech.wvs.desafiopicpay.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.wvs.desafiopicpay.authorization.AuthorizerService;
import tech.wvs.desafiopicpay.notification.NotificationService;
import tech.wvs.desafiopicpay.transaction.exception.InvalidTransactionException;
import tech.wvs.desafiopicpay.wallet.Wallet;
import tech.wvs.desafiopicpay.wallet.WalletRepository;
import tech.wvs.desafiopicpay.wallet.WalletType;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizerService authorizerService;
    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository,
                              WalletRepository walletRepository,
                              AuthorizerService authorizerService,
                              NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizerService = authorizerService;
        this.notificationService = notificationService;
    }

    @Transactional
    public Transaction create(Transaction transaction) {

        // 1. Validar
        validateTransaction(transaction);

        // 2. Criar transação
        var transactionCreated = transactionRepository.save(transaction);

        // 3. Debitar da carteira do pagador
        var wallet = walletRepository.findById(transaction.payer()).get();
        walletRepository.save(wallet.debit(transaction.value()));

        // 4. Chamar serviços externos
        //// 4.1 Serviço de autorização de transações
        authorizerService.authorize(transaction);


        //// 4.2 Serviço de notificação
        notificationService.notify(transaction);

        // 5. Retornar transação criada
        return transactionCreated;
    }

    private void validateTransaction(Transaction transaction) {
        // 1. Type deve ser comum(1)
        // 2. Tem saldo suficiente
        // 3. Pagador e recebedor devem ser diferentes

        walletRepository.findById(transaction.payee())
                .map(payee -> walletRepository.findById(transaction.payer())
                        .map(payer -> isTransactionValid(transaction, payer)
                                ? transaction : null)
                        .orElseThrow(() -> new InvalidTransactionException("Payer not found")))
                .orElseThrow(() -> new InvalidTransactionException("Payee not found"));
    }

    private boolean isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.COMUM.getValue() &&
               payer.balance().compareTo(transaction.value()) >= 0 &&
               !payer.id().equals(transaction.payee());
    }

}
