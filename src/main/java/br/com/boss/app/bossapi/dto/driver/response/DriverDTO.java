package br.com.boss.app.bossapi.dto.driver.response;

public interface DriverDTO {
    public String getUuid();
    public String getName();
    public String getLicenseNumber();
    public String getLicenseExpirationDate();
    public String getPrimaryPhone();
    public String getSecondaryPhone();
}
