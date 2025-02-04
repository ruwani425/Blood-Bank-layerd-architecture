package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.DonationDAO;
import lk.ijse.gdse.bbms.entity.Donation;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class DonationDAOImpl implements DonationDAO {
    @Override
    public ArrayList<Donation> getAllData() throws SQLException, ClassNotFoundException {
        return null;
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
        return "";
    }

    @Override
    public ArrayList<Donation> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}
