package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.bbms.bo.BOFactory;
import lk.ijse.gdse.bbms.bo.custom.BloodStockBO;
import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.BloodStockDTO;
import lk.ijse.gdse.bbms.dto.HospitalDTO;
import lk.ijse.gdse.bbms.dto.tm.BloodIssueTM;
import lk.ijse.gdse.bbms.dto.tm.BloodRequestTM;
import lk.ijse.gdse.bbms.dto.tm.BloodStockTM;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import lk.ijse.gdse.bbms.util.MailUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JRException;


public class BloodStockPageController implements Initializable {

    @FXML
    private TableView<BloodStockTM> tblBloodStock;

    @FXML
    private TableColumn<BloodStockTM,String> colBloodID;

    @FXML
    private TableColumn<BloodStockTM,String> colTestID;

    @FXML
    private TableColumn<BloodStockTM,String> colBloodGroup;

    @FXML
    private TableColumn<BloodStockTM,Integer> colQty;

    @FXML
    private TableColumn<BloodStockTM,Double> colHaemoglobin;

    @FXML
    private TableColumn<BloodStockTM,Float> colPlatelets;

    @FXML
    private TableColumn<BloodStockTM,Double> colRedBloodCells;

    @FXML
    private TableColumn<BloodStockTM,Double> colWhiteBloodCells;

    @FXML
    private TableColumn<BloodStockTM, Date> colExpiryDate;

    @FXML
    private TableColumn<BloodStockTM,String> colStatus;

    @FXML
    private JFXButton btnVerified;

    @FXML
    private JFXButton btnExpired;

    @FXML
    private TableView<BloodIssueTM> tblBloodIssue;

    @FXML
    private TableColumn<BloodIssueTM, String> colIssueBloodId;

    @FXML
    private TableColumn<BloodIssueTM, Date> colIssueExpireDate;

    @FXML
    private TableColumn<BloodIssueTM, String> colIssueBloodGroup;

    @FXML
    private TableColumn<BloodIssueTM, Double> colIssueQty;

    @FXML
    private TableColumn<BloodIssueTM, JFXButton> colAction;

    @FXML
    private Label lblRequestID;

    @FXML
    private JFXButton btnIssue;

    private BloodRequestTM bloodRequestTM;

    @FXML
    private JFXButton btnBloodIssueReport;

    @FXML
    private JFXButton btnSendMail;

    String bloodType;

    String hospitalEmail;

    ObservableList<BloodIssueTM> bloodIssueTMS = FXCollections.observableArrayList();
    ArrayList<BloodIssueTM>issuedBlood=new ArrayList<>();
    BloodStockBO bloodStockBO= (BloodStockBO) BOFactory.getInstance().getBO(BOFactory.BOType.BLOODSTOCK);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getExpiredBlood();
        setCellValueFactory();
        try {
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tblBloodStock.setOnMouseClicked(this::handleRowClick);
    }

    public void setRequestID(BloodRequestTM bloodRequestTM) throws Exception {
        this.bloodRequestTM = bloodRequestTM;
        lblRequestID.setText(bloodRequestTM.getRequestId());
        System.out.println(bloodRequestTM);
        HospitalDTO hospitalById = bloodStockBO.getHospitalById(bloodRequestTM.getHospitalId());
        hospitalEmail=hospitalById.getEmail();
        bloodType=bloodRequestTM.getBloodType();
    }

    private void setCellValueFactory() {
        colBloodID.setCellValueFactory(new PropertyValueFactory<>("bloodID"));
        colTestID.setCellValueFactory(new PropertyValueFactory<>("testID"));
        colBloodGroup.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colHaemoglobin.setCellValueFactory(new PropertyValueFactory<>("haemoglobin"));
        colPlatelets.setCellValueFactory(new PropertyValueFactory<>("platelets"));
        colRedBloodCells.setCellValueFactory(new PropertyValueFactory<>("redBloodCells"));
        colWhiteBloodCells.setCellValueFactory(new PropertyValueFactory<>("whiteBloodCells"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        colAction.setCellValueFactory(new PropertyValueFactory<>("button"));
        colIssueBloodGroup.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        colIssueBloodId.setCellValueFactory(new PropertyValueFactory<>("bloodIssueID"));
        colIssueExpireDate.setCellValueFactory(new PropertyValueFactory<>("expiry"));
        colIssueQty.setCellValueFactory(new PropertyValueFactory<>("bloodQty"));
    }

    private void handleRowClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            BloodStockTM selectedItem = tblBloodStock.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                BloodIssueTM bloodIssueTM=new BloodIssueTM(selectedItem.getBloodID(),selectedItem.getBloodGroup(),selectedItem.getQty(),selectedItem.getExpiryDate(),null);
                bloodIssueTM.setBloodID(selectedItem.getBloodID());
                addIssueItem(bloodIssueTM);
            }
        }
    }

    private void addIssueItem(BloodIssueTM bloodIssueTM) {
        var button = new JFXButton("Delete");
        button.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;"); // Optional styling
        button.setOnAction(event -> {
            bloodIssueTMS.remove(bloodIssueTM);
            tblBloodIssue.getItems().remove(bloodIssueTM);
            System.out.println("Deleted row with Blood Issue ID: " + bloodIssueTM.getBloodIssueID());
        });
        issuedBlood.add(bloodIssueTM);
        System.out.println(issuedBlood.toString());
        bloodIssueTM.setButton(button);

        bloodIssueTMS.add(bloodIssueTM);
        tblBloodIssue.getItems().add(bloodIssueTM);
    }

    public void refreshTable() throws Exception {
        ArrayList<BloodStockDTO> bloodStockDTOS = bloodStockBO.getAllBloodStocks("VERIFIED");
        ObservableList<BloodStockTM> bloodStockTMS = FXCollections.observableArrayList();

        for (BloodStockDTO bloodStockDTO : bloodStockDTOS) {
            BloodStockTM bloodStockTM = new BloodStockTM(
                    bloodStockDTO.getBloodID(),
                    bloodStockDTO.getTestID(),
                    bloodStockDTO.getBloodGroup(),
                    bloodStockDTO.getQty(),
                    bloodStockDTO.getHaemoglobin(),
                    bloodStockDTO.getPlatelets(),
                    bloodStockDTO.getRedBloodCells(),
                    bloodStockDTO.getWhiteBloodCells(),
                    bloodStockDTO.getExpiryDate(),
                    bloodStockDTO.getStatus()
            );
            bloodStockTMS.add(bloodStockTM);
        }

        tblBloodStock.setItems(bloodStockTMS);
    }

    public void getExpiredBlood(){
        try {
            ArrayList<BloodStockDTO> bloodStockDTOS = bloodStockBO.getExpiredBloodStocks();
            ObservableList<BloodStockTM> bloodStockTMS = FXCollections.observableArrayList();

            for (BloodStockDTO bloodStockDTO : bloodStockDTOS) {
                BloodStockTM bloodStockTM = new BloodStockTM(
                        bloodStockDTO.getBloodID(),
                        bloodStockDTO.getTestID(),
                        bloodStockDTO.getBloodGroup(),
                        bloodStockDTO.getQty(),
                        bloodStockDTO.getHaemoglobin(),
                        bloodStockDTO.getPlatelets(),
                        bloodStockDTO.getRedBloodCells(),
                        bloodStockDTO.getWhiteBloodCells(),
                        bloodStockDTO.getExpiryDate(),
                        bloodStockDTO.getStatus()
                );

                bloodStockTMS.add(bloodStockTM);
            }

            tblBloodStock.setItems(bloodStockTMS);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnIsExpiredOnAction(ActionEvent event) {
      getExpiredBlood();
    }


    @FXML
    void btnIsVerifiedOnAction(ActionEvent event) {
        try {
            ArrayList<BloodStockDTO> bloodStockDTOS = bloodStockBO.getAllBloodStocks("VERIFIED");
            ObservableList<BloodStockTM> bloodStockTMS = FXCollections.observableArrayList();

            for (BloodStockDTO bloodStockDTO : bloodStockDTOS) {
                BloodStockTM bloodStockTM = new BloodStockTM(
                        bloodStockDTO.getBloodID(),
                        bloodStockDTO.getTestID(),
                        bloodStockDTO.getBloodGroup(),
                        bloodStockDTO.getQty(),
                        bloodStockDTO.getHaemoglobin(),
                        bloodStockDTO.getPlatelets(),
                        bloodStockDTO.getRedBloodCells(),
                        bloodStockDTO.getWhiteBloodCells(),
                        bloodStockDTO.getExpiryDate(),
                        bloodStockDTO.getStatus()
                );

                if (bloodStockDTO.getStatus().equalsIgnoreCase("VERIFIED")) {
                    bloodStockTMS.add(bloodStockTM);
                }
            }

            tblBloodStock.setItems(bloodStockTMS);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnIssueOnAction(ActionEvent event) throws Exception {
            boolean isAdd=bloodStockBO.addBloodIssue(bloodRequestTM,issuedBlood);
            if (isAdd){
                refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "successfully saved").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to saved blood issue").show();
            }
    }

    @FXML
    public void btnViewIssueBloodReportOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Blood Issue Report");

            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/reports/RecivedBloodDetails.jrxml"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate the report.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database connection error.").show();
        }
    }

    @FXML
    public void btnSendBloodNotFoundEmail(ActionEvent actionEvent) {
        System.out.println(hospitalEmail);
        System.out.println(bloodType);
        new Thread(() -> {
            MailUtil.sendEmail(
                    hospitalEmail,
                    "Urgent Blood Requirement: "+bloodType+"Out of Stock",
                    "We hope this message finds you well.\n" +
                            "\n" +
                            "We regret to inform you that the requested blood type "+bloodType+"is currently unavailable in our stock at this moment. We are working diligently to replenish our supply and will notify you as soon as it becomes available.\n" +
                            "\n" +
                            "If there’s anything else we can assist you with, please don’t hesitate to reach out.\n" +
                            "\n" +
                            "Thank you for understanding."
            );
        }).start();
    }
}
