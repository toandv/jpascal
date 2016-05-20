package io.github.toandv.wci;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Created by toan on 5/8/16.
 */
@SuppressWarnings("restriction")
public class JavafxHello extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction((event) -> {
            System.out.println("Hello World!");
        });
        GridPane root = new GridPane();
        root.getChildren().add(btn);

        Rectangle r = new Rectangle();
        r.setX(0);
        r.setY(10);
        r.setWidth(200);
        r.setHeight(100);
        r.setArcWidth(20);
        r.setArcHeight(20);

        Rectangle r1 = new Rectangle();
        r.setX(100);
        r.setY(10);
        r.setWidth(200);
        r.setHeight(100);
        r.setArcWidth(20);
        r.setArcHeight(20);

        root.getChildren().add(r);
        root.getChildren().add(r1);
        primaryStage.setScene(new Scene(root, 1080, 800));
        primaryStage.show();
    }
}