package lk.ijse.gdse.bbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheckupDTO {
    private String checkupId;
    private String donorId;
    private String healthStatus;
    private Date checkupDate;
    private double weight;
    private double sugarLevel;
    private String bloodPressure;

    DonationDTO donation;

    public HealthCheckupDTO(String checkupId, String donorId, String healthStatus, Date checkupDate, double weight, double sugarLevel, String bloodPressure) {
        this.checkupId = checkupId;
        this.donorId = donorId;
        this.healthStatus = healthStatus;
        this.checkupDate = checkupDate;
        this.weight = weight;
        this.sugarLevel = sugarLevel;
        this.bloodPressure = bloodPressure;
    }

    public void setDonation(String text, String campaignId, String checkupId, String bloodGroup, int qty, Date donationDate) {
        this.donation = new DonationDTO(text, campaignId, checkupId, bloodGroup, qty, donationDate);
    }
}