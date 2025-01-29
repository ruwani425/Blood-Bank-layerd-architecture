package lk.ijse.gdse.bbms.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.dto.tm.DonorTM;
import lk.ijse.gdse.bbms.model.BloodRequestModel;
import lk.ijse.gdse.bbms.model.BloodStockModel;
import javafx.scene.control.TableView;
import lk.ijse.gdse.bbms.model.DonorModel;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeStartViewController implements Initializable {

    boolean run = true;

    Thread thread;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblYear;

    @FXML
    private Label lblMonthDate;

    @FXML
    private Label stockLbl;

    @FXML
    private Label requestLbl;

    @FXML
    private Label lblResived;


    BloodStockModel bloodStockModel = new BloodStockModel();
    BloodRequestModel bloodRequestModel = new BloodRequestModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCurrentTime();
        setYear();
        setMonthAndDate();
//        int totalBloodCount = 0;
//        try {
//            totalBloodCount = bloodStockModel.getTotalBloodIDCount();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        stockLbl.setText(String.valueOf(totalBloodCount));
//
//        int totalReservedCount = 0;
//        try {
//            totalReservedCount = bloodStockModel.getTotalIssuedBloodIDCount();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        lblResived.setText(String.valueOf(totalReservedCount));
//
//        int totalRequestCount = 0;
//        try {
//            totalRequestCount = bloodRequestModel.getTotalRequestBloodCount();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        requestLbl.setText(String.valueOf(totalRequestCount));
        try {
            populateDashboardData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateDashboardData() throws SQLException {
        stockLbl.setText(String.valueOf(bloodStockModel.getTotalBloodIDCount()));
        lblResived.setText(String.valueOf(bloodStockModel.getTotalIssuedBloodIDCount()));
        requestLbl.setText(String.valueOf(bloodRequestModel.getTotalRequestBloodCount()));
    }

    private void setCurrentTime() {
        thread = new Thread(() -> {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss"); // Time-only format
            while (run) {
                try {
                    Thread.sleep(1000);
                } catch (Exception ignored) {
                }

                final String time = timeFormat.format(new Date());
                Platform.runLater(() -> lblTime.setText(time));
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void setYear() {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy"); // Year format
        String currentYear = yearFormat.format(new Date());

        Platform.runLater(() -> lblYear.setText(currentYear));
    }

    private void setMonthAndDate() {
        SimpleDateFormat monthDateFormat = new SimpleDateFormat("MMMM dd"); // Month and date format
        String monthDate = monthDateFormat.format(new Date());

        Platform.runLater(() -> lblMonthDate.setText(monthDate));
    }

}