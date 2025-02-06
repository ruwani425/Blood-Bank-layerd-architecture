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
import lk.ijse.gdse.bbms.bo.custom.EmployeeBO;
import lk.ijse.gdse.bbms.dto.EmployeeDTO;
import lk.ijse.gdse.bbms.dto.tm.EmployeeTM;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeePageController implements Initializable {

    @FXML
    private TextField txtSearchBar;

    @FXML
    private JFXButton addEmployeeBtn;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TableColumn<EmployeeTM, String> colEmployeeID;

    @FXML
    private TableColumn<EmployeeTM, String> colEmployeeName;

    @FXML
    private TableColumn<EmployeeTM, String> colNic;

    @FXML
    private TableColumn<EmployeeTM, String> colAddress;

    @FXML
    private TableColumn<EmployeeTM, String> colEmail;

    @FXML
    private TableColumn<EmployeeTM, String> colRole;

    @FXML
    private TableColumn<EmployeeTM, String> colStatus;

    private EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        try {
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tblEmployee.setOnMouseClicked(event -> {
            try {
                handleRowClick(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void popUpNewWindowAddEmployee(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employeePopUp-view.fxml"));
            Parent root = fxmlLoader.load();

            EmployeePopUpViewController employeePopUpViewController = fxmlLoader.getController();
            employeePopUpViewController.setEmployeePageViewController(this);

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UNDECORATED); // Remove the black toolbar
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void refreshTable() throws Exception {
        ArrayList<EmployeeDTO> employeeDTOS = employeeBO.getAllEmployees(); // Assuming you have a method like this
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDTO employeeDTO : employeeDTOS) {
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDTO.getEmployeeID(),
                    employeeDTO.getName(),
                    employeeDTO.getNic(),
                    employeeDTO.getAddress(),
                    employeeDTO.getEmail(),
                    employeeDTO.getRole(),
                    employeeDTO.getStatus()
            );
            employeeTMS.add(employeeTM);
        }
        tblEmployee.setItems(employeeTMS);
    }

    private void handleRowClick(MouseEvent event) throws IOException {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            EmployeeTM selectedEmployee = tblEmployee.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                openEditEmployeeWindow(selectedEmployee);
            }
        }
    }

    private void openEditEmployeeWindow(EmployeeTM employee) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employeePopUp-view.fxml"));
            Parent root = fxmlLoader.load();

            EmployeePopUpViewController controller = fxmlLoader.getController();
            controller.setEmployeePageViewController(this);
            controller.setEmployeeData(employee);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tblEmployee.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchDataEmployees(KeyEvent event) {
        FilteredList<EmployeeTM> filteredData = new FilteredList<>(tblEmployee.getItems(), b -> true);

        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employeeTM -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                return employeeTM.getEmployeeID().toLowerCase().contains(searchKeyword) ||
                        employeeTM.getName().toLowerCase().contains(searchKeyword) ||
                        employeeTM.getNic().toLowerCase().contains(searchKeyword) ||
                        employeeTM.getAddress().toLowerCase().contains(searchKeyword) ||
                        employeeTM.getEmail().toLowerCase().contains(searchKeyword) ||
                        employeeTM.getRole().toLowerCase().contains(searchKeyword) ||
                        employeeTM.getStatus().toLowerCase().contains(searchKeyword);
            });
        });

        tblEmployee.setItems(filteredData);
    }
}
