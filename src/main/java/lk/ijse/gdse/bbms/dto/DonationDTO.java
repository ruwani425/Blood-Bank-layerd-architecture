package lk.ijse.gdse.bbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationDTO {
    private String donationId;
    private String campaignId;
    private String helthCheckupId;
    private String bloodGroup;
    private int qty;
    private Date dateOfDonation;
}
