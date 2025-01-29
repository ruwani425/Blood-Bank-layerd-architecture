package lk.ijse.gdse.bbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private String inventoryId;
    private String itemName;
    private String status;
    private Date expiryDate;
    private int qty;
}
