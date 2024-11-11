package br.com.boss.app.bossapi.dto.driver.request;

public interface SubmitDriverDTO {
    public String getName();
    public String getLicenseNumber();
    public String getLicenseExpirationDate();
    public String getPrimaryPhone();
    public String getSecondaryPhone();
    public String getStartDate();
    public String getEmail();
    public String getTruckId();
}
