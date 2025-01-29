package lk.ijse.gdse.bbms.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorTM {
    private String donorId;
    private String donorName;
    private String donorNic;
    private String donorAddress;
    private String donorEmail;
    private String bloodGroup;
    private String gender;
    private Date dob;

    public DonorTM(String donorName, String donorAddress, String bloodGroup) {
        this.donorName = donorName;
        this.donorAddress = donorAddress;
        this.bloodGroup = bloodGroup;
    }
}
