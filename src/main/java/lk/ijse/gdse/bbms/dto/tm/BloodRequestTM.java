package lk.ijse.gdse.bbms.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodRequestTM {
    private String requestId;
    private String hospitalId;
    private String bloodType;
    private Date dateOfRequest;
    private double qty;
    private String status;
}
