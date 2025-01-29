package lk.ijse.gdse.bbms.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationTM {
    private String Donation_id;
    private String Blood_campaign_id;
    private String Health_checkup_id;
    private String Blood_group;
    private int qty;
    private Date dateOfDonation;
}
