package lk.ijse.gdse.bbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodRequestDTO {
    private String requestId;
    private String hospitalId;
    private String bloodType;
    private Date dateOfRequest;
    private double qty;
    private String status;
}
