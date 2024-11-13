package br.com.boss.app.bossapi.dto.delivery;

public interface DeliveryDTO {
    String getUuid();
    String getOrigin();
    String getDestination();
    String getDeliveryStatus();
    Double getGrossValue();
    Double getWeight();
}
