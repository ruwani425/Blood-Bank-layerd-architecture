package lk.ijse.gdse.bbms.entity;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodTest {
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

    public BloodTest(Date collectedDate, Date expiryDate, String testResult, double haemoglobin, Date testDate, String reportSerialNum, float platelets, double redBloodCells, double whiteBloodCells, String reportImageUrl, String bloodType, double bloodQty, String testID) {
        this.collectedDate = collectedDate;
        this.expiryDate = expiryDate;
        this.testResult = testResult;
        this.haemoglobin = haemoglobin;
        this.testDate = testDate;
        this.reportSerialNum = reportSerialNum;
        this.platelets = platelets;
        this.redBloodCells = redBloodCells;
        this.whiteBloodCells = whiteBloodCells;
        this.reportImageUrl = reportImageUrl;
        this.bloodType = bloodType;
        this.bloodQty = bloodQty;
        this.testID = testID;
    }
}
