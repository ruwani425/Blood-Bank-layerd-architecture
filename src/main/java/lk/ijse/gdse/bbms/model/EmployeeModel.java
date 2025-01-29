package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.dto.EmployeeDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {

    public String getNextEmployeeId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Employee_id FROM Employee ORDER BY Employee_id DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last employee ID
            String substring = lastId.substring(1); // Extract the numeric part (assumes ID starts with a letter, e.g., "E001")
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the numeric part by 1
            return String.format("E%03d", newIdIndex); // Return the new Employee ID in format E001, E002, etc.
        }
        return "E001"; // Return the default employee ID if no data is found
    }

    public boolean addEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Employee (Employee_id, Name, Nic, Address, E_mail, Role, Status) VALUES (?,?,?,?,?,?,?)",
                employeeDTO.getEmployeeID(),
                employeeDTO.getName(),
                employeeDTO.getNic(),
                employeeDTO.getAddress(),
                employeeDTO.getEmail(),
                employeeDTO.getRole(),
                employeeDTO.getStatus()
        );
    }

    public boolean deleteEmployee(String employeeId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Employee WHERE Employee_id=?", employeeId);
    }

    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Employee SET Name=?, Nic=?, Address=?, E_mail=?, Role=?, Status=? WHERE Employee_id=?",
                employeeDTO.getName(),
                employeeDTO.getNic(),
                employeeDTO.getAddress(),
                employeeDTO.getEmail(),
                employeeDTO.getRole(),
                employeeDTO.getStatus(),
                employeeDTO.getEmployeeID()
        );
    }

    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException {
        ArrayList<EmployeeDTO> employeeList = new ArrayList<>();

        ResultSet rst = CrudUtil.execute("SELECT Employee_id, Name, Nic, Address, E_mail, Role, Status FROM Employee");

        while (rst.next()) {
            String employeeId = rst.getString("Employee_id");
            String name = rst.getString("Name");
            String nic = rst.getString("Nic");
            String address = rst.getString("Address");
            String email = rst.getString("E_mail");
            String role = rst.getString("Role");
            String status = rst.getString("Status");

            EmployeeDTO employeeDTO = new EmployeeDTO(employeeId, name, nic, address, email, role, status);
            employeeList.add(employeeDTO);
        }

        return employeeList;
    }

}
