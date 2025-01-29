package lk.ijse.gdse.bbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDTO {
    private String Blood_campaign_id;
    private String campaign_name;
    private String address;
    private Date startDate;
    private Date endDate;
    private String status;
    private int collectedUnits;
}
