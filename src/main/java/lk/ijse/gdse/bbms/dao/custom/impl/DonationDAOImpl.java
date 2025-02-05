package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.DonationDAO;
import lk.ijse.gdse.bbms.dto.DonationDTO;
import lk.ijse.gdse.bbms.entity.Donation;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DonationDAOImpl implements DonationDAO {
    @Override
    public ArrayList<Donation> getAllData() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from Blood_donation");

        ArrayList<Donation> donations = new ArrayList<>();

        while (rst.next()) {
            Donation donation = new Donation(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getInt(5),
                    rst.getDate(6)
            );
            donations.add(donation);
        }
        return donations;
    }

    @Override
    public boolean save(Donation donation) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into Blood_donation values(?,?,?,?,?,?)",
                donation.getDonationId(),
                donation.getCampaignId(),
                donation.getHelthCheckupId(),
                donation.getBloodGroup(),
                donation.getQty(),
                donation.getDateOfDonation());
    }

    @Override
    public boolean update(Donation Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Donation id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
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

    @Override
    public ArrayList<Donation> search(Donation newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}
