package br.com.boss.app.bossapi.dto.truck.response;

import java.util.Date;

public interface TruckSummaryDTO {
    public String getUuid();
    public String getLicensePlate();
    public Double getDriverPercentage();
    public Integer getCapacity();
    public Date getStartDate();
}