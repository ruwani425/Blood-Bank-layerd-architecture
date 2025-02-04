package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse.bbms.bo.BOFactory;
import lk.ijse.gdse.bbms.bo.custom.BloodRequestBO;
import lk.ijse.gdse.bbms.dto.BloodRequestDTO;
import lk.ijse.gdse.bbms.dto.tm.BloodRequestTM;
import lk.ijse.gdse.bbms.dto.tm.DonorTM;
import lk.ijse.gdse.bbms.model.BloodRequestModel;
import lk.ijse.gdse.bbms.model.HospitalModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BloodRequestController implements Initializable {

    @FXML
    private TableView<BloodRequestTM> tblRequest;

    @FXML
    private TableColumn<BloodRequestTM, String> colRequestID;

    @FXML
    private TableColumn<BloodRequestTM, String> colHospitalID;

    @FXML
    private TableColumn<BloodRequestTM, String> colBloodGroup;

    @FXML
    private TableColumn<BloodRequestTM, Date> colDateOfRequest;

    @FXML
    private TableColumn<BloodRequestTM, Double> colBloodQty;

    @FXML
    private TableColumn<BloodRequestTM, String> colStatus;

    @FXML
    private Label lblRequestID;

    @FXML
    private ComboBox<String> cmbBloodGroup;

    @FXML
    private TextField txtBloodQty;

    @FXML
    private Label lblRequestDate;

    @FXML
    private ComboBox<String> cmbHospital;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private JFXButton btnAddRequest;

    BloodRequestModel bloodRequestModel = new BloodRequestModel();
    private HospitalModel hospitalModel = new HospitalModel();
    HomePageViewController homePageViewController;
    BloodRequestBO bloodRequestBO = (BloodRequestBO) BOFactory.getInstance().getBO(BOFactory.BOType.BLOODREQUEST);

    public void setHomePageViewController(HomePageViewController homePageViewController) {
        this.homePageViewController = homePageViewController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblRequestID.setText(bloodRequestBO.getNextRequestId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        populateBloodGroups();
        populateStatus();
        setCellValueFactory();
        loadBloodRequests();

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        lblRequestDate.setText(formattedDate);

        try {
            ArrayList<String> idList = hospitalModel.getAllHospitalIDs();
            cmbHospital.getItems().addAll(idList); // Add items to ComboBox
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tblRequest.setOnMouseClicked(this::handleRowClick);
    }

    private void handleRowClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            BloodRequestTM selectedBloodRequest = tblRequest.getSelectionModel().getSelectedItem();
            if (selectedBloodRequest != null) {
                openBloodIssueWindow(selectedBloodRequest);
            }
        }
    }

    private void openBloodIssueWindow(BloodRequestTM selectedBloodRequest) {
        homePageViewController.navigateWithRequestId(selectedBloodRequest);
    }

    private void setCellValueFactory() {
        colRequestID.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        colHospitalID.setCellValueFactory(new PropertyValueFactory<>("hospitalId"));
        colBloodGroup.setCellValueFactory(new PropertyValueFactory<>("bloodType"));
        colDateOfRequest.setCellValueFactory(new PropertyValueFactory<>("dateOfRequest"));
        colBloodQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    private void loadBloodRequests() {
        try {
            ArrayList<BloodRequestDTO> bloodRequestList = bloodRequestBO.getAllRequests("PENDING");

            ObservableList<BloodRequestTM> observableList = FXCollections.observableArrayList();
            for (BloodRequestDTO dto : bloodRequestList) {
                observableList.add(new BloodRequestTM(
                        dto.getRequestId(),
                        dto.getHospitalId(),
                        dto.getBloodType(),
                        dto.getDateOfRequest(),
                        dto.getQty(),
                        dto.getStatus()
                ));
            }

            tblRequest.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load blood requests!").show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void populateBloodGroups() {
        cmbBloodGroup.getItems().addAll("A_POSITIVE", "A_NEGATIVE", "B_POSITIVE", "B_NEGATIVE", "AB_POSITIVE", "AB_NEGATIVE", "O_POSITIVE", "O_NEGATIVE");
    }

    private void populateStatus() {
        cmbStatus.getItems().addAll("ACTIVE", "INACTIVE", "PENDING", "COMPLETED");
    }

    @FXML
    void btnAddrequestOnAction(ActionEvent event) {
        try {
            String requestId = lblRequestID.getText();
            String hospitalId = cmbHospital.getSelectionModel().getSelectedItem();
            String bloodType = cmbBloodGroup.getSelectionModel().getSelectedItem();
            Date dateOfRequest = Date.valueOf(LocalDate.now());
            double qty = Double.parseDouble(txtBloodQty.getText());
            String status = cmbStatus.getSelectionModel().getSelectedItem();

            BloodRequestDTO bloodRequestDTO = new BloodRequestDTO(
                    requestId, hospitalId, bloodType, dateOfRequest, qty, status
            );

            boolean isSaved = bloodRequestBO.addBloodRequest(bloodRequestDTO);

            if (isSaved) {
                lblRequestID.setText(bloodRequestBO.getNextRequestId());
                new Alert(Alert.AlertType.INFORMATION, "Blood request saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save blood request!").show();
            }

            clearFields();
            refreshTable();

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error occurred!").show();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid quantity value! Please enter a valid number.").show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void clearFields() {
        try {
            txtBloodQty.clear();
            cmbHospital.getSelectionModel().clearSelection();
            cmbBloodGroup.getSelectionModel().clearSelection();
            cmbStatus.getSelectionModel().clearSelection();

            lblRequestID.setText(bloodRequestBO.getNextRequestId());

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            lblRequestDate.setText(currentDate.format(formatter));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to reset fields!").show();
        }
    }
    public void refreshTable() throws SQLException {
        ArrayList<BloodRequestDTO> bloodRequestDTOS = bloodRequestModel.getAllRequests("PENDING");
        ObservableList<BloodRequestTM> bloodRequestTMS = FXCollections.observableArrayList();

        for (BloodRequestDTO bloodRequestDTO : bloodRequestDTOS) {
            BloodRequestTM bloodRequestTM = new BloodRequestTM(
                    bloodRequestDTO.getRequestId(),
                    bloodRequestDTO.getHospitalId(),
                    bloodRequestDTO.getBloodType(),
                    bloodRequestDTO.getDateOfRequest(),
                    bloodRequestDTO.getQty(),
                    bloodRequestDTO.getStatus()
            );
            bloodRequestTMS.add(bloodRequestTM);
        }
        tblRequest.setItems(bloodRequestTMS);
    }
}
