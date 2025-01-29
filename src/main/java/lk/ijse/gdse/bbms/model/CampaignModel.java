package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.dto.CampaignDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CampaignModel {
    public String getNextCampaignId() throws SQLException {
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

    public ArrayList<CampaignDTO> getAllCampaigns() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Blood_campaign");

        ArrayList<CampaignDTO> campaignDTOS = new ArrayList<>();

        while (rst.next()) {
            CampaignDTO campaignDTO = new CampaignDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getDate(5),
                    rst.getString(6),
                    rst.getInt(7)
            );
            campaignDTOS.add(campaignDTO);
        }
        return campaignDTOS;
    }

    public boolean addCampaign(CampaignDTO campaignDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Blood_campaign values (?,?,?,?,?,?,?)",
                campaignDTO.getBlood_campaign_id(),
                campaignDTO.getCampaign_name(),
                campaignDTO.getAddress(),

                campaignDTO.getStartDate(),
                campaignDTO.getEndDate(),
                campaignDTO.getStatus(),
                campaignDTO.getCollectedUnits()
        );
    }

    public boolean deleteCampaign(String campaignId) throws SQLException {
        return CrudUtil.execute("delete from Blood_campaign where Blood_campaign_id=?",campaignId);
    }

    public boolean updateCampaign(CampaignDTO campaignDTO) throws SQLException {
        return CrudUtil.execute(
                "update Blood_campaign set Name=?, Address=?, Start_date=?, End_date=?,Status=? where Blood_campaign_id=?",
                campaignDTO.getCampaign_name(),
                campaignDTO.getAddress(),
                campaignDTO.getStartDate(),
                campaignDTO.getEndDate(),
                campaignDTO.getStatus(),
                campaignDTO.getBlood_campaign_id()
        );
    }

    public ArrayList<String> findCampaignIds() throws SQLException {
        ResultSet rst=CrudUtil.execute("select Blood_campaign_id from Blood_campaign");
        ArrayList<String> campaignIds = new ArrayList<>();
        while (rst.next()) {
            campaignIds.add(rst.getString(1));
        }
        return campaignIds;
    }

    public CampaignDTO getCampaignById(String value) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Blood_campaign where Blood_campaign_id=?",value);
        if (rst.next()) {
            return new CampaignDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getDate(5),
                    rst.getString(6),
                    rst.getInt(7)
            );
        }
        return null;
    }

    public boolean updateCollectedUnit(String campaignId, int qty) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Blood_campaign SET Collected_units = Collected_units + ? WHERE Blood_campaign_id = ?",
                1,
                campaignId
        );
    }
}