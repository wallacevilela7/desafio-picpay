package tech.wvs.desafiopicpay.authorization;

public record AuthorizationEntity(String status,
                                  AuthorizationData data) {

    public boolean isAuthorized() {
        return "success".equalsIgnoreCase(status)
                && data != null
                && data.authorization();
    }
}
