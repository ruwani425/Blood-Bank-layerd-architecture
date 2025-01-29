package lk.ijse.gdse.bbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodTestDTO {
    private String testID;
    private String donationID;
    private Date collectedDate;
    private Date expiryDate;
    private String testResult;
    private double haemoglobin;
    private Date testDate;
    private String reportSerialNum;
    private float platelets;
    private double redBloodCells;
    private double whiteBloodCells;
    private String reportImageUrl; // Attribute to store the report image URL
    private String bloodType;
    private double bloodQty;
}
