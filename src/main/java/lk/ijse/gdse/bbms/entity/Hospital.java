package lk.ijse.gdse.bbms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hospital {
    private String hospitalId;
    private String hospitalName;
    private String hospitalAddress;
    private String contactNumber;
    private String email;
    private String type;
}
