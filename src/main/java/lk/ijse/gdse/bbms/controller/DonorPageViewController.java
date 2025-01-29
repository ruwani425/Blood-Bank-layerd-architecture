package lk.ijse.gdse.bbms.controller;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.dto.tm.DonorTM;
import lk.ijse.gdse.bbms.model.DonorModel;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DonorPageViewController implements Initializable {

    public Button addDonorBtn;
    public TextField txtSearchBar;

    @FXML
    private TableView<DonorTM> tblDonors;

    @FXML
    private TableColumn<DonorTM, String> colId;

    @FXML
    private TableColumn<DonorTM, String> colName;

    @FXML
    private TableColumn<DonorTM, String> colDob;

    @FXML
    private TableColumn<DonorTM, String> colGender;

    @FXML
    private TableColumn<DonorTM, String> colBloodGroup;

    @FXML
    private TableColumn<DonorTM, String> colEmail;

    @FXML
    private TableColumn<DonorTM, String> colAddress;

    @FXML
    private TableColumn<DonorTM, String> colNic;

    DonorModel donorModel = new DonorModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        try {
            refreshTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tblDonors.setOnMouseClicked(this::handleRowClick);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("donorId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("donorName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colBloodGroup.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("donorEmail"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("donorAddress"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("donorNic"));
    }

    public void refreshTable() throws SQLException {
        ArrayList<DonorDTO> donorDTOS = donorModel.getAllDonors();
        ObservableList<DonorTM> donorTMS = FXCollections.observableArrayList();

        for (DonorDTO donorDTO : donorDTOS) {
            DonorTM donorTM = new DonorTM(
                    donorDTO.getDonorId(),
                    donorDTO.getDonorName(),
                    donorDTO.getDonorNic(),
                    donorDTO.getDonorAddress(),
                    donorDTO.getDonorEmail(),
                    donorDTO.getBloodGroup(),
                    donorDTO.getGender(),
                    donorDTO.getDob()
            );
            donorTMS.add(donorTM);
        }
        tblDonors.setItems(donorTMS);
    }

    @FXML
    void popUpNewWindowAddDonor(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/addDonorPopUp-view.fxml"));
            Parent root = fxmlLoader.load();
            AddDonorPopUpController addDonorPopUpController = fxmlLoader.getController();
            addDonorPopUpController.setDonorPageViewController(this);

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
            DonorTM selectedDonor = tblDonors.getSelectionModel().getSelectedItem();
            if (selectedDonor != null) {
                openEditDonorWindow(selectedDonor);
            }
        }
    }

    private void openEditDonorWindow(DonorTM donor) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/addDonorPopUp-view.fxml"));
            Parent root = fxmlLoader.load();

            AddDonorPopUpController controller = fxmlLoader.getController();
            controller.setDonorPageViewController(this);
            controller.setDonorData(donor);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tblDonors.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchDataDonors(KeyEvent event) {
        FilteredList<DonorTM> filteredData = new FilteredList<>(tblDonors.getItems(), b -> true);

        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(donorTM -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                String dobString = donorTM.getDob() != null ? donorTM.getDob().toString() : "";
                return donorTM.getDonorId().toLowerCase().contains(searchKeyword) ||
                        donorTM.getDonorName().toLowerCase().contains(searchKeyword) ||
                        donorTM.getDonorNic().toLowerCase().contains(searchKeyword) ||
                        donorTM.getDonorAddress().toLowerCase().contains(searchKeyword) ||
                        donorTM.getDonorEmail().toLowerCase().contains(searchKeyword) ||
                        donorTM.getGender().toLowerCase().contains(searchKeyword) ||
                        dobString.contains(searchKeyword) ||
                        donorTM.getBloodGroup().toLowerCase().contains(searchKeyword);
            });
        });

        tblDonors.setItems(filteredData);
    }
}