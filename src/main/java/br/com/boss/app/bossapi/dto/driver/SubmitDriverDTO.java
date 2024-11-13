package br.com.boss.app.bossapi.dto.driver;

import java.sql.Date;

public interface SubmitDriverDTO {
    public String getUuid();
    public String getName();
    public String getLicenseNumber();
    public Date getLicenseExpirationDate();
    public String getPrimaryPhone();
    public String getSecondaryPhone();
    public String getEmail();
}
