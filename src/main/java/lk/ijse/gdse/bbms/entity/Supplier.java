package lk.ijse.gdse.bbms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    private String supplierId;
    private String supplierName;
    private String address;
    private String email;
    private String description;
}
