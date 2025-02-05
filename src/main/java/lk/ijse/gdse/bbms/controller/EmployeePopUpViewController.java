package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.bo.BOFactory;
import lk.ijse.gdse.bbms.bo.custom.EmployeeBO;
import lk.ijse.gdse.bbms.dto.EmployeeDTO;
import lk.ijse.gdse.bbms.dto.tm.EmployeeTM;
import lk.ijse.gdse.bbms.util.Validation;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeePopUpViewController implements Initializable {

    @FXML
    private Label lblEmpId;

    @FXML
    private TextField txtEmpAddress;

    @FXML
    private ComboBox<String> cmbEmpStatus;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtEmpEmail;

    @FXML
    private TextField txtEmpRole;

    private EmployeePageController employeePageController;
    EmployeeBO employeeBO= (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

    public void setEmployeePageViewController(EmployeePageController employeePageController) {
        this.employeePageController = employeePageController;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        try {
            lblEmpId.setText(employeeBO.getNextEmployeeId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cmbEmpStatus.getItems().addAll("ACTIVE", "INACTIVE", "SUSPENDED");
    }

    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) {
        String nameRegex = "^[A-Za-z\\s]{3,50}$"; // Only letters and spaces, 3-50 characters
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // Standard email format
        String nicRegex = "^[0-9]{9}[vVxX]|[0-9]{12}$"; // Sri Lankan NIC format
        String addressRegex = "^.{5,100}$"; // Address should be between 5 and 100 characters
        String roleRegex = "^[A-Za-z\\s]{3,50}$"; // Role should be between 3 and 50 characters

        boolean isNameValid = Validation.validateTextField(txtEmployeeName, nameRegex, txtEmployeeName.getText());
        boolean isEmailValid = Validation.validateTextField(txtEmpEmail, emailRegex, txtEmpEmail.getText());
        boolean isNicValid = Validation.validateTextField(txtNic, nicRegex, txtNic.getText());
        boolean isAddressValid = Validation.validateTextField(txtEmpAddress, addressRegex, txtEmpAddress.getText());
        boolean isRoleValid = Validation.validateTextField(txtEmpRole, roleRegex, txtEmpRole.getText());

        if (!isNameValid || !isEmailValid || !isNicValid || !isAddressValid || !isRoleValid) {
            new Alert(Alert.AlertType.ERROR, "Please correct the highlighted fields.").show();
            return;
        }

        String name = txtEmployeeName.getText();
        String email = txtEmpEmail.getText();
        String nic = txtNic.getText();
        String address = txtEmpAddress.getText();
        String role = txtEmpRole.getText();
        String status = cmbEmpStatus.getValue();
        String employeeId = lblEmpId.getText();

        EmployeeDTO employeeDTO = new EmployeeDTO(employeeId, name, nic, address, email, role, status);
        try {
            boolean isAdded = employeeBO.addEmployee(employeeDTO);
            if (isAdded) {
                lblEmpId.setText(employeeBO.getNextEmployeeId());
                new Alert(Alert.AlertType.INFORMATION, "Employee added successfully!").show();
                employeePageController.refreshTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add employee!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error occurred").show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteEmployeeOnAction(ActionEvent event) {
        String employeeId = lblEmpId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this employee?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = employeeBO.deleteEmployee(employeeId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully!").show();
                    employeePageController.refreshTable();
                    closeWindow();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete employee!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error occurred").show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnUpdateEmployeeOnAction(ActionEvent event) {
        String nameRegex = "^[A-Za-z\\s]{3,50}$";
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String nicRegex = "^[0-9]{9}[vVxX]|[0-9]{12}$";
        String addressRegex = "^.{5,100}$";
        String roleRegex = "^[A-Za-z\\s]{3,50}$";

        boolean isNameValid = Validation.validateTextField(txtEmployeeName, nameRegex, txtEmployeeName.getText());
        boolean isEmailValid = Validation.validateTextField(txtEmpEmail, emailRegex, txtEmpEmail.getText());
        boolean isNicValid = Validation.validateTextField(txtNic, nicRegex, txtNic.getText());
        boolean isAddressValid = Validation.validateTextField(txtEmpAddress, addressRegex, txtEmpAddress.getText());
        boolean isRoleValid = Validation.validateTextField(txtEmpRole, roleRegex, txtEmpRole.getText());

        if (!isNameValid || !isEmailValid || !isNicValid || !isAddressValid || !isRoleValid) {
            new Alert(Alert.AlertType.ERROR, "Please correct the highlighted fields.").show();
            return;
        }

        String name = txtEmployeeName.getText();
        String email = txtEmpEmail.getText();
        String nic = txtNic.getText();
        String address = txtEmpAddress.getText();
        String role = txtEmpRole.getText();
        String status = cmbEmpStatus.getValue();
        String employeeId = lblEmpId.getText();

        EmployeeDTO employeeDTO = new EmployeeDTO(employeeId, name, nic, address, email, role, status);
        try {
            boolean isUpdated = employeeBO.updateEmployee(employeeDTO);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully!").show();
                employeePageController.refreshTable();
                closeWindow();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update employee!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error occurred").show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void setEmployeeData(EmployeeTM employee) {
        btnAdd.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);

        lblEmpId.setText(employee.getEmployeeID());
        txtEmployeeName.setText(employee.getName());
        txtNic.setText(employee.getNic());
        txtEmpAddress.setText(employee.getAddress());
        txtEmpEmail.setText(employee.getEmail());
        txtEmpRole.setText(employee.getRole());
        cmbEmpStatus.setValue(employee.getStatus());
        btnAdd.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

    private void clearFields() {
        txtEmployeeName.clear();
        txtNic.clear();
        txtEmpAddress.clear();
        txtEmpEmail.clear();
        txtEmpRole.clear();
        cmbEmpStatus.getSelectionModel().clearSelection();
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }
}
