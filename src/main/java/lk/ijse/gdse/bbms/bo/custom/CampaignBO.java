package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.CampaignDTO;

import java.util.ArrayList;

public interface CampaignBO extends SuperBO {
    public String getNextCampaignId() throws Exception;

    public boolean addCampaign(CampaignDTO campaignDTO) throws Exception;

    public boolean deleteCampaign(String campaignId)throws Exception;

    public boolean updateCampaign(CampaignDTO campaignDTO) throws Exception;

    public ArrayList<CampaignDTO> getAllCampaigns()throws Exception;
}
