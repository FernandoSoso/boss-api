package br.com.boss.app.bossapi.dto.driver.response;

import br.com.boss.app.bossapi.dto.truck.response.TruckSummaryDTO;
import br.com.boss.app.bossapi.enums.SituationStatus;

public interface UniqueDriverDTO {
    public Long getUuid();
    public String getName();
    public String getLicenseNumber();
    public String getLicenseExpirationDate();
    public String getPrimaryPhone();
    public String getSecondaryPhone();
    public String getEmail();
    public TruckSummaryDTO getTruck();
    public SituationStatus getStatus();
}
