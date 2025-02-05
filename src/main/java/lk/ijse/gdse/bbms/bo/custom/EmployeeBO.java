package lk.ijse.gdse.bbms.bo.custom;

import lk.ijse.gdse.bbms.bo.SuperBO;
import lk.ijse.gdse.bbms.dto.EmployeeDTO;

import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    boolean addEmployee(EmployeeDTO employeeDTO) throws Exception;

    boolean deleteEmployee(String employeeId)throws Exception;

    boolean updateEmployee(EmployeeDTO employeeDTO)throws Exception;

    String getNextEmployeeId()throws Exception;

    ArrayList<EmployeeDTO> getAllEmployees()throws Exception;
}
