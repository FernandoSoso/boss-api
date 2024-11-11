package br.com.boss.app.bossapi.dto.delivery.response;

public interface DeliveryDTO {
    public String getUuid();
    public String getOrigin();
    public String getDestination();
    public String getDeliveryStatus();
    public Double getGrossValue();
    public Double getWeight();
}
