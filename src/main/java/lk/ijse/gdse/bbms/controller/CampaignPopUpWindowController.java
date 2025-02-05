package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.gdse.bbms.bo.BOFactory;
import lk.ijse.gdse.bbms.bo.custom.CampaignBO;
import lk.ijse.gdse.bbms.dto.CampaignDTO;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.dto.tm.CampaignTM;
import lk.ijse.gdse.bbms.dto.tm.DonorTM;
import lk.ijse.gdse.bbms.model.CampaignModel;
import lk.ijse.gdse.bbms.model.DonorModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class CampaignPopUpWindowController implements Initializable {
    @FXML
    private JFXRadioButton btnRadioActive;

    @FXML
    private JFXRadioButton btnRadioInactive;

    @FXML
    private JFXRadioButton btnRadioPending;

    @FXML
    private JFXRadioButton btnRadioCompleted;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private JFXButton deleteCampaignBtn;

    @FXML
    private JFXButton addCampaignBtn;

    @FXML
    private Label lblCampaignId;

    @FXML
    private TextField txtCampaignName;

    @FXML
    private TextField txtCampaignAddress;

    @FXML
    private DatePicker datePikerStartDate;

    @FXML
    private DatePicker datePikerEndDate;

    @FXML
    private JFXButton closeBtn;

    @FXML
    private Stage stage;

    CampaignPageController campaignPageController;
    CampaignDTO dto=new CampaignDTO();
    CampaignBO campaignBO= (CampaignBO) BOFactory.getInstance().getBO(BOFactory.BOType.CAMPAIGN);

    public void setCampainPageController(CampaignPageController campaignPageController) {
        this.campaignPageController = campaignPageController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCampaignBtn.setDisable(false);
        deleteCampaignBtn.setDisable(true);
        updateBtn.setDisable(true);
        try {
            lblCampaignId.setText(campaignBO.getNextCampaignId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        populateCampaignStatus();
    }

    private void populateCampaignStatus() {
        ToggleGroup statusGroup = new ToggleGroup();
        btnRadioActive.setToggleGroup(statusGroup);
        btnRadioInactive.setToggleGroup(statusGroup);
        btnRadioPending.setToggleGroup(statusGroup);
        btnRadioCompleted.setToggleGroup(statusGroup);

        String campaignStatus = dto.getStatus();
        if (campaignStatus == null) {
            campaignStatus = "PENDING";
        }

        switch (campaignStatus) {
            case "ACTIVE":
                btnRadioActive.setSelected(true);
                break;
            case "INACTIVE":
                btnRadioInactive.setSelected(true);
                break;
            case "PENDING":
                btnRadioPending.setSelected(true);
                break;
            case "COMPLETED":
                btnRadioCompleted.setSelected(true);
                break;
            default:
                btnRadioPending.setSelected(true);
                break;
        }
    }



    @FXML
    void addCampaignOnAction(ActionEvent event) throws Exception {
        String CampId = lblCampaignId.getText();
        String name = txtCampaignName.getText();
        String address = txtCampaignAddress.getText();

        Date startDate = Date.valueOf(datePikerStartDate.getValue().toString());
        Date endDate =  Date.valueOf(datePikerEndDate.getValue().toString());

        ToggleGroup statusGroup = new ToggleGroup();
        btnRadioActive.setToggleGroup(statusGroup);
        btnRadioInactive.setToggleGroup(statusGroup);
        btnRadioPending.setToggleGroup(statusGroup);
        btnRadioCompleted.setToggleGroup(statusGroup);


        String status = null;
        if (btnRadioActive.isSelected()) {
            status = "ACTIVE";
        } else if (btnRadioInactive.isSelected()) {
            status = "INACTIVE";
        } else if (btnRadioPending.isSelected()) {
            status = "PENDING";
        } else if (btnRadioCompleted.isSelected()) {
            status = "COMPLETED";
        }

        int collectedUnits=0;

        CampaignDTO campaignDTO = new CampaignDTO(CampId,name,address,startDate,endDate,status,collectedUnits);
        boolean isSaved = campaignBO.addCampaign(campaignDTO);

        if (isSaved) {

            try {
                lblCampaignId.setText(campaignBO.getNextCampaignId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            campaignPageController.refreshTable();
            new Alert(Alert.AlertType.INFORMATION, "Campaign saved successfully...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save Campaign...!").show();
        }
        clearFields();
    }

    @FXML
    private void clearFields() throws Exception {
        txtCampaignAddress.clear();
        txtCampaignName.clear();
        datePikerStartDate.setValue(null);
        datePikerEndDate.setValue(null);

        ToggleGroup statusGroup = btnRadioActive.getToggleGroup();
        statusGroup.selectToggle(null);
        lblCampaignId.setText(campaignBO.getNextCampaignId());
    }

    @FXML
    void deleteCampaignOnAction(ActionEvent event) throws Exception {
        String campaignId = lblCampaignId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Campaign?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = campaignBO.deleteCampaign(campaignId);

            if (isDeleted) {
                campaignPageController.refreshTable();
                new Alert(Alert.AlertType.INFORMATION, "Campaign deleted successfully...!").show();
                stage = (Stage) deleteCampaignBtn.getScene().getWindow();
                stage.close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Campaign...!").show();
            }
        }
        clearFields();
    }

    @FXML
    void updateCampaignOnAction(ActionEvent event) throws Exception {
        String id = lblCampaignId.getText();
        String name = txtCampaignName.getText();
        String address = txtCampaignAddress.getText();

        Date startDate = Date.valueOf(datePikerStartDate.getValue().toString());
        Date endDate =  Date.valueOf(datePikerEndDate.getValue().toString());

        ToggleGroup statusGroup = new ToggleGroup();
        btnRadioActive.setToggleGroup(statusGroup);
        btnRadioInactive.setToggleGroup(statusGroup);
        btnRadioPending.setToggleGroup(statusGroup);
        btnRadioCompleted.setToggleGroup(statusGroup);


        String status = null;
        if (btnRadioActive.isSelected()) {
            status = "ACTIVE";
        } else if (btnRadioInactive.isSelected()) {
            status = "INACTIVE";
        } else if (btnRadioPending.isSelected()) {
            status = "PENDING";
        } else if (btnRadioCompleted.isSelected()) {
            status = "COMPLETED";
        }

        CampaignDTO campaignDTO = new CampaignDTO(id,name,address,startDate,endDate,status,0);
        boolean isSaved = campaignBO.updateCampaign(campaignDTO);

        if (isSaved) {

            try {
                lblCampaignId.setText(campaignBO.getNextCampaignId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            campaignPageController.refreshTable();
            new Alert(Alert.AlertType.INFORMATION, "Campaign updated successfully...!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update Campaign...!").show();
        }
        clearFields();
    }
    public void setCampaignData(CampaignTM CampaignTM) {
        addCampaignBtn.setDisable(true);
        updateBtn.setDisable(false);
        deleteCampaignBtn.setDisable(false);

        lblCampaignId.setText(CampaignTM.getBlood_campaign_id());
        txtCampaignName.setText(CampaignTM.getCampaign_name());
        txtCampaignAddress.setText(CampaignTM.getAddress());

        if (CampaignTM.getStartDate() != null) {
            LocalDate startDate = CampaignTM.getStartDate().toLocalDate();
            datePikerStartDate.setValue(startDate);
        }
        if (CampaignTM.getEndDate() != null) {
            LocalDate endDate = CampaignTM.getEndDate().toLocalDate();
            datePikerEndDate.setValue(endDate);
        }
        String status = CampaignTM.getStatus();
        if (status != null) {
            ToggleGroup statusGroup = btnRadioActive.getToggleGroup();
            switch (status) {
                case "ACTIVE":
                    statusGroup.selectToggle(btnRadioActive);
                    break;
                case "INACTIVE":
                    statusGroup.selectToggle(btnRadioInactive);
                    break;
                case "PENDING":
                    statusGroup.selectToggle(btnRadioPending);
                    break;
                case "COMPLETED":
                    statusGroup.selectToggle(btnRadioCompleted);
                    break;
                default:
                    statusGroup.selectToggle(null);
                    break;
            }
        }
    }
    @FXML
    void btnCloseOnAction(ActionEvent event) {
        stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}