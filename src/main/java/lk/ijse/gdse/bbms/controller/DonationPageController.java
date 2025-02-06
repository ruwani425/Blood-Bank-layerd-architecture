package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import lk.ijse.gdse.bbms.bo.custom.DonationBO;
import lk.ijse.gdse.bbms.dto.CampaignDTO;
import lk.ijse.gdse.bbms.dto.DonationDTO;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.dto.tm.DonationTM;
import lk.ijse.gdse.bbms.util.MailUtil;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DonationPageController implements Initializable {

    @FXML
    private ComboBox<String> cmbSelectCampaign;

    @FXML
    private TextField txtQty;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private TableView<DonationTM> tblDonation;

    @FXML
    private TableColumn<DonationTM, String> colDonationId;

    @FXML
    private TableColumn<DonationTM, String> colBloodCampaignId;

    @FXML
    private TableColumn<DonationTM, String> colHealthCheckUpId;

    @FXML
    private TableColumn<DonationTM, String> colBloodGroup;

    @FXML
    private TableColumn<DonationTM, Integer> colQty;

    @FXML
    private TableColumn<DonationTM, Date> colDateOfDonation;

    @FXML
    private Label lblDonationId;

    @FXML
    private Label lblBloodGroup;


    @FXML
    private Label lblDonorName;

    @FXML
    private Label lblDonorAddress;

    @FXML
    private Label lblDonorNic;

    @FXML
    private Label lblCampaignName;

    @FXML
    private Label lblCampaignID;

    @FXML
    private Label lblCollectedUnits;

    private String donorEmail;

    private DonationBO donationBO = (DonationBO) BOFactory.getInstance().getBO(BOFactory.BOType.DONATION);

    private String checkupId;
    private String bloodGroup;
    private String donorId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setCellValueFactory();
            refreshTable();
            getDonorById();
            lblDonationId.setText(donationBO.getNextDonationId());
            ArrayList<String> idList = donationBO.findCampaignIds();
            cmbSelectCampaign.getItems().addAll(idList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getDonorById() {

    }

    private void setCellValueFactory() {
        colDonationId.setCellValueFactory(new PropertyValueFactory<>("Donation_id"));
        colBloodCampaignId.setCellValueFactory(new PropertyValueFactory<>("Blood_campaign_id"));
        colHealthCheckUpId.setCellValueFactory(new PropertyValueFactory<>("Health_checkup_id"));
        colBloodGroup.setCellValueFactory(new PropertyValueFactory<>("Blood_group"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colDateOfDonation.setCellValueFactory(new PropertyValueFactory<>("dateOfDonation"));
    }

    public void refreshTable() throws Exception {
        ArrayList<DonationDTO> donationDTOS = donationBO.getAllDonations();
        ObservableList<DonationTM> donationTMS = FXCollections.observableArrayList();

        for (DonationDTO donationDTO : donationDTOS) {
            DonationTM donationTM = new DonationTM(
                    donationDTO.getDonationId(),
                    donationDTO.getCampaignId(),
                    donationDTO.getHelthCheckupId(),
                    donationDTO.getBloodGroup(),
                    donationDTO.getQty(),
                    donationDTO.getDateOfDonation()
            );
            donationTMS.add(donationTM);
        }
        tblDonation.setItems(donationTMS);
    }

    @FXML
    void btnAddDonationOnAction(ActionEvent event) throws Exception {
        String campaignId = cmbSelectCampaign.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        Date donationDate = Date.valueOf(LocalDate.now());

        System.out.println(campaignId);
        System.out.println(checkupId);

        boolean isSaved = donationBO.addDonation(new DonationDTO(
                lblDonationId.getText(),
                campaignId,
                checkupId,
                bloodGroup,
                qty,
                donationDate
        ), donorId);

        if (isSaved) {
            lblDonationId.setText(donationBO.getNextDonationId());
            refreshTable();

            new Thread(() -> {
                MailUtil.sendEmail(
                        donorEmail,
                        " Heartfelt Thanks for Your Generous Blood Donation!",
                        "Dear Donor,\n" +
                                "\n" +
                                "Thank you for your generous blood donation. Your kindness has the power to save lives and bring hope to those in need.\n" +
                                "\n" +
                                "We deeply appreciate your support and look forward to seeing you again!"
                );
            }).start();

            new Alert(Alert.AlertType.INFORMATION, "Donation saved successfully!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save donation.").show();
        }
    }


    public void setDateFromHealthCheckUp(String s, String bloodGroup, String id) {
        System.out.printf("HealthCheckupDTO initialized in DonationPageController: %s, %s%n", s, bloodGroup);
        this.checkupId = s;
        this.bloodGroup = bloodGroup;
        this.donorId = id;
        System.out.println("HealthCheckupDTO initialized in DonationPageController: ");
        try {
            DonorDTO donorById = donationBO.getDonorById(id);
            lblBloodGroup.setText(donorById.getBloodGroup());
            lblDonorAddress.setText(donorById.getDonorAddress());
            lblDonorNic.setText(donorById.getDonorNic());
            lblDonorName.setText(donorById.getDonorName());
            donorEmail = donorById.getDonorEmail();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbSelectOnAction(ActionEvent event) throws Exception {
        CampaignDTO campaignDTO = donationBO.getCampaignById(cmbSelectCampaign.getValue());
        lblCampaignID.setText(campaignDTO.getBlood_campaign_id());
        lblCampaignName.setText(campaignDTO.getCampaign_name());
        lblCollectedUnits.setText(String.valueOf(campaignDTO.getCollectedUnits()));
    }
}