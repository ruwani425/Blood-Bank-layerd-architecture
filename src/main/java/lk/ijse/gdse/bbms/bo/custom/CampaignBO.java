package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.CampaignDTO;

public interface CampaignBO extends SuperBO {
    public String getNextCampaignId() throws Exception;

    public boolean addCampaign(CampaignDTO campaignDTO) throws Exception;
}
