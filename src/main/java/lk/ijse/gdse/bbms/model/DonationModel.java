package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.BloodTestDTO;
import lk.ijse.gdse.bbms.dto.DonationDTO;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.dto.HealthCheckupDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DonationModel {
    DonorModel donorModel = new DonorModel();
    BloodTestModel bloodTestModel = new BloodTestModel();
    CampaignModel campaignModel = new CampaignModel();

    public boolean addDonation(DonationDTO donationDTO, String donorID) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        BloodTestDTO bloodTestDTO = getBloodTestDTO(donationDTO);

        try {
            if (
                    CrudUtil.execute("insert into Blood_donation values(?,?,?,?,?,?)",
                            donationDTO.getDonationId(),
                            donationDTO.getCampaignId(),
                            donationDTO.getHelthCheckupId(),
                            donationDTO.getBloodGroup(),
                            donationDTO.getQty(),
                            donationDTO.getDateOfDonation())) {
                if (bloodTestModel.addBloodTest(bloodTestDTO)) {
                    if (
                            donorModel.updateLastDonationDate(donorID, donationDTO.getDateOfDonation())) {
                            if(campaignModel.updateCollectedUnit(donationDTO.getCampaignId(),donationDTO.getQty())){
                                connection.commit();
                                return true;
                            }else {
                                connection.rollback();
                                return false;
                            }
                    } else {
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private BloodTestDTO getBloodTestDTO(DonationDTO donationDTO) throws SQLException {
        BloodTestDTO bloodTestDTO = new BloodTestDTO();
        bloodTestDTO.setDonationID(donationDTO.getDonationId());
        bloodTestDTO.setTestID(bloodTestModel.getNextBloodTesdtID());
        bloodTestDTO.setCollectedDate(donationDTO.getDateOfDonation());
        bloodTestDTO.setExpiryDate(null);
        bloodTestDTO.setTestResult("PENDING");
        bloodTestDTO.setHaemoglobin(0);
        bloodTestDTO.setTestDate(null);
        bloodTestDTO.setReportSerialNum("PENDING");
        bloodTestDTO.setPlatelets(0);
        bloodTestDTO.setRedBloodCells(0);
        bloodTestDTO.setWhiteBloodCells(0);
        bloodTestDTO.setReportImageUrl("PENDING");
        bloodTestDTO.setBloodType(donationDTO.getBloodGroup());
        return bloodTestDTO;
    }

    public String getNextDonationId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Donation_id from Blood_donation order by Donation_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last Donation ID
            if (lastId != null && lastId.startsWith("DN")) {
                String substring = lastId.substring(2); // Extract the numeric part (skip "DN")
                try {
                    int i = Integer.parseInt(substring); // Convert the numeric part to integer
                    int newIdIndex = i + 1; // Increment the number by 1
                    return String.format("DN%03d", newIdIndex); // Return the new Donation ID in format DNnnn
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing donation ID: " + lastId);
                }
            } else {
                System.out.println("Unexpected ID format: " + lastId);
            }
        }
        return "DN001"; // Return the default donation ID if no valid data is found
    }

    public ArrayList<DonationDTO> getAllDonations() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Blood_donation");

        ArrayList<DonationDTO> donationDTOS = new ArrayList<>();

        while (rst.next()) {
            DonationDTO donationDTO = new DonationDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getInt(5),
                    rst.getDate(6)
            );
            donationDTOS.add(donationDTO);
        }
        return donationDTOS;
    }
}