package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.CampaignBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.CampaignDAO;
import lk.ijse.gdse.bbms.dto.CampaignDTO;
import lk.ijse.gdse.bbms.entity.Campaign;

import java.util.ArrayList;

public class CampaignBOImpl implements CampaignBO {

    private final CampaignDAO campaignDAO = (CampaignDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CAMPAIGN);

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

    @Override
    public boolean deleteCampaign(String campaignId) throws Exception {
        Campaign campaign = new Campaign();
        campaign.setBlood_campaign_id(campaignId);
        return campaignDAO.delete(campaign);
    }

    @Override
    public boolean updateCampaign(CampaignDTO campaignDTO) throws Exception {
        Campaign campaign = new Campaign();

        campaign.setCampaign_name(campaignDTO.getCampaign_name());
        campaign.setBlood_campaign_id(campaignDTO.getBlood_campaign_id());
        campaign.setAddress(campaignDTO.getAddress());
        campaign.setStatus(campaignDTO.getStatus());
        campaign.setEndDate(campaignDTO.getEndDate());
        campaign.setStartDate(campaignDTO.getStartDate());
        return campaignDAO.update(campaign);
    }

    @Override
    public ArrayList<CampaignDTO> getAllCampaigns() throws Exception {
        ArrayList<Campaign> campaigns = campaignDAO.getAllData();
        ArrayList<CampaignDTO> campaignDTOS = new ArrayList<>();
        for (Campaign campaign : campaigns) {
            CampaignDTO campaignDTO = new CampaignDTO();
            campaignDTO.setCampaign_name(campaign.getCampaign_name());
            campaignDTO.setBlood_campaign_id(campaign.getBlood_campaign_id());
            campaignDTO.setAddress(campaign.getAddress());
            campaignDTO.setStatus(campaign.getStatus());
            campaignDTO.setEndDate(campaign.getEndDate());
            campaignDTO.setStartDate(campaign.getStartDate());
            campaignDTOS.add(campaignDTO);
        }
        return campaignDTOS;
    }
}
