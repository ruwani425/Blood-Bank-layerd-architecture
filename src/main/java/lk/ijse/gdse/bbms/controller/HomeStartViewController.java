package lk.ijse.gdse.bbms.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse.bbms.bo.BOFactory;
import lk.ijse.gdse.bbms.bo.custom.BloodRequestBO;
import lk.ijse.gdse.bbms.bo.custom.BloodStockBO;
import lk.ijse.gdse.bbms.bo.custom.HomeBO;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeStartViewController implements Initializable {

    private boolean run = true;

    private Thread thread;
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

    private final HomeBO homeBO = (HomeBO) BOFactory.getInstance().getBO(BOFactory.BOType.HOME);
    private final BloodRequestBO bloodRequestBO = (BloodRequestBO) BOFactory.getInstance().getBO(BOFactory.BOType.BLOODREQUEST);
    private final BloodStockBO bloodStockBO = (BloodStockBO) BOFactory.getInstance().getBO(BOFactory.BOType.BLOODSTOCK);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCurrentTime();
        setYear();
        setMonthAndDate();

        try {
            populateDashboardData();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateDashboardData() throws Exception {
        stockLbl.setText(String.valueOf(homeBO.getTotalBloodIDCount()));
        lblResived.setText(String.valueOf(homeBO.getTotalIssuedBloodIDCount()));
        requestLbl.setText(String.valueOf(homeBO.getTotalRequestBloodCount()));
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