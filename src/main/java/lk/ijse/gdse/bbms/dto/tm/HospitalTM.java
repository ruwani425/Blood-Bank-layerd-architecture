package lk.ijse.gdse.bbms.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalTM {
    private String hospitalId;
    private String hospitalName;
    private String hospitalAddress;
    private String contactNumber;
    private String email;
    private String type;
}
