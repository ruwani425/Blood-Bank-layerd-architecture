package lk.ijse.gdse.bbms.dao.custom;

import lk.ijse.gdse.bbms.dao.CrudDAO;
import lk.ijse.gdse.bbms.dao.SuperDAO;
import lk.ijse.gdse.bbms.dto.HospitalDTO;
import lk.ijse.gdse.bbms.entity.Hospital;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HospitalDAO extends SuperDAO, CrudDAO<Hospital> {
    ArrayList<String> getAllHospitalIDs() throws SQLException;

}
