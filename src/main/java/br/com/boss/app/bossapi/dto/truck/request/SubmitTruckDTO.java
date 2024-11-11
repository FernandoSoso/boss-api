package br.com.boss.app.bossapi.dto.truck.request;

public interface SubmitTruckDTO {
    public String getLicensePlate();
    public String getBrand();
    public String getModel();
    public Integer getYear();
    public Integer getCapacity();
    public Double getDriverPercentage();
    public String getDriverId();
}