package lk.ijse.gdse.bbms.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodStockTM {
    private String bloodID;
    private String testID;
    private String bloodGroup;
    private double qty;
    private double haemoglobin;
    private Float platelets;
    private double redBloodCells;
    private double whiteBloodCells;
    private Date expiryDate;
    private String status;
}
