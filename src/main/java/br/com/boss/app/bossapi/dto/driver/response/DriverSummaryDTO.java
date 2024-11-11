package br.com.boss.app.bossapi.dto.driver.response;

import java.util.Date;

public interface DriverSummaryDTO {
    public String getUuid();
    public String getName();
    public String getLicenseNumber();
    public Date getLicenseExpirationDate();
    public Date getStartDate();
}