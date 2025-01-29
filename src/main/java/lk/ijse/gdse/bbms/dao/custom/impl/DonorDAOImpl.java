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
    public boolean update(Donor Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public void delete(String id) throws SQLException, ClassNotFoundException {

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
