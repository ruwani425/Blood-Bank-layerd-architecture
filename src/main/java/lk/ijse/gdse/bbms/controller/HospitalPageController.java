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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse.bbms.dto.HospitalDTO;
import lk.ijse.gdse.bbms.dto.tm.HospitalTM;
import lk.ijse.gdse.bbms.model.HospitalModel;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HospitalPageController implements Initializable {

    @FXML
    private TableView<HospitalTM> tblHospital;

    @FXML
    private TableColumn<HospitalTM, String> colHospitalID;

    @FXML
    private TableColumn<HospitalTM, String> colHospitalName;

    @FXML
    private TableColumn<HospitalTM, String> colAddress;

    @FXML
    private TableColumn<HospitalTM, String> colContactNum;

    @FXML
    private TableColumn<HospitalTM, String> colEmail;

    @FXML
    private TableColumn<HospitalTM, String> colType;

    @FXML
    private JFXButton addHospitalbtn;

    @FXML
    private TextField txtSearchBar;

    private final HospitalModel hospitalModel = new HospitalModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        try {
            refreshTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tblHospital.setOnMouseClicked(this::handleRowClick);
    }

    private void setCellValueFactory() {
        colHospitalID.setCellValueFactory(new PropertyValueFactory<>("hospitalId"));
        colHospitalName.setCellValueFactory(new PropertyValueFactory<>("hospitalName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("hospitalAddress"));
        colContactNum.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    public void refreshTable() throws SQLException {
        ArrayList<HospitalDTO> hospitalDTOS = hospitalModel.getAllHospitals();
        ObservableList<HospitalTM> hospitalTMS = FXCollections.observableArrayList();

        for (HospitalDTO hospitalDTO : hospitalDTOS) {
            HospitalTM hospitalTM = new HospitalTM(
                    hospitalDTO.getHospitalId(),
                    hospitalDTO.getHospitalName(),
                    hospitalDTO.getHospitalAddress(),
                    hospitalDTO.getContactNumber(),
                    hospitalDTO.getEmail(),
                    hospitalDTO.getType()
            );
            hospitalTMS.add(hospitalTM);
        }
        tblHospital.setItems(hospitalTMS);
    }

    @FXML
    void popUpWindowAddHospital(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/hospitalPopUp-view.fxml"));
            Parent root = fxmlLoader.load();
             HospitalPopUpController hospitalPopUpController = fxmlLoader.getController();
             hospitalPopUpController.setHospitalPageController(this);

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

    private void handleRowClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            HospitalTM selectedHospital = tblHospital.getSelectionModel().getSelectedItem();
            if (selectedHospital != null) {
                openEditHospitalWindow(selectedHospital);
            }
        }
    }

    private void openEditHospitalWindow(HospitalTM hospital) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/hospitalPopUp-view.fxml"));
            Parent root = fxmlLoader.load();

            HospitalPopUpController controller = fxmlLoader.getController();
            controller.setHospitalPageController(this);
            controller.setHospitalData(hospital);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tblHospital.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchDataHospital(javafx.scene.input.KeyEvent keyEvent) {
        FilteredList<HospitalTM> filteredData = new FilteredList<>(tblHospital.getItems(), b -> true);

        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(hospitalTM -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();
                return hospitalTM.getHospitalId().toLowerCase().contains(searchKeyword) ||
                        hospitalTM.getHospitalName().toLowerCase().contains(searchKeyword) ||
                        hospitalTM.getHospitalAddress().toLowerCase().contains(searchKeyword) ||
                        hospitalTM.getContactNumber().toLowerCase().contains(searchKeyword) ||
                        hospitalTM.getEmail().toLowerCase().contains(searchKeyword) ||
                        hospitalTM.getType().toLowerCase().contains(searchKeyword);
            });
        });

        tblHospital.setItems(filteredData);
    }
}
