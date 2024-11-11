package br.com.boss.app.bossapi.dto.delivery.response;

import br.com.boss.app.bossapi.dto.driver.response.DriverSummaryDTO;
import br.com.boss.app.bossapi.dto.truck.response.TruckSummaryDTO;
import br.com.boss.app.bossapi.enums.DeliveryStatus;

public interface UniqueDeliveryDTO {
    public String getUuid();
    public DriverSummaryDTO getDriver();
    public TruckSummaryDTO getTruck();
    public String getOrigin();
    public String getDestination();
    public Double getNetValue();
    public Double getGrossValue();
    public Double getDriverPercentage();
    public DeliveryStatus getStatus();
    public Double getValuePerTon();
    public Double getWeight();
    public String getObservation();
}
