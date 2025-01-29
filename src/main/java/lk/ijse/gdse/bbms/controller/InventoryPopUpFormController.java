package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.dto.InventoryDTO;
import lk.ijse.gdse.bbms.dto.SupplierDTO;
import lk.ijse.gdse.bbms.dto.tm.InventoryTM;
import lk.ijse.gdse.bbms.model.InventoryModel;
import lk.ijse.gdse.bbms.model.SupplierModel;

import java.sql.Date;
import java.sql.SQLException;

public class InventoryPopUpFormController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQty;

    @FXML
    private DatePicker datePikcerExpiry;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private Label lblInventoryID;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private ComboBox<String> cmbSupplier;

    @FXML
    private Label lblSupplierName;


    Stage stage = new Stage();

    private final InventoryModel inventoryModel = new InventoryModel();
    private final SupplierModel supplierModel = new SupplierModel();

    private InventoryPageController inventoryPageController;

    public void setInventoryPageController(InventoryPageController inventoryPageController) {
        this.inventoryPageController = inventoryPageController;
    }

    @FXML
    public void initialize() {
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        try {
            lblInventoryID.setText(inventoryModel.getNextInventoryId());
            init();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error generating Inventory ID.").show();
        }
    }

    private void init() throws SQLException {
        cmbStatus.getItems().addAll("AVAILABLE", "RESERVED", "EXPIRED", "DAMAGED");
        cmbSupplier.getItems().addAll(supplierModel.getAllSupplierIDs());
    }

    @FXML
    void btnAddInventoryOnAction(ActionEvent event) {
        String inventoryId = lblInventoryID.getText();
        String itemName = txtName.getText();
        String status = cmbStatus.getValue();
        Date expiryDate = Date.valueOf(datePikcerExpiry.getValue());
        int qty = Integer.parseInt(txtQty.getText());

        InventoryDTO inventoryDTO = new InventoryDTO(inventoryId, itemName, status, expiryDate, qty);

        try {
            boolean isAdded = inventoryModel.addInventoryItem(inventoryDTO, cmbSupplier.getValue());
            if (isAdded) {
                inventoryPageController.refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "Inventory item added successfully!").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add Inventory item.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while adding Inventory item.").show();
        }
    }

    @FXML
    void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnDeleteInventoryOnAction(ActionEvent event) {
        String inventoryId = lblInventoryID.getText();

        try {
            boolean isDeleted = inventoryModel.deleteInventoryItem(inventoryId);
            if (isDeleted) {
                inventoryPageController.refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "Inventory item deleted successfully!").show();
                stage = (Stage) btnDelete.getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete Inventory item.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while deleting Inventory item.").show();
        }
    }

    @FXML
    void btnUpdateInventoryOnAction(ActionEvent event) {
        String inventoryId = lblInventoryID.getText();
        String itemName = txtName.getText();
        String status = cmbStatus.getValue();
        Date expiryDate = Date.valueOf(datePikcerExpiry.getValue());
        int qty = Integer.parseInt(txtQty.getText());

        InventoryDTO inventoryDTO = new InventoryDTO(inventoryId, itemName, status, expiryDate, qty);

        try {
            boolean isUpdated = inventoryModel.updateInventoryItem(inventoryDTO);
            if (isUpdated) {
                inventoryPageController.refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "Inventory item updated successfully!").show();
                stage = (Stage) btnDelete.getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Inventory item.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while updating Inventory item.").show();
        }
    }

    private void clearFields() throws SQLException {
        txtName.clear();
        txtQty.clear();
        cmbStatus.setValue(null);
        datePikcerExpiry.setValue(null);
        lblInventoryID.setText(inventoryModel.getNextInventoryId());
    }

    public void setInventoryData(InventoryTM selectedItem) {
        lblInventoryID.setText(selectedItem.getInventoryId());
        txtName.setText(selectedItem.getItemName());
        cmbStatus.setValue(selectedItem.getStatus());
        datePikcerExpiry.setValue(selectedItem.getExpiryDate().toLocalDate());
        txtQty.setText(String.valueOf(selectedItem.getQty()));

        btnAdd.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }

    @FXML
    void cmbSupplierOnAction(ActionEvent event) throws SQLException {
        SupplierDTO supplierDTO = supplierModel.getSupplierById(cmbSupplier.getValue());
        lblSupplierName.setText(supplierDTO.getSupplierName());
    }
}
