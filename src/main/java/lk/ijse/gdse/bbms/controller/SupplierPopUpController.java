package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.bo.BOFactory;
import lk.ijse.gdse.bbms.bo.custom.SupplierBO;
import lk.ijse.gdse.bbms.dto.SupplierDTO;
import lk.ijse.gdse.bbms.dto.tm.SupplierTM;
import lk.ijse.gdse.bbms.model.SupplierModel;

import java.sql.SQLException;
import java.util.Optional;

public class SupplierPopUpController {

    @FXML
    private Label lblSupplierID;

    @FXML
    private TextField txtDescription;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    Stage stage=new Stage();

    SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);
    private SupplierPageController supplierPageController;

    public void setSupplierPageController(SupplierPageController supplierPageController) {
        this.supplierPageController = supplierPageController;
    }


    @FXML
    public void initialize() throws Exception {
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        lblSupplierID.setText(supplierBO.getNextSupplierId());
    }

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        String supplierId = lblSupplierID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String description = txtDescription.getText();

        SupplierDTO supplierDTO = new SupplierDTO(supplierId, name, address, email, description);

        try {
            boolean isAdded = supplierBO.addSupplier(supplierDTO);
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier added successfully!").show();
                supplierPageController.refreshTable();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add Supplier.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while adding Supplier.").show();
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
    void btnDeleteSupplierOnAction(ActionEvent event) throws Exception {
        String supplierID = lblSupplierID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this supplier?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = supplierBO.deleteSupplier(supplierID);

            if (isDeleted) {
                supplierPageController.refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "supplier deleted successfully...!").show();
                stage = (Stage) btnDelete.getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete supplier...!").show();
            }
        }
        clearFields();
    }


    @FXML
    void btnUpdateSupplierOnAction(ActionEvent event) {
        String supplierId = lblSupplierID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String description = txtDescription.getText();

        SupplierDTO supplierDTO = new SupplierDTO(supplierId, name, address, email, description);

        try {
            boolean isUpdated = supplierBO.updateSupplier(supplierDTO);
            if (isUpdated) {
                supplierPageController.refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully!").show();
                Stage stage = (Stage) closeBtn.getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Supplier.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while updating Supplier.").show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() throws Exception {
        txtName.clear();
        txtAddress.clear();
        txtEmail.clear();
        txtDescription.clear();
        lblSupplierID.setText(supplierBO.getNextSupplierId());
    }

    public void setSupplierData(SupplierTM supplier) {
        btnAdd.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);

        if (supplier != null) {
            lblSupplierID.setText(supplier.getSupplierId());
            txtName.setText(supplier.getSupplierName());
            txtAddress.setText(supplier.getAddress());
            txtEmail.setText(supplier.getEmail());
            txtDescription.setText(supplier.getDescription());

            // Disable the ID field to prevent editing
            lblSupplierID.setDisable(true);
        }
    }
}
