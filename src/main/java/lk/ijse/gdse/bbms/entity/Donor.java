package lk.ijse.gdse.bbms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donor {
    private String donorId;
    private String donorName;
    private String donorNic;
    private String donorAddress;
    private String donorEmail;
    private String bloodGroup;
    private String gender;
    private Date dob;
    private Date lastDonationDate;
}
