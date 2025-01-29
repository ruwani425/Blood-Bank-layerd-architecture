package lk.ijse.gdse.bbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDTO {
    private String hospitalId;
    private String hospitalName;
    private String hospitalAddress;
    private String contactNumber;
    private String email;
    private String type;
}
