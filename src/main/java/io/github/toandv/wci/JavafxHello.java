package io.github.toandv.wci;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by toan on 5/8/16.
 */
@SuppressWarnings("restriction")
public class JavafxHello extends Application {

    CheckBox chksport1, chksport2, chksport3;
    Label lbltotal, lbllist;

    //2 VBoxes for the labels and checkboxes
    VBox vbchecks, vblabels;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        //vbox for checkboxes
        vbchecks = new VBox();
        vbchecks.setSpacing(10);
        vbchecks.setPadding(new Insets(20));

        //vbox for labels
        vblabels = new VBox();
        vblabels.setSpacing(10);
        vblabels.setPadding(new Insets(20));


        //make 3 checkboxes
        chksport1 = new CheckBox("Hockey");
        chksport2 = new CheckBox("Baseball");
        chksport3 = new CheckBox("Football");

        //make 2 labels
        lbltotal = new Label("Sports chosen: 0");
        lbllist = new Label("None");

        //add all things to vboxes
        vbchecks.getChildren().addAll(chksport1, chksport2, chksport3);
        vblabels.getChildren().addAll(lbltotal, lbllist);

        //attach click-method to all 3 checkboxes
        chksport1.setOnAction(e -> handleButtonAction(e));
        chksport2.setOnAction(e -> handleButtonAction(e));
        chksport3.setOnAction(e -> handleButtonAction(e));

        //create main container and add 2 vboxes to it
        FlowPane root = new FlowPane();
        root.setHgap(20);
        root.getChildren().addAll(vbchecks, vblabels);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void handleButtonAction(ActionEvent e) {
        int count = 0;
        String choices = "";
        if (chksport1.isSelected()) {
            count++;
            choices += chksport1.getText() + "\n";
        }
        if (chksport2.isSelected()) {
            count++;
            choices += chksport2.getText() + "\n";
        }
        if (chksport3.isSelected()) {
            count++;
            choices += chksport3.getText() + "\n";
        }
        lbltotal.setText("Sports chosen: " + count);
        lbllist.setText(choices);
    }
}