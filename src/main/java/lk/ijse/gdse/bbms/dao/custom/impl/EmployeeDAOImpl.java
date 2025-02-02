package lk.ijse.gdse.bbms.dao.custom.impl;

import lk.ijse.gdse.bbms.dao.custom.EmployeeDAO;
import lk.ijse.gdse.bbms.entity.Employee;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAllData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Employee employee) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Employee (Employee_id, Name, Nic, Address, E_mail, Role, Status) VALUES (?,?,?,?,?,?,?)",
                employee.getEmployeeID(),
                employee.getName(),
                employee.getNic(),
                employee.getAddress(),
                employee.getEmail(),
                employee.getRole(),
                employee.getStatus()
        );
    }

    @Override
    public boolean update(Employee employee) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Employee SET Name=?, Nic=?, Address=?, E_mail=?, Role=?, Status=? WHERE Employee_id=?",
                employee.getName(),
                employee.getNic(),
                employee.getAddress(),
                employee.getEmail(),
                employee.getRole(),
                employee.getStatus(),
                employee.getEmployeeID()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Employee employee) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Employee WHERE Employee_id=?", employee.getEmployeeID());
    }

    @Override
    public String getNewId() throws SQLException, ClassNotFoundException {
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

    @Override
    public ArrayList<Employee> search(String newValue) throws SQLException, ClassNotFoundException {
        return null;
    }
}
