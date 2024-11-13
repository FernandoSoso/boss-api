package br.com.boss.app.bossapi.dto.truck;

import br.com.boss.app.bossapi.dto.driver.DriverSummaryDTO;
import br.com.boss.app.bossapi.enums.SituationStatus;

public interface UniqueTruckDTO {
    public String getUuid();
    public Double getCapacity();
    public String getBrand();
    public String getModel();
    public Integer getYear();
    public String getLicensePlate();
    public Double getDriverPercentage();
    public DriverSummaryDTO getDriver();
    public SituationStatus getStatus();
}