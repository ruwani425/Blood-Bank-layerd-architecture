package lk.ijse.gdse.bbms.dto.tm;

import com.jfoenix.controls.JFXButton;
import lk.ijse.gdse.bbms.dto.BloodStockDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodIssueTM extends BloodStockDTO {
    private String bloodIssueID;
    private String bloodGroup;
    private double bloodQty;
    private Date expiry;
    private JFXButton button;
}
