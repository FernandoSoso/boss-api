package br.com.boss.app.bossapi.dto.driver;

import br.com.boss.app.bossapi.dto.truck.TruckSummaryDTO;
import br.com.boss.app.bossapi.enums.SituationStatus;

public interface UniqueDriverDTO {
    public String getUuid();
    public String getName();
    public String getLicenseNumber();
    public String getLicenseExpirationDate();
    public String getPrimaryPhone();
    public String getSecondaryPhone();
    public String getEmail();
    public TruckSummaryDTO getTruck();
    public SituationStatus getStatus();
}
