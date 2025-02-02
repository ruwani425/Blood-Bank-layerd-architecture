package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.EmployeeBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.EmployeeDAO;
import lk.ijse.gdse.bbms.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.gdse.bbms.dto.EmployeeDTO;
import lk.ijse.gdse.bbms.entity.Employee;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);
    Employee employee = new Employee();

    @Override
    public boolean addEmployee(EmployeeDTO employeeDTO) throws Exception {
        employee.setEmployeeID(employeeDTO.getEmployeeID());
        employee.setStatus(employeeDTO.getStatus());
        employee.setAddress(employeeDTO.getAddress());
        employee.setName(employeeDTO.getName());
        employee.setNic(employeeDTO.getNic());
        employee.setEmail(employeeDTO.getEmail());
        employee.setRole(employeeDTO.getRole());
        return employeeDAO.save(employee);
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws Exception {
        employee.setEmployeeID(employeeId);
        return employeeDAO.delete(employee);
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws Exception {
        employee.setEmployeeID(employeeDTO.getEmployeeID());
        employee.setStatus(employeeDTO.getStatus());
        employee.setAddress(employeeDTO.getAddress());
        employee.setName(employeeDTO.getName());
        employee.setNic(employeeDTO.getNic());
        employee.setEmail(employeeDTO.getEmail());
        employee.setRole(employeeDTO.getRole());
        return employeeDAO.update(employee);
    }

    @Override
    public String getNextEmployeeId() throws Exception {
        return employeeDAO.getNewId();
    }
}
