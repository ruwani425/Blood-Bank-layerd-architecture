package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse.bbms.bo.BOFactory;
import lk.ijse.gdse.bbms.bo.custom.CampaignBO;
import lk.ijse.gdse.bbms.dto.CampaignDTO;
import lk.ijse.gdse.bbms.dto.tm.CampaignTM;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CampaignPageController implements Initializable {

    @FXML
    public JFXButton addCampaignBtn;

    @FXML
    public TextField searchCampaignTxt;

    @FXML
    private TableView<CampaignTM> tblCampaign;

    @FXML
    private TableColumn<CampaignTM,String> colCampId;

    @FXML
    private TableColumn<CampaignTM,String> colCampName;

    @FXML
    private TableColumn<CampaignTM,String> colCampAddress;

    @FXML
    private TableColumn<CampaignTM,Date> colStartDate;

    @FXML
    private TableColumn<CampaignTM,Date> colEndDate;

    @FXML
    private TableColumn<CampaignTM,String> colStatus;

    @FXML
    private TableColumn<CampaignTM,Integer> colCollectedUnit;

    CampaignBO campaignBO= (CampaignBO) BOFactory.getInstance().getBO(BOFactory.BOType.CAMPAIGN);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();

        try {
            refreshTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        tblCampaign.setOnMouseClicked(this::handleRowClick);
    }

    private void setCellValueFactory() {
        colCampId.setCellValueFactory(new PropertyValueFactory<>("Blood_campaign_id"));
        colCampName.setCellValueFactory(new PropertyValueFactory<>("campaign_name"));
        colCampAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colCollectedUnit.setCellValueFactory(new PropertyValueFactory<>("collectedUnits"));
    }
    public void refreshTable() throws Exception {
        ArrayList<CampaignDTO> campaignDTOS = campaignBO.getAllCampaigns();
        ObservableList<CampaignTM> campaignTMS = FXCollections.observableArrayList();

        for (CampaignDTO campaignDTO : campaignDTOS) {
            CampaignTM campaignTM = new CampaignTM(
                    campaignDTO.getBlood_campaign_id(),
                    campaignDTO.getCampaign_name(),
                    campaignDTO.getAddress(),
                    campaignDTO.getStartDate(),
                    campaignDTO.getEndDate(),
                    campaignDTO.getStatus(),
                    campaignDTO.getCollectedUnits()
            );
            campaignTMS.add(campaignTM);
        }
        tblCampaign.setItems(campaignTMS);
    }

    private void handleRowClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            CampaignTM selectedCampaign = tblCampaign.getSelectionModel().getSelectedItem();
            if (selectedCampaign != null) {
                openEditCampaignWindow(selectedCampaign);
            }
        }
    }

    @FXML
    void addNewCampaignOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/campaignPopUp-view.fxml"));
            Parent root = fxmlLoader.load();

            CampaignPopUpWindowController addCampaignPopUpController = fxmlLoader.getController();
            addCampaignPopUpController.setCampainPageController(this);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root));

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openEditCampaignWindow(CampaignTM campaignTM) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/campaignPopUp-view.fxml"));
            Parent root = fxmlLoader.load();

            CampaignPopUpWindowController controller = fxmlLoader.getController();
            controller.setCampainPageController(this);

            controller.setCampaignData(campaignTM);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tblCampaign.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchCampaignData(KeyEvent event) {
        FilteredList<CampaignTM> filteredData = new FilteredList<>(tblCampaign.getItems(), b -> true);

        searchCampaignTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(campaignTM -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                return campaignTM.getBlood_campaign_id().toLowerCase().contains(searchKeyword) ||
                        campaignTM.getCampaign_name().toLowerCase().contains(searchKeyword) ||
                        campaignTM.getAddress().toLowerCase().contains(searchKeyword) ||
                        campaignTM.getStartDate().toString().contains(searchKeyword) ||
                        campaignTM.getEndDate().toString().contains(searchKeyword) ||
                        campaignTM.getStatus().toLowerCase().contains(searchKeyword) ||
                        String.valueOf(campaignTM.getCollectedUnits()).contains(searchKeyword);
            });
        });

        tblCampaign.setItems(filteredData);
    }
}