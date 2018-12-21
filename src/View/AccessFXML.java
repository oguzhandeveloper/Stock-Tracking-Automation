/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.*;
import Controller.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author OGUZHAN
 */
public class AccessFXML {

    public static Personnel personnelCurrent;
    public static AnchorPane ap;

    public void show(String url, String title, AnchorPane ap) {

        if (ap == null) {
            ap = this.ap;
        }
        try {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource(url));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ap.getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(home_page_scene);
            app_stage.setTitle(title);
            app_stage.show();
        } catch (Exception e) {
            System.out.println("This Screen Can't Load!\n" + e);
        }
    }

    public void _modal(String title, String description, String buttonString, AnchorPane panel) {
        if (description.length() >= 100) {
            String subs = "";
            for (int i = 0; i < description.length(); i += 100) {
                subs += description.substring(i, i + 100 > description.length() ? description.length() : i + 100) + "\n";
            }
            description = subs;
        }
        if (panel == null) {
            panel = this.ap;
        }
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(title));
        content.setBody(new Text(description));
        content.setPrefSize(300, 150);
        StackPane stackPane = new StackPane();
        stackPane.autosize();
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        dialog.setBackground(Background.EMPTY);
        JFXButton button = new JFXButton(buttonString);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });

        button.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        button.setPrefHeight(32);
        content.setActions(button);
        panel.getChildren().add(stackPane);
        AnchorPane.setTopAnchor(stackPane, panel.getHeight() / 2 - 75 + 0.0);
        AnchorPane.setLeftAnchor(stackPane, panel.getWidth() / 2 - 150 + 0.0);
        dialog.show();
    }

}
