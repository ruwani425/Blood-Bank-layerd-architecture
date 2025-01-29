package lk.ijse.gdse.bbms.util;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BorderWidths;

public class Validation {

    public static boolean validateTextField(TextField textField, String regex, String value) {
        boolean isValid = value.matches(regex);

        if (isValid) {
            textField.setBorder(new Border(new BorderStroke(
                    Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        } else {
            textField.setBorder(new Border(new BorderStroke(
                    Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }

        return isValid;
    }
}
