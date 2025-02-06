package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.BloodRequestDetailDAO;
import lk.ijse.gdse.bbms.entity.BloodRequestDetail;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class BloodRequestDetailDAOImpl implements BloodRequestDetailDAO {
    @Override
    public ArrayList<BloodRequestDetail> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(BloodRequestDetail bloodRequestDetail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into Blood_request_detail values(?,?)",
                bloodRequestDetail.getBlood_request_id(),
                bloodRequestDetail.getBlood_id()
        );
    }

    @Override
    public boolean update(BloodRequestDetail Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(BloodRequestDetail id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public ArrayList<BloodRequestDetail> search(BloodRequestDetail newValue) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public BloodRequestDetail findById(BloodRequestDetail entity) throws SQLException {
        return null;
    }
}
