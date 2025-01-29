package lk.ijse.gdse.bbms.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignTM {
    private String Blood_campaign_id;
    private String campaign_name;
    private String address;
    private Date startDate;
    private Date endDate;
    private String status;
    private int collectedUnits;
}
