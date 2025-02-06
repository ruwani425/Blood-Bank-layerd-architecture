package lk.ijse.gdse.bbms.bo.custom.impl;

import lk.ijse.gdse.bbms.bo.custom.EmployeeBO;
import lk.ijse.gdse.bbms.dao.DAOFactory;
import lk.ijse.gdse.bbms.dao.custom.EmployeeDAO;
import lk.ijse.gdse.bbms.dao.custom.impl.EmployeeDAOImpl;
import lk.ijse.gdse.bbms.dto.EmployeeDTO;
import lk.ijse.gdse.bbms.entity.Employee;

import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    private EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public boolean addEmployee(EmployeeDTO employeeDTO) throws Exception {
        Employee employee = new Employee();
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
        Employee employee = new Employee();
        employee.setEmployeeID(employeeId);
        return employeeDAO.delete(employee);
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws Exception {
        Employee employee = new Employee();
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

    @Override
    public ArrayList<EmployeeDTO> getAllEmployees() throws Exception {
        ArrayList<EmployeeDTO>employeeDTOS = new ArrayList<>();
        ArrayList<Employee>employees=employeeDAO.getAllData();
        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmployeeID(employee.getEmployeeID());
            employeeDTO.setStatus(employee.getStatus());
            employeeDTO.setAddress(employee.getAddress());
            employeeDTO.setName(employee.getName());
            employeeDTO.setNic(employee.getNic());
            employeeDTO.setEmail(employee.getEmail());
            employeeDTO.setRole(employee.getRole());
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }
}
