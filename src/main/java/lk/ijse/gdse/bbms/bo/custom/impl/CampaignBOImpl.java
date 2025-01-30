package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.CampaignBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.CampaignDAO;

public class CampaignBOImpl implements CampaignBO {

    CampaignDAO campaignDAO= (CampaignDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CAMPAIGN);
    @Override
    public String getNextCampaignId() throws Exception {
        return campaignDAO.getNewId();
    }
}
