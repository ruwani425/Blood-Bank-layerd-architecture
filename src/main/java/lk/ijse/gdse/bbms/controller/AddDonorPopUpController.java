package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.bo.BOFactory;
import lk.ijse.gdse.bbms.bo.custom.DonorBO;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.dto.tm.DonorTM;
import lk.ijse.gdse.bbms.util.Validation;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;


public class AddDonorPopUpController implements Initializable {

    @FXML
    public JFXButton closeBtn;

    @FXML
    private TextField txtDonorAddress;

    @FXML
    private DatePicker txtDonorDob;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private TextField txtDonorName;

    @FXML
    private TextField txtDonorEmail;

    @FXML
    private TextField txtDonorNic;

    @FXML
    private ComboBox<String> txtDonorBloodGroup;

    @FXML
    private ComboBox<String> txtDonorGender;

    @FXML
    private Label lblDonorId;

    @FXML
    private Stage stage;

    private DonorPageViewController donorPageViewController;
    private DonorBO donorBO = (DonorBO) BOFactory.getInstance().getBO(BOFactory.BOType.DONOR);

    public void setDonorPageViewController(DonorPageViewController donorPageViewController) {
        this.donorPageViewController = donorPageViewController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdd.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        try {
            lblDonorId.setText(donorBO.getNextDonorId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        populateDonorGender();
        populateDonorBloodGroup();
    }

    private void populateDonorGender() {
        txtDonorGender.getItems().addAll("MALE", "FEMALE", "OTHER");
    }

    private void populateDonorBloodGroup() {
        txtDonorBloodGroup.getItems().addAll("A_POSITIVE", "A_NEGATIVE", "B_POSITIVE", "B_NEGATIVE", "AB_POSITIVE", "AB_NEGATIVE", "O_POSITIVE", "O_NEGATIVE");
    }

    @FXML
    void btnAddDonorOnAction(ActionEvent event) throws Exception {
        String nameRegex = "^[A-Za-z\\s]{3,50}$"; // Only letters and spaces, 3-50 characters
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // Standard email format
        String nicRegex = "^[0-9]{9}[vVxX]|[0-9]{12}$"; // Sri Lankan NIC format
        String addressRegex = "^.{5,100}$"; // At least 5 characters
        String dobRegex = "^\\d{4}-\\d{2}-\\d{2}$"; // Date in yyyy-MM-dd format

        boolean isNameValid = Validation.validateTextField(txtDonorName, nameRegex, txtDonorName.getText());
        boolean isEmailValid = Validation.validateTextField(txtDonorEmail, emailRegex, txtDonorEmail.getText());
        boolean isNicValid = Validation.validateTextField(txtDonorNic, nicRegex, txtDonorNic.getText());
        boolean isAddressValid = Validation.validateTextField(txtDonorAddress, addressRegex, txtDonorAddress.getText());

        if (!isNameValid || !isEmailValid || !isNicValid || !isAddressValid) {
            new Alert(Alert.AlertType.ERROR, "Please correct the highlighted fields.").show();
            return;
        }

        String name = txtDonorName.getText();
        String email = txtDonorEmail.getText();
        String nic = txtDonorNic.getText();
        String bloodGroup = txtDonorBloodGroup.getSelectionModel().getSelectedItem().toString();
        String gender = txtDonorGender.getSelectionModel().getSelectedItem().toString();
        Date dob = Date.valueOf(txtDonorDob.getValue().toString());
        String address = txtDonorAddress.getText();
        String id = lblDonorId.getText();

        DonorDTO donorDTO = new DonorDTO(id, name, nic, address, email, bloodGroup, gender, dob, null);
        boolean isSaved = donorBO.addDonor(donorDTO);

        if (isSaved) {
            try {
                lblDonorId.setText(donorBO.getNextDonorId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            donorPageViewController.refreshTable();
            new Alert(Alert.AlertType.INFORMATION, "Donor saved successfully...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save Donor...!").show();
        }
        clearFields();
    }

    @FXML
    void btnDeleteDonorOnAction(ActionEvent event) throws Exception {
        String donorId = lblDonorId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Donor?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = donorBO.deleteDonor(donorId);

            if (isDeleted) {
                donorPageViewController.refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "Donor deleted successfully...!").show();
                stage = (Stage) btnDelete.getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Donor...!").show();
            }
        }
        clearFields();
    }

    @FXML
    void btnUpdateDonorOnAction(ActionEvent event) throws Exception {
        String nameRegex = "^[A-Za-z\\s]{3,50}$"; // Only letters and spaces, 3-50 characters
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // Standard email format
        String nicRegex = "^[0-9]{9}[vVxX]|[0-9]{12}$"; // Sri Lankan NIC format
        String addressRegex = "^.{5,100}$"; // At least 5 characters

        boolean isNameValid = Validation.validateTextField(txtDonorName, nameRegex, txtDonorName.getText());
        boolean isEmailValid = Validation.validateTextField(txtDonorEmail, emailRegex, txtDonorEmail.getText());
        boolean isNicValid = Validation.validateTextField(txtDonorNic, nicRegex, txtDonorNic.getText());
        boolean isAddressValid = Validation.validateTextField(txtDonorAddress, addressRegex, txtDonorAddress.getText());

        if (!isNameValid || !isEmailValid || !isNicValid || !isAddressValid) {
            new Alert(Alert.AlertType.ERROR, "Please correct the highlighted fields.").show();
            return;
        }

        String name = txtDonorName.getText();
        String email = txtDonorEmail.getText();
        String nic = txtDonorNic.getText();
        String bloodGroup = txtDonorBloodGroup.getSelectionModel().getSelectedItem().toString();
        String gender = txtDonorGender.getSelectionModel().getSelectedItem().toString();
        Date dob = Date.valueOf(txtDonorDob.getValue().toString());
        String address = txtDonorAddress.getText();
        String id = lblDonorId.getText();

        DonorDTO donorDTO = new DonorDTO(id, name, nic, address, email, bloodGroup, gender, dob, null);
        boolean isUpdate = donorBO.updateDonor(donorDTO);

        if (isUpdate) {
            donorPageViewController.refreshTable();
            new Alert(Alert.AlertType.INFORMATION, "Donor updated successfully...!").show();

            clearFields();
            Stage stage = (Stage) btnUpdate.getScene().getWindow();
            stage.close();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update Donor...!").show();
        }
    }


    public void setDonorData(DonorTM donor) {
        btnAdd.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);

        lblDonorId.setText(donor.getDonorId());
        txtDonorName.setText(donor.getDonorName());
        txtDonorNic.setText(donor.getDonorNic());
        txtDonorAddress.setText(donor.getDonorAddress());
        txtDonorEmail.setText(donor.getDonorEmail());
        txtDonorBloodGroup.setValue(donor.getBloodGroup());
        txtDonorGender.setValue(donor.getGender());

        if (donor.getDob() != null) {
            Date sqlDate = (Date) donor.getDob();
            LocalDate dob = sqlDate.toLocalDate();
            txtDonorDob.setValue(dob);
        }
    }

    @FXML
    private void clearFields() throws Exception {
        txtDonorAddress.clear();
        txtDonorDob.setValue(null);
        txtDonorEmail.clear();
        txtDonorNic.clear();
        txtDonorName.clear();
        txtDonorBloodGroup.getSelectionModel().clearSelection();
        txtDonorGender.getSelectionModel().clearSelection();
        lblDonorId.setText(donorBO.getNextDonorId());

        resetFieldBorder(txtDonorName);
        resetFieldBorder(txtDonorEmail);
        resetFieldBorder(txtDonorNic);
        resetFieldBorder(txtDonorAddress);
    }

    private void resetFieldBorder(TextField textField) {
        textField.setBorder(Border.EMPTY);
    }


    public void btnCloseOnAction(ActionEvent actionEvent) {
        stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
