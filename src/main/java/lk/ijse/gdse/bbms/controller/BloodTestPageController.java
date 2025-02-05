package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.bo.BOFactory;
import lk.ijse.gdse.bbms.bo.custom.BloodTestBO;
import lk.ijse.gdse.bbms.dto.BloodTestDTO;
import lk.ijse.gdse.bbms.dto.tm.BloodTestTM;
import lk.ijse.gdse.bbms.model.BloodTestModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class BloodTestPageController implements Initializable {

    @FXML
    private TableView<BloodTestTM> tblBloodTest;

    @FXML
    private TableColumn<BloodTestTM, String> colTestId;

    @FXML
    private TableColumn<BloodTestTM, String> colDonationID;

    @FXML
    private TableColumn<BloodTestTM, Date> coltestdate;

    @FXML
    private TableColumn<BloodTestTM, String> colResult;

    @FXML
    private TableColumn<BloodTestTM, Double> colHaemoglobin;

    @FXML
    private TableColumn<BloodTestTM, Date> colCollectedDate;

    @FXML
    private TableColumn<BloodTestTM, String> colSerialNum;

    @FXML
    private TableColumn<BloodTestTM, Float> colPlatelets;

    @FXML
    private TableColumn<BloodTestTM, Double> colRedBloodCells;

    @FXML
    private TableColumn<BloodTestTM, Double> colWhiteBloodCells;

    @FXML
    private TableColumn<BloodTestTM, Double> colBloodQty;

    @FXML
    private Label lblTestId;

    @FXML
    private Label lblCollectedDate;

    @FXML
    private TextField txtHaemoglobin;

    @FXML
    private TextField txtSerialNum;

    @FXML
    private TextField txtPlatelets;

    @FXML
    private TextField txtRedBloodCells;

    @FXML
    private TextField txtWhiteBloodCells;

    @FXML
    private Label lblTestDate;

    @FXML
    private DatePicker datePikerExpiryDate;

    @FXML
    private Label lblBloodType;

    @FXML
    private ComboBox<String> cmbResult;

    @FXML
    private JFXButton BtnUpdate;

    @FXML
    private JFXButton pendingBtn;

    @FXML
    private JFXButton finishedBtn;

    @FXML
    private JFXButton btnChooseFile;

    @FXML
    private TextField txtBloodQty;

    String imageUrl;

    BloodTestBO bloodTestBO = (BloodTestBO) BOFactory.getInstance().getBO(BOFactory.BOType.BLOODTEST);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setCellValueFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            setComboBoxValues();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tblBloodTest.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    populateFields(newValue);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setComboBoxValues() {
        cmbResult.setItems(FXCollections.observableArrayList("PASS", "FAIL"));
    }

    private void populateFields(BloodTestTM selectedTest) throws SQLException {
        lblTestId.setText(selectedTest.getTestID());
        BloodTestDTO bloodTestDetails = bloodTestBO.getBloodTestDetailById(selectedTest.getTestID());
        lblCollectedDate.setText(String.valueOf(bloodTestDetails.getCollectedDate()));
        txtHaemoglobin.setText(String.valueOf(selectedTest.getHaemoglobin()));
        txtSerialNum.setText(selectedTest.getReportSerialNum());
        txtPlatelets.setText(String.valueOf(selectedTest.getPlatelets()));
        txtRedBloodCells.setText(String.valueOf(selectedTest.getRedBloodCells()));
        txtWhiteBloodCells.setText(String.valueOf(selectedTest.getWhiteBloodCells()));
        lblTestDate.setText(java.time.LocalDate.now().toString());
        lblBloodType.setText(bloodTestDetails.getBloodType());
    }

    private void setCellValueFactory() {
        colTestId.setCellValueFactory(new PropertyValueFactory<>("testID"));
        colDonationID.setCellValueFactory(new PropertyValueFactory<>("donationID"));
        coltestdate.setCellValueFactory(new PropertyValueFactory<>("testDate"));
        colCollectedDate.setCellValueFactory(new PropertyValueFactory<>("collectedDate"));
        colSerialNum.setCellValueFactory(new PropertyValueFactory<>("reportSerialNum"));
        colResult.setCellValueFactory(new PropertyValueFactory<>("testResult"));
        colHaemoglobin.setCellValueFactory(new PropertyValueFactory<>("haemoglobin"));
        colPlatelets.setCellValueFactory(new PropertyValueFactory<>("platelets"));
        colRedBloodCells.setCellValueFactory(new PropertyValueFactory<>("redBloodCells"));
        colWhiteBloodCells.setCellValueFactory(new PropertyValueFactory<>("whiteBloodCells"));
        colBloodQty.setCellValueFactory(new PropertyValueFactory<>("bloodQty"));
    }

    public void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<BloodTestDTO> bloodTestDTOS = bloodTestBO.getAllBloodTestsBystatus("PENDING");
        ObservableList<BloodTestTM> bloodTestTMS = FXCollections.observableArrayList();

        for (BloodTestDTO bloodTestDTO : bloodTestDTOS) {
            BloodTestTM bloodTestTM = new BloodTestTM(
                    bloodTestDTO.getTestID(),
                    bloodTestDTO.getDonationID(),
                    bloodTestDTO.getCollectedDate(),
                    bloodTestDTO.getTestResult(),
                    bloodTestDTO.getHaemoglobin(),
                    bloodTestDTO.getTestDate(),
                    bloodTestDTO.getReportSerialNum(),
                    bloodTestDTO.getPlatelets(),
                    bloodTestDTO.getRedBloodCells(),
                    bloodTestDTO.getWhiteBloodCells(),
                    bloodTestDTO.getBloodQty()
            );
            bloodTestTMS.add(bloodTestTM);
        }

        tblBloodTest.setItems(bloodTestTMS);
    }

    public void getByStatus(String result) throws SQLException, ClassNotFoundException {
        ArrayList<BloodTestDTO> bloodTestDTOS = bloodTestBO.getAllBloodTestsBystatus(result);
        ObservableList<BloodTestTM> bloodTestTMS = FXCollections.observableArrayList();

        for (BloodTestDTO bloodTestDTO : bloodTestDTOS) {
            BloodTestTM bloodTestTM = new BloodTestTM(
                    bloodTestDTO.getTestID(),
                    bloodTestDTO.getDonationID(),
                    bloodTestDTO.getCollectedDate(),
                    bloodTestDTO.getTestResult(),
                    bloodTestDTO.getHaemoglobin(),
                    bloodTestDTO.getTestDate(),
                    bloodTestDTO.getReportSerialNum(),
                    bloodTestDTO.getPlatelets(),
                    bloodTestDTO.getRedBloodCells(),
                    bloodTestDTO.getWhiteBloodCells(),
                    bloodTestDTO.getBloodQty()
            );
            bloodTestTMS.add(bloodTestTM);
        }

        tblBloodTest.setItems(bloodTestTMS);
    }

    @FXML
    void btnChooseFileOnFile(ActionEvent event) {
        Stage stage = new Stage();

        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getAbsolutePath());

            String targetDirectory = "src/main/resources/images/";
            try {
                Path targetPath = Paths.get(targetDirectory);
                if (!Files.exists(targetPath)) {
                    Files.createDirectories(targetPath);
                }

                Path targetFile = targetPath.resolve(selectedFile.getName());

                Files.copy(selectedFile.toPath(), targetFile);

                System.out.println("File copied to: " + targetFile.toAbsolutePath());
                imageUrl = targetFile.toAbsolutePath().toString();
            } catch (IOException e) {
                System.err.println("Failed to copy file: " + e.getMessage());
            }
        } else {
            System.out.println("File selection canceled.");
        }
    }

    @FXML
    void btnFinishedOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        getByStatus();
    }

    private void getByStatus() throws SQLException, ClassNotFoundException {
        ArrayList<BloodTestDTO> bloodTestDTOS = bloodTestBO.getAllBloodTests();
        ObservableList<BloodTestTM> bloodTestTMS = FXCollections.observableArrayList();

        for (BloodTestDTO bloodTestDTO : bloodTestDTOS) {
            BloodTestTM bloodTestTM = new BloodTestTM(
                    bloodTestDTO.getTestID(),
                    bloodTestDTO.getDonationID(),
                    bloodTestDTO.getCollectedDate(),
                    bloodTestDTO.getTestResult(),
                    bloodTestDTO.getHaemoglobin(),
                    bloodTestDTO.getTestDate(),
                    bloodTestDTO.getReportSerialNum(),
                    bloodTestDTO.getPlatelets(),
                    bloodTestDTO.getRedBloodCells(),
                    bloodTestDTO.getWhiteBloodCells(),
                    bloodTestDTO.getBloodQty()
            );
            bloodTestTMS.add(bloodTestTM);
        }

        tblBloodTest.setItems(bloodTestTMS);
    }

    @FXML
    void btnPendingOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        getByStatus("PENDING");
    }

    @FXML
    void BtnUpdateBloodTestOnAction(ActionEvent event) throws SQLException {
        try {
            BloodTestDTO bloodTestDTO = new BloodTestDTO(
                    lblTestId.getText(), // TestID
                    null, // DonationID (not available in UI)
                    Date.valueOf(lblCollectedDate.getText()), // CollectedDate
                    Date.valueOf(datePikerExpiryDate.getValue()), // ExpiryDate
                    cmbResult.getValue(), // TestResult
                    Double.parseDouble(txtHaemoglobin.getText()), // Haemoglobin
                    Date.valueOf(lblTestDate.getText()), // TestDate
                    txtSerialNum.getText(), // ReportSerialNum
                    Float.parseFloat(txtPlatelets.getText()), // Platelets (modified to float)
                    Double.parseDouble(txtRedBloodCells.getText()), // RedBloodCells
                    Double.parseDouble(txtWhiteBloodCells.getText()), // WhiteBloodCells
                    imageUrl, // ReportImageUrl (optional)
                    lblBloodType.getText(), // BloodGroup
                    Double.parseDouble(txtBloodQty.getText())
            );

            boolean isUpdate = bloodTestBO.updateBloodTest(bloodTestDTO);

            if (isUpdate) {
                refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "Blood Test updated successfully...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Blood Test...!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while updating: " + e.getMessage()).show();
        }
    }

}
