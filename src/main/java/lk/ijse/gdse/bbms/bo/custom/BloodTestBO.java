package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.BloodTestDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BloodTestBO extends SuperBO {
    BloodTestDTO getBloodTestDetailById(String testID) throws SQLException;

    ArrayList<BloodTestDTO> getAllBloodTestsBystatus(String pending) throws SQLException, ClassNotFoundException;

    ArrayList<BloodTestDTO> getAllBloodTests() throws SQLException, ClassNotFoundException;

    boolean updateBloodTest(BloodTestDTO bloodTestDTO) throws Exception;
}
