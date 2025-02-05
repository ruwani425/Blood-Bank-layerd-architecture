package lk.ijse.gdse.bbms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.gdse.bbms.bo.BOFactory;
import lk.ijse.gdse.bbms.bo.custom.InventoryBO;
import lk.ijse.gdse.bbms.dto.InventoryDTO;
import lk.ijse.gdse.bbms.dto.tm.InventoryTM;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InventoryPageController implements Initializable {

    @FXML
    private TableView<InventoryTM> tblInventory;

    @FXML
    private TableColumn<InventoryTM, String> colInventoryID;

    @FXML
    private TableColumn<InventoryTM, String> colInventoryName;

    @FXML
    private TableColumn<InventoryTM, String> colStatus;

    @FXML
    private TableColumn<InventoryTM, Date> colExpiryDate;

    @FXML
    private TableColumn<InventoryTM, Integer> colQty;

    @FXML
    private TextField txtSearchBar;

    @FXML
    private JFXButton btnAddInventory;

    @FXML
    private JFXButton btnAddSupplier;

    HomePageViewController homePageViewController;

    private final InventoryBO inventoryBO= (InventoryBO) BOFactory.getInstance().getBO(BOFactory.BOType.INVENTORY);

    public void setHomePageViewController(HomePageViewController homePageViewController) {
        this.homePageViewController = homePageViewController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        try {
            refreshTable();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load inventory data", e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tblInventory.setOnMouseClicked(this::handleRowClick);
    }

    private void setCellValueFactory() {
        colInventoryID.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        colInventoryName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    public void refreshTable() throws Exception {
        ArrayList<InventoryDTO> inventoryDTOs = inventoryBO.getAllInventoryItems();
        ObservableList<InventoryTM> inventoryTMs = FXCollections.observableArrayList();

        for (InventoryDTO dto : inventoryDTOs) {
            InventoryTM tm = new InventoryTM(
                    dto.getInventoryId(),
                    dto.getItemName(),
                    dto.getStatus(),
                    dto.getExpiryDate(),
                    dto.getQty()
            );
            inventoryTMs.add(tm);
        }

        tblInventory.setItems(inventoryTMs);
    }

    private void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            InventoryTM selectedItem = tblInventory.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                openEditInventoryWindow(selectedItem);
            }
        }
    }

    private void openEditInventoryWindow(InventoryTM selectedItem) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/inventoryPopUpForm-view.fxml"));
            Parent root = fxmlLoader.load();

            InventoryPopUpFormController controller = fxmlLoader.getController();
            controller.setInventoryData(selectedItem);
            controller.setInventoryPageController(this);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(tblInventory.getScene().getWindow());
            stage.showAndWait();

            refreshTable();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToSupplierPage(ActionEvent event) {
       homePageViewController.navigateToSupplierPage(homePageViewController);
    }

    @FXML
    void popUpNewWindowAddInventory(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/inventoryPopUpForm-view.fxml"));
            Parent root = fxmlLoader.load();
            InventoryPopUpFormController inventoryPopUpFormController = fxmlLoader.getController();
            inventoryPopUpFormController.setInventoryPageController(this);

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

    @FXML
    void searchDataInventory(KeyEvent event) {
        FilteredList<InventoryTM> filteredData = new FilteredList<>(tblInventory.getItems(), b -> true);

        txtSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(inventoryTM -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                String expiryDateString = inventoryTM.getExpiryDate() != null ? inventoryTM.getExpiryDate().toString() : "";
                return inventoryTM.getInventoryId().toLowerCase().contains(searchKeyword) ||
                        inventoryTM.getItemName().toLowerCase().contains(searchKeyword) ||
                        inventoryTM.getStatus().toLowerCase().contains(searchKeyword) ||
                        expiryDateString.contains(searchKeyword) || // Check expiry date as a string
                        String.valueOf(inventoryTM.getQty()).contains(searchKeyword); // Check quantity
            });
        });

        tblInventory.setItems(filteredData);
    }
    public void navigateToInventoryPage() {
        try {
            homePageViewController.homeAnchor.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inventoryPage-view.fxml"));
            AnchorPane supplierPage = loader.load();

            homePageViewController.homeAnchor.getChildren().add(supplierPage);

            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), supplierPage);
            transition.setFromY(-homePageViewController.homeAnchor.getHeight());
            transition.setToY(0);
            transition.play();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Supplier Page!").show();
        }
    }
}
