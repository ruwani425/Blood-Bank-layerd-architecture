package lk.ijse.gdse.bbms.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodTestTM {
    private String testID;
    private String donationID;
    private Date collectedDate;
    private String testResult;
    private double haemoglobin;
    private Date testDate;
    private String reportSerialNum;
    private float platelets;
    private double redBloodCells;
    private double whiteBloodCells;
    private double bloodQty;
}
