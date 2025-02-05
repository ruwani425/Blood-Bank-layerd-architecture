package lk.ijse.gdse.bbms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservedBlood {
    private String reservedBloodID;
    private String blood_id;
    private String hospital_id;
    private Date reserved_date;
    private double reserved_qty;
}
