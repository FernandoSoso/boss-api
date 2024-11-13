package br.com.boss.app.bossapi.dto.truck;

import java.util.Date;

public interface TruckSummaryDTO {
    public String getUuid();
    public String getLicensePlate();
    public Double getDriverPercentage();
    public Double getCapacity();
    public Date getStartDate();
}