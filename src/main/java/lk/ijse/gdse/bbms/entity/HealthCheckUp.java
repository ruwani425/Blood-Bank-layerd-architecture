package lk.ijse.gdse.bbms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthCheckUp {
    private String checkupId;
    private String donorId;
    private String healthStatus;
    private Date checkupDate;
    private double weight;
    private double sugarLevel;
    private String bloodPressure;
}
