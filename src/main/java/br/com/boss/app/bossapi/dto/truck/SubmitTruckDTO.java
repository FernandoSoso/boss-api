package br.com.boss.app.bossapi.dto.truck;

import br.com.boss.app.bossapi.dto.driver.DriverSummaryDTO;

public interface SubmitTruckDTO {
    public String getUuid();
    public String getLicensePlate();
    public String getBrand();
    public String getModel();
    public Integer getYear();
    public Double getCapacity();
    public Double getDriverPercentage();
}