
package org.example.demo4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class test {

    @FXML
    private Button menuButton;

    @FXML
    private void handleHamburgerClick() {

        ContextMenu menu = new ContextMenu();

        MenuItem createImage = new MenuItem("Create Image");

        createImage.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(
                        getClass().getResource("dockerFileFrom.fxml")
                );

                Stage stage = (Stage) menuButton.getScene().getWindow();
                stage.setScene(new Scene(root));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        menu.getItems().add(createImage);
        menu.show(menuButton, Side.BOTTOM, 0, 0);
    }
}


