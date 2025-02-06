package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.ReservedBloodDAO;
import lk.ijse.gdse.bbms.entity.ReservedBlood;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservedBloodDAOImpl implements ReservedBloodDAO {
    @Override
    public ArrayList<ReservedBlood> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(ReservedBlood reservedBlood) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into Reserved_blood values(?,?,?,?,?)",
                reservedBlood.getReservedBloodID(),
                reservedBlood.getBlood_id(),
                reservedBlood.getHospital_id(),
                reservedBlood.getReserved_date(),
                reservedBlood.getReserved_qty()
        );
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
        ResultSet rst = CrudUtil.execute("select Reserved_id from Reserved_blood order by Reserved_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last blood ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("R%03d", newIdIndex); // Return the new Blood ID in format Rnnn
        }
        return "R001";
    }

    @Override
    public ArrayList<ReservedBlood> search(ReservedBlood newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ReservedBlood findById(ReservedBlood entity) throws SQLException {
        return null;
    }
}
