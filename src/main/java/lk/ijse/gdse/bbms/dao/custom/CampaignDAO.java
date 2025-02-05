package lk.ijse.gdse.bbms.dao.custom;

import lk.ijse.gdse.bbms.dao.CrudDAO;
import lk.ijse.gdse.bbms.dao.SuperDAO;
import lk.ijse.gdse.bbms.entity.Campaign;

import java.util.ArrayList;

public interface CampaignDAO extends SuperDAO, CrudDAO <Campaign> {
    boolean updateCollectedUnit(String campaignId, int qty)throws Exception;

    ArrayList<String> getCampaignIDs()throws Exception;
}
