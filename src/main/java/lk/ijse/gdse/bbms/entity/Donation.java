package lk.ijse.gdse.bbms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Donation {
    private String donationId;
    private String campaignId;
    private String helthCheckupId;
    private String bloodGroup;
    private int qty;
    private Date dateOfDonation;
}
