package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.bo.BOFactory;
import lk.ijse.gdse.bbms.bo.custom.HospitalBO;
import lk.ijse.gdse.bbms.dto.HospitalDTO;
import lk.ijse.gdse.bbms.dto.tm.HospitalTM;
import lk.ijse.gdse.bbms.util.Validation;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class HospitalPopUpController implements Initializable {
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private ComboBox<String> cmbHospitalType;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblHospitalId;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton closeBtn;

    private Stage stage=new Stage();

    private HospitalPageController hospitalPageController;
    private HospitalBO hospitalBO= (HospitalBO) BOFactory.getInstance().getBO(BOFactory.BOType.HOSPITAL);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        try {
            lblHospitalId.setText(hospitalBO.getNextHospitalId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        populateHospitalTypes();
    }
    private void populateHospitalTypes() {
        cmbHospitalType.getItems().addAll("Government","Private","Military","Teaching","Base","District","Rural","General");
    }

    public void setHospitalPageController(HospitalPageController hospitalPageController) {
        this.hospitalPageController = hospitalPageController;
    }

    @FXML
    void btnAddHospitalOnAction(ActionEvent event) {
        String nameRegex = "^[A-Za-z\\s]{3,50}$"; // Only letters and spaces, 3-50 characters
        String addressRegex = "^.{5,100}$"; // At least 5 characters for address
        String contactNumberRegex = "^[0-9]{10}$"; // 10 digits for Sri Lankan contact numbers
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // Standard email format

        boolean isNameValid = Validation.validateTextField(txtName, nameRegex, txtName.getText());
        boolean isAddressValid = Validation.validateTextField(txtAddress, addressRegex, txtAddress.getText());
        boolean isContactNumberValid = Validation.validateTextField(txtContactNumber, contactNumberRegex, txtContactNumber.getText());
        boolean isEmailValid = Validation.validateTextField(txtEmail, emailRegex, txtEmail.getText());

        if (!isNameValid || !isAddressValid || !isContactNumberValid || !isEmailValid) {
            new Alert(Alert.AlertType.ERROR, "Please correct the highlighted fields.").show();
            return; // Stop execution if validation fails
        }

        String name = txtName.getText();
        String address = txtAddress.getText();
        String contactNumber = txtContactNumber.getText();
        String email = txtEmail.getText();
        String type = cmbHospitalType.getValue();
        String hospitalId = lblHospitalId.getText();

        HospitalDTO hospitalDTO = new HospitalDTO(hospitalId, name, address, contactNumber, email, type);

        try {
            boolean isAdded = hospitalBO.addHospital(hospitalDTO);
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Hospital added successfully!").show();
                clearFields();
                hospitalPageController.refreshTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add Hospital.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while adding Hospital.").show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnDeleteHospitalOnAction(ActionEvent event) {
        String hospitalId = lblHospitalId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Hospital?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = hospitalBO.deleteHospital(hospitalId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Hospital deleted successfully!").show();
                    clearFields();
                    hospitalPageController.refreshTable();
                    stage = (Stage) btnDelete.getScene().getWindow();
                    stage.close();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete Hospital.").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting Hospital.").show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnUpdateHospitalOnAction(ActionEvent event) {
        String nameRegex = "^[A-Za-z\\s]{3,50}$"; // Only letters and spaces, 3-50 characters
        String addressRegex = "^.{5,100}$"; // At least 5 characters for address
        String contactNumberRegex = "^[0-9]{10}$"; // 10 digits for Sri Lankan contact numbers
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // Standard email format

        boolean isNameValid = Validation.validateTextField(txtName, nameRegex, txtName.getText());
        boolean isAddressValid = Validation.validateTextField(txtAddress, addressRegex, txtAddress.getText());
        boolean isContactNumberValid = Validation.validateTextField(txtContactNumber, contactNumberRegex, txtContactNumber.getText());
        boolean isEmailValid = Validation.validateTextField(txtEmail, emailRegex, txtEmail.getText());

        if (!isNameValid || !isAddressValid || !isContactNumberValid || !isEmailValid) {
            new Alert(Alert.AlertType.ERROR, "Please correct the highlighted fields.").show();
            return;
        }

        String name = txtName.getText();
        String address = txtAddress.getText();
        String contactNumber = txtContactNumber.getText();
        String email = txtEmail.getText();
        String type = cmbHospitalType.getValue();
        String hospitalId = lblHospitalId.getText();

        HospitalDTO hospitalDTO = new HospitalDTO(hospitalId, name, address, contactNumber, email, type);

        try {
            boolean isUpdated = hospitalBO.updateHospital(hospitalDTO);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Hospital updated successfully!").show();
                clearFields();
                hospitalPageController.refreshTable();
                stage = (Stage) btnUpdate.getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Hospital.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while updating Hospital.").show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() throws Exception {
        txtName.clear();
        txtAddress.clear();
        txtContactNumber.clear();
        txtEmail.clear();
        cmbHospitalType.setValue(null);
        lblHospitalId.setText(hospitalBO.getNextHospitalId());
    }
    public void setHospitalData(HospitalTM hospital) {
        btnAdd.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);

        lblHospitalId.setText(hospital.getHospitalId());
        txtName.setText(hospital.getHospitalName());
        txtAddress.setText(hospital.getHospitalAddress());
        txtContactNumber.setText(hospital.getContactNumber());
        txtEmail.setText(hospital.getEmail());
        cmbHospitalType.setValue(hospital.getType());
    }
}
