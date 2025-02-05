package lk.ijse.gdse.bbms.controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import lk.ijse.gdse.bbms.dto.tm.BloodRequestTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomePageViewController implements Initializable {

    @FXML
    private ImageView homeBtn;

    @FXML
    private ImageView donorsBtn;

    @FXML
    private ImageView hospitalBtn;

    @FXML
    private ImageView bloodStockBtn;

    @FXML
    private ImageView campaignBtn;

    @FXML
    private ImageView healthCheckupBtn;

    @FXML
    private ImageView requestBtn;

    @FXML
    private ImageView bloodTestBtn;

    @FXML
    private ImageView inventoryBtn;

    @FXML
    private ImageView btnEmployee;

    @FXML
    AnchorPane homeAnchor;

    @FXML
    private AnchorPane rootPane;

    @FXML
    void navigateToBloodStockPage(MouseEvent event) {
        navigateTo("/view/bloodStockPage-view.fxml");
    }

    @FXML
    void navigateToBloodTestPage(MouseEvent event) {
        navigateTo("/view/bloodTestPage-view.fxml");
    }

    @FXML
    void navigateToCampaignPage(MouseEvent event) {
        navigateTo("/view/campaignPage-view.fxml");
    }

    @FXML
    void navigateToDonorPage(MouseEvent event) {
        navigateTo("/view/donorPage-view.fxml");
    }

    @FXML
    void navigateToEmployeePage(MouseEvent event) {
        navigateTo("/view/employeePage-view.fxml");
    }

    @FXML
    void navigateToHealthChekupPage(MouseEvent event) {
        try {
            homeAnchor.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/healthCheckUp-view.fxml"));
            AnchorPane load = loader.load();

            HealthCheckUpPageController controller = loader.getController();

            load.setTranslateY(-homeAnchor.getHeight());

            homeAnchor.getChildren().add(load);

            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), load);
            transition.setFromY(-homeAnchor.getHeight());
            transition.setToY(0);
            transition.play();

            controller.setHomePageViewController(this);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        }
    }

    @FXML
    void navigateToHomePage(MouseEvent event) {
        navigateTo("/view/homeStartPage-view.fxml");
    }

    @FXML
    void navigateToHospitalPage(MouseEvent event) {
        navigateTo("/view/hospitalPage-view.fxml");
    }

    //
//    @FXML
//    void navigateToInventoryPage(MouseEvent event) {
//        navigateTo("/view/inventoryPage-view.fxml");
//    }
    @FXML
    void navigateToInventoryPage() {
        try {
            homeAnchor.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/inventoryPage-view.fxml"));
            AnchorPane inventoryPage = loader.load();

            InventoryPageController controller = loader.getController();
            controller.setHomePageViewController(this);

            homeAnchor.getChildren().add(inventoryPage);

            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), inventoryPage);
            transition.setFromY(-homeAnchor.getHeight());
            transition.setToY(0);
            transition.play();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Inventory Page!").show();
        }
    }

    public void navigateToSupplierPage(HomePageViewController homePageViewController) {
        try {
            homeAnchor.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/supplierPage-view.fxml"));
            AnchorPane inventoryPage = loader.load();

            SupplierPageController controller = loader.getController();
            controller.setHomePageViewController(homePageViewController);

            homeAnchor.getChildren().add(inventoryPage);

            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), inventoryPage);
            transition.setFromY(-homeAnchor.getHeight());
            transition.setToY(0);
            transition.play();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Inventory Page!").show();
        }
    }

    @FXML
    void navigateToRequestPage(MouseEvent event) {
        try {
            homeAnchor.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bloodRequest-view.fxml"));
            AnchorPane load = loader.load();

            BloodRequestController controller = loader.getController();

            load.setTranslateY(-homeAnchor.getHeight());

            homeAnchor.getChildren().add(load);

            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), load);
            transition.setFromY(-homeAnchor.getHeight());
            transition.setToY(0);
            transition.play();

            controller.setHomePageViewController(this);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        }
    }

//    public void navigateTo(String fxmlPath) {
//
//        try {
//            homeAnchor.getChildren().clear();
//            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
//            homeAnchor.getChildren().add(load);
//        } catch (IOException e) {
//            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
//        }
//    }

    public void navigateToDonationsPageByButton(String s, String bloodGroup, String donorId) {
        try {
            homeAnchor.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/donationPage-view.fxml"));
            AnchorPane load = loader.load();

            DonationPageController controller = loader.getController();

            load.setTranslateY(-homeAnchor.getHeight());

            homeAnchor.getChildren().add(load);

            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), load);
            transition.setFromY(-homeAnchor.getHeight());
            transition.setToY(0);
            transition.play();

            controller.setDateFromHealthCheckUp(s, bloodGroup, donorId);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        }
    }

    public void navigateTo(String fxmlPath) {
        try {
            homeAnchor.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.setTranslateY(-homeAnchor.getHeight());

            homeAnchor.getChildren().add(load);

            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), load);
            transition.setFromY(-homeAnchor.getHeight());
            transition.setToY(0);
            transition.play();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        rootPane.setPrefWidth(screenWidth);
        rootPane.setPrefHeight(screenHeight);
        navigateTo("/view/homeStartPage-view.fxml");
    }

    @FXML
    void onMouseEnterdImage(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(15);
            glow.setHeight(15);
            glow.setRadius(15);
            icon.setEffect(glow);
        }
    }

    @FXML
    void onMouseExitedImage(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }

    public void navigateWithRequestId(BloodRequestTM requestId) {
        try {
            homeAnchor.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/bloodStockPage-view.fxml"));
            AnchorPane load = loader.load();

            BloodStockPageController controller = loader.getController();

            load.setTranslateY(-homeAnchor.getHeight());

            homeAnchor.getChildren().add(load);

            TranslateTransition transition = new TranslateTransition(Duration.millis(1000), load);
            transition.setFromY(-homeAnchor.getHeight());
            transition.setToY(0);
            transition.play();

            controller.setRequestID(requestId);

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}