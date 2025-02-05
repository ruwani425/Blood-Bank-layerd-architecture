package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.ReservedBloodDAO;
import lk.ijse.gdse.bbms.entity.ReservedBlood;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReservedBloodDAOImpl implements ReservedBloodDAO {
    @Override
    public ArrayList<ReservedBlood> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(ReservedBlood Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(ReservedBlood Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(ReservedBlood id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public ArrayList<ReservedBlood> search(ReservedBlood newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}
