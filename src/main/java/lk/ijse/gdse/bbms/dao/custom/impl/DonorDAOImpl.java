package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.DonorDAO;
import lk.ijse.gdse.bbms.entity.Donor;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class DonorDAOImpl implements DonorDAO {
    @Override
    public ArrayList<Donor> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Donor donorEntity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into Donor values (?,?,?,?,?,?,?,?,?)",
                donorEntity.getDonorId(),
                donorEntity.getDonorName(),
                donorEntity.getDonorNic(),
                donorEntity.getDonorAddress(),
                donorEntity.getDonorEmail(),
                donorEntity.getBloodGroup(),
                donorEntity.getGender(),
                donorEntity.getDob(),
                donorEntity.getLastDonationDate()
        );
    }

    @Override
    public boolean update(Donor donor) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update Donor set Name=?, Donor_nic=?, Address=?, E_mail=?,Blood_group=?,Gender=?,Dob=?,Last_donation_date=? where Donor_id=?",
                donor.getDonorName(),
                donor.getDonorNic(),
                donor.getDonorAddress(),
                donor.getDonorEmail(),
                donor.getBloodGroup(),
                donor.getGender(),
                donor.getDob(),
                donor.getLastDonationDate(),
                donor.getDonorId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Donor donor) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from Donor where Donor_id=?", donor.getDonorId());
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public ArrayList<Donor> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}
