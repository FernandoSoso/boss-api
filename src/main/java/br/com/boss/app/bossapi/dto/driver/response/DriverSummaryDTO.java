package br.com.boss.app.bossapi.dto.driver.response;

import java.util.Date;

public record DriverSummaryDTO(String id,
                               String name,
                               String licenseNumber,
                               Date licenseExpirationDate,
                               Date startDate) {

}