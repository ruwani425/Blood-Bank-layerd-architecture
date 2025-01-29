package lk.ijse.gdse.bbms.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTM {
    private String employeeID;
    private String name;
    private String nic;
    private String address;
    private String email;
    private String role;
    private String status;
}
