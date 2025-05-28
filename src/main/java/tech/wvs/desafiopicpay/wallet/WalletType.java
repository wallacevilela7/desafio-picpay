package tech.wvs.desafiopicpay.wallet;

public enum WalletType {
    COMUM(1),
    LOJISTA(2);

    private int value;

    WalletType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
