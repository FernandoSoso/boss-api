package br.com.boss.app.bossapi.dto.delivery.request;

public interface SubmitDeliveryDTO {
    public String getOrigin();
    public String getDestination();
    public Double getValuePerTon();
    public Double getWeight();
    public String getDriverId();
    public String getTruckId();
    public String getObservation();
    public Double getDriverShare();
}
