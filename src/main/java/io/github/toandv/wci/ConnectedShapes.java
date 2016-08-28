package io.github.toandv.wci;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

/**
 * @author johndunning
 */
public class ConnectedShapes extends Application {

    double orgSceneX, orgSceneY;

    private EventHandler<MouseEvent> mousePressedEventHandler = (t) ->
    {
        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();

        // bring the clicked circle to the front

        Circle c = (Circle) (t.getSource());
        c.toFront();
    };

    private EventHandler<MouseEvent> mouseDraggedEventHandler = (t) ->
    {
        double offsetX = t.getSceneX() - orgSceneX;
        double offsetY = t.getSceneY() - orgSceneY;

        Circle c = (Circle) (t.getSource());

        c.setCenterX(c.getCenterX() + offsetX);
        c.setCenterY(c.getCenterY() + offsetY);

        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
    };

    private Circle createCircle(double x, double y, double r, Color color) {
        Circle circle = new Circle(x, y, r);

        circle.setFill(Color.LIGHTGREY);
        circle.setCursor(Cursor.CROSSHAIR);

        circle.setOnMousePressed(mousePressedEventHandler);
        circle.setOnMouseDragged(mouseDraggedEventHandler);

        return circle;
    }

    private Text createText(String string) {
        Text text = new Text(string);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle(
                "-fx-font-family: \"Times New Roman\";" +
                        "-fx-font-style: italic;" +
                        "-fx-font-size: 48px;"
        );

        return text;
    }

    private Line connect(Circle c1, Circle c2) {
        Line line = new Line();

        line.startXProperty().bind(c1.centerXProperty());
        line.startYProperty().bind(c1.centerYProperty());

        line.endXProperty().bind(c2.centerXProperty());
        line.endYProperty().bind(c2.centerYProperty());

        line.setStrokeWidth(1);
        line.setStrokeLineCap(StrokeLineCap.BUTT);
        line.getStrokeDashArray().setAll(1.0, 4.0);

        return line;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connected Shapes");

        //
        Group root = new Group();
        Scene scene = new Scene(root, 500, 260);

        // circles
        Circle redCircle = createCircle(100, 50, 30, Color.RED);
        Circle blueCircle = createCircle(200, 150, 20, Color.BLUE);
        Circle greenCircle = createCircle(400, 100, 40, Color.GREEN);

        Text   text   = createText("Xyzzy");

        StackPane layout = new StackPane();
        layout.getChildren().addAll(
                redCircle,
                text
        );
        layout.setPadding(new Insets(20));

        Line line1 = connect(redCircle, blueCircle);
        Line line2 = connect(redCircle, greenCircle);
        Line line3 = connect(greenCircle, blueCircle);

        // add the circles
        root.getChildren().add(redCircle);
        root.getChildren().add(blueCircle);
        root.getChildren().add(greenCircle);
        root.getChildren().add(layout);

        // add the lines
        root.getChildren().add(line1);
        root.getChildren().add(line2);
        root.getChildren().add(line3);

        // bring the circles to the front of the lines
        redCircle.toFront();
        blueCircle.toFront();
        greenCircle.toFront();

        // set the scene
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}