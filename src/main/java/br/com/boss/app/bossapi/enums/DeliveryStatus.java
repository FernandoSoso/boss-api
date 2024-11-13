package br.com.boss.app.bossapi.enums;

public enum DeliveryStatus {
    EM_PROGRESSO,
    CONCLUIDO,
    CANCELADO;

    public static DeliveryStatus fromString(String status) {
        return DeliveryStatus.valueOf(status.toUpperCase());
    }

    @Override
    public String toString() {
        return name();
    }
}