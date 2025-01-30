package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.CampaignDAO;
import lk.ijse.gdse.bbms.dto.CampaignDTO;
import lk.ijse.gdse.bbms.entity.Campaign;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CampaignDAOImpl implements CampaignDAO {
    @Override
    public ArrayList<Campaign> getAllData() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from Blood_campaign");

        ArrayList<Campaign> campaigns = new ArrayList<>();

        while (rst.next()) {
            Campaign campaign = new Campaign(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getDate(5),
                    rst.getString(6),
                    rst.getInt(7)
            );
            campaigns.add(campaign);
        }
        return campaigns;
    }

    @Override
    public boolean save(Campaign campaign) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Blood_campaign values (?,?,?,?,?,?,?)",
                campaign.getBlood_campaign_id(),
                campaign.getCampaign_name(),
                campaign.getAddress(),

                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getStatus(),
                campaign.getCollectedUnits()
        );
    }

    @Override
    public boolean update(Campaign campaign) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update Blood_campaign set Name=?, Address=?, Start_date=?, End_date=?,Status=? where Blood_campaign_id=?",
                campaign.getCampaign_name(),
                campaign.getAddress(),
                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getStatus(),
                campaign.getBlood_campaign_id()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Campaign campaign) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from Blood_campaign where Blood_campaign_id=?",campaign.getBlood_campaign_id());
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select Blood_campaign_id from Blood_campaign order by Blood_campaign_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last campaign ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("C%03d", newIdIndex); // Return the new Campaign ID in format Cnnn
        }
        return "C001"; // Return the default Campaign ID if no data is found
    }

    @Override
    public ArrayList<Campaign> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}
