package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.CampaignDAO;
import lk.ijse.gdse.bbms.entity.Campaign;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CampaignDAOImpl implements CampaignDAO {
    @Override
    public ArrayList<Campaign> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Campaign Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Campaign Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Campaign id) throws SQLException, ClassNotFoundException {
        return false;
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
