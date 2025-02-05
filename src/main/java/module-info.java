module lk.ijse.gdse.bbms {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.desktop;
    requires static lombok;
    requires java.sql;
    requires java.mail;
    requires net.sf.jasperreports.core;
    requires jdk.jfr;

    opens lk.ijse.gdse.bbms.controller to javafx.fxml;
    opens lk.ijse.gdse.bbms.dto.tm to javafx.base;
    exports lk.ijse.gdse.bbms;
}