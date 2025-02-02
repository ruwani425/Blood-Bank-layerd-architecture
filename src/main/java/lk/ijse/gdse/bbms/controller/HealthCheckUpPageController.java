package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.gdse.bbms.bo.BOFactory;
import lk.ijse.gdse.bbms.bo.custom.HealthCheckUpBO;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.dto.HealthCheckupDTO;
import lk.ijse.gdse.bbms.entity.HealthCheckUp;
import lk.ijse.gdse.bbms.model.DonorModel;
import lk.ijse.gdse.bbms.model.HealthCheckUpModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

public class HealthCheckUpPageController implements Initializable {

    @FXML
    private JFXButton showStatusBtn;

    @FXML
    private JFXButton checkStatusBtn;

    @FXML
    private Label healthCheckupIdLbl;

    @FXML
    private Label donorIdLbl;

    @FXML
    private Label lastDonationDateLbl;

    @FXML
    private Label ageLbl;

    @FXML
    private Label weightLbl;

    @FXML
    private Label sugerLevelLbl;

    @FXML
    private Label bloodPresureLbl;

    @FXML
    private Label healthStatusLbl;

    @FXML
    private Label dateOfCheckUpLbl;


    @FXML
    private TextField txtNicDonor;

    @FXML
    private TextField txtWeight;

    @FXML
    private TextField txtBloodPressure;

    @FXML
    private TextField txtSugarLevel;


    @FXML
    private Label lblHealthCheckUpId;

    private HomePageViewController homePageViewController;

    private DonorModel donorModel=new DonorModel();
    private DonorDTO donorDTO;
    private HealthCheckUpModel healthCheckUpModel=new HealthCheckUpModel();
    private HealthCheckupDTO healthCheckupDTO;
    private HealthCheckUpBO healthCheckUpBO= (HealthCheckUpBO) BOFactory.getInstance().getBO(BOFactory.BOType.HEALTHCHECKUP);
    String colorCode = "#FF0000";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showStatusBtn.setDisable(true);
        try {
            lblHealthCheckUpId.setText(healthCheckUpBO.getNextHealthCheckUpId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnCheckDonorHealthCheckUpDetail(ActionEvent event) throws Exception {
        String nicRegex = "^[0-9]{9}[vVxX]|[0-9]{12}$"; // Valid NIC format (9 digits + V or 12 digits)
        String bloodPressureRegex = "^\\d{1,3}/\\d{1,3}$"; // Blood pressure format: systolic/diastolic
        String weightRegex = "^[1-9][0-9]*([.][0-9]{1,2})?$"; // Weight as a positive decimal number
        String sugarLevelRegex = "^[0-9]+(\\.[0-9]{1,2})?$"; // Sugar level as a positive number with optional decimals

        String healthCheckID = lblHealthCheckUpId.getText();
        Date checkUpDate = Date.valueOf(LocalDate.now());
        String donorNic = txtNicDonor.getText();
        String bloodPressure = txtBloodPressure.getText();
        double donorWeight = 0;
        double sugarLevel = 0;

        StringBuilder errorMessage = new StringBuilder();

        boolean validInput = true;

        if (!donorNic.matches(nicRegex)) {
            txtNicDonor.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorMessage.append("Invalid Donor NIC format. Please enter a valid NIC (9 digits + V or 12 digits).\n");
            validInput = false;
        } else {
            txtNicDonor.setStyle("");
        }

        if (!bloodPressure.matches(bloodPressureRegex)) {
            txtBloodPressure.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorMessage.append("Invalid Blood Pressure format. Please use systolic/diastolic format (e.g., 110/68).\n");
            validInput = false;
        } else {
            txtBloodPressure.setStyle("");
        }

        String[] bpParts = bloodPressure.split("/");
        int systolic = 0;
        int diastolic = 0;
        try {
            systolic = Integer.parseInt(bpParts[0].trim());
            diastolic = Integer.parseInt(bpParts[1].trim());
        } catch (NumberFormatException e) {
            validInput = false;
        }

        try {
            donorWeight = Double.parseDouble(txtWeight.getText());
            if (donorWeight <= 0) {
                txtWeight.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                errorMessage.append("Weight must be a positive number.\n");
                validInput = false;
            } else {
                txtWeight.setStyle("");
            }
        } catch (NumberFormatException e) {
            txtWeight.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorMessage.append("Invalid weight format. Please enter a valid weight.\n");
            validInput = false;
        }

        try {
            sugarLevel = Double.parseDouble(txtSugarLevel.getText());
            if (sugarLevel < 0) {
                txtSugarLevel.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                errorMessage.append("Sugar level cannot be negative.\n");
                validInput = false;
            } else {
                txtSugarLevel.setStyle("");
            }
        } catch (NumberFormatException e) {
            txtSugarLevel.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            errorMessage.append("Invalid sugar level format. Please enter a valid sugar level.\n");
            validInput = false;
        }

        if (!validInput) {
            showAlert("Validation Errors", errorMessage.toString());
            return;
        }

        donorDTO = donorModel.getDonorByNic(donorNic);
        if (donorDTO == null) {
            clearLabels();
            sugerLevelLbl.setText("No donor found with the given National ID Number.");
            return;
        }

        String donorID = donorDTO.getDonorId();
        int age = Period.between(donorDTO.getDob().toLocalDate(), LocalDate.now()).getYears();
        String gender = donorDTO.getGender();

        if (age < 18 || age > 65 || donorWeight < 50) {
            healthStatusLbl.setText("Not Eligible");
            healthStatusLbl.setStyle("-fx-text-fill: " + colorCode + ";");
            setHealthCheckUpDetailsForLables(age, checkUpDate, donorDTO.getDonorId(), bloodPressure, healthCheckID, donorDTO.getLastDonationDate(), sugarLevel, donorWeight);
            return;
        }

        boolean isEligible = false;
        if (sugarLevel >= 100 && sugarLevel <= 140) {
            if ((age >= 18 && age <= 39) && gender.equalsIgnoreCase("female") && systolic <= 110 && diastolic <= 68) {
                isEligible = true;
            } else if ((age >= 18 && age <= 39) && gender.equalsIgnoreCase("male") && systolic <= 119 && diastolic <= 70) {
                isEligible = true;
            } else if ((age >= 40 && age <= 50) && gender.equalsIgnoreCase("female") && systolic <= 122 && diastolic <= 74) {
                isEligible = true;
            } else if ((age >= 40 && age <= 50) && gender.equalsIgnoreCase("male") && systolic <= 124 && diastolic <= 77) {
                isEligible = true;
            } else if ((age >= 60 && age <= 65) && gender.equalsIgnoreCase("female") && systolic <= 139 && diastolic <= 68) {
                isEligible = true;
            } else if ((age >= 60 && age <= 65) && gender.equalsIgnoreCase("male") && systolic <= 133 && diastolic <= 69) {
                isEligible = true;
            }
        }

        if (isEligible) {
            showStatusBtn.setDisable(false);
            healthStatusLbl.setText("The Donor is eligible for a blood Donation");
            showStatusBtn.setText("Pass");

            healthCheckupDTO = new HealthCheckupDTO();
            healthCheckupDTO.setHealthStatus(healthStatusLbl.getText());
            healthCheckupDTO.setCheckupDate(checkUpDate);
            healthCheckupDTO.setCheckupId(healthCheckID);
            healthCheckupDTO.setBloodPressure(bloodPressure);
            healthCheckupDTO.setSugarLevel(sugarLevel);
            healthCheckupDTO.setDonorId(donorID);
            healthCheckupDTO.setWeight(donorWeight);

            saveHealthCheckup(healthCheckID, donorID, "ELIGIBLE", checkUpDate, donorWeight, sugarLevel, bloodPressure);
        } else {
            healthStatusLbl.setText("Not Eligible");
            saveHealthCheckup(healthCheckID, donorID, "NOT_ELIGIBLE", checkUpDate, donorWeight, sugarLevel, bloodPressure);
            healthStatusLbl.setStyle("-fx-text-fill: " + colorCode + ";");
            setHealthCheckUpDetailsForLables(age, checkUpDate, donorDTO.getDonorId(), bloodPressure, healthCheckID, donorDTO.getLastDonationDate(), sugarLevel, donorWeight);
        }
        setHealthCheckUpDetailsForLables(age, checkUpDate, donorDTO.getDonorId(), bloodPressure, healthCheckID, donorDTO.getLastDonationDate(), sugarLevel, donorWeight);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    void setHealthCheckUpDetailsForLables(int age, Date checkUpDate, String donorId, String bloodPressure, String healthCheckID, Date lastDonationDate, double sugarLevel, double donorWeight){
        ageLbl.setText("Donor age: " + age + " years old");
        dateOfCheckUpLbl.setText("Checkup date: " +checkUpDate);
        donorIdLbl.setText("Donor ID: " + donorDTO.getDonorId());
        bloodPresureLbl.setText("Donor blood Pressure: " + bloodPressure);
        healthCheckupIdLbl.setText("Donor HealthCheckup ID: " +healthCheckID);
        lastDonationDateLbl.setText("Last Donation Date: " + donorDTO.getLastDonationDate());
        sugerLevelLbl.setText("Donor sugar Level: " +sugarLevel);
        weightLbl.setText("Donor weight: " +donorWeight);
    }

    private void saveHealthCheckup(String healthCheckID, String donorID, String status, Date checkUpDate, double donorWeight, double sugarLevel, String bloodPressure) throws Exception {
        healthCheckupDTO = new HealthCheckupDTO(healthCheckID, donorID, status, checkUpDate, donorWeight, sugarLevel, bloodPressure);
        boolean isSaved;
        try {
            isSaved = healthCheckUpBO.addHealthCheckup(healthCheckupDTO);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error while saving health checkup.").show();
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (isSaved) {
            showStatusBtn.setDisable(false);
            lblHealthCheckUpId.setText(healthCheckUpBO.getNextHealthCheckUpId());
            new Alert(Alert.AlertType.INFORMATION, "Health Checkup added successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save health checkup details").show();
        }
    }

    @FXML
    public void setHomePageViewController(HomePageViewController homePageViewController) {
        this.homePageViewController = homePageViewController;
    }

    private void clearLabels() {
        ageLbl.setText("");
        dateOfCheckUpLbl.setText("");
        donorIdLbl.setText("");
        bloodPresureLbl.setText("");
        healthStatusLbl.setText("");
        healthCheckupIdLbl.setText("");
        lastDonationDateLbl.setText("");
        sugerLevelLbl.setText("");
        weightLbl.setText("");
    }

    @FXML
    void btnNavigateToDonationPage(ActionEvent event) {
        homePageViewController.navigateToDonationsPageByButton(healthCheckupDTO.getCheckupId(),donorDTO.getBloodGroup(),donorDTO.getDonorId());
    }
}