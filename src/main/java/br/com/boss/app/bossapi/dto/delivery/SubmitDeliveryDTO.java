package br.com.boss.app.bossapi.dto.delivery;

import br.com.boss.app.bossapi.dto.driver_truck.EntryDTO;

public interface SubmitDeliveryDTO {
    public String getUuid();
    public String getOrigin();
    public String getDestination();
    public Double getValuePerTon();
    public Double getGrossValue();
    public Double getNetValue();
    public Double getWeight();
    public String getObservation();
    public Double getDriverShare();
    public EntryDTO getResponsible();
}
