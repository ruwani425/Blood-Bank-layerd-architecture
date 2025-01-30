package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.CampaignBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.CampaignDAO;
import lk.ijse.gdse.bbms.dto.CampaignDTO;
import lk.ijse.gdse.bbms.entity.Campaign;

public class CampaignBOImpl implements CampaignBO {

    CampaignDAO campaignDAO = (CampaignDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CAMPAIGN);

    @Override
    public String getNextCampaignId() throws Exception {
        return campaignDAO.getNewId();
    }

    @Override
    public boolean addCampaign(CampaignDTO campaignDTO) throws Exception {
        Campaign campaign = new Campaign();
        campaign.setCampaign_name(campaignDTO.getCampaign_name());
        campaign.setBlood_campaign_id(campaignDTO.getBlood_campaign_id());
        campaign.setAddress(campaignDTO.getAddress());
        campaign.setStatus(campaignDTO.getStatus());
        campaign.setEndDate(campaignDTO.getEndDate());
        campaign.setStartDate(campaignDTO.getStartDate());
        return campaignDAO.save(campaign);
    }
}
