package br.com.boss.app.bossapi.dto.truck.response;

import br.com.boss.app.bossapi.enums.SituationStatus;

public interface TruckDTO {
    public String getUuid();
    public String getLicensePlate();
    public String getBrand();
    public String getModel();
    public Integer getYear();
    public Double getCapacity();
    public SituationStatus getStatus();
}