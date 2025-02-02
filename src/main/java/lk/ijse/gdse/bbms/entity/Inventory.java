package lk.ijse.gdse.bbms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    private String inventoryId;
    private String itemName;
    private String status;
    private Date expiryDate;
    private int qty;
}
