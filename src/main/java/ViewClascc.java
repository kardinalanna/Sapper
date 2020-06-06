import Logik.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.awt.*;
import java.io.InputStream;

public class ViewClascc extends Application {
    private Controller controller;
    private int columns = 9;
    private int rows = 9;
    private int bomb = 10;
    private int sizeOfPicture = 50;
    private Label label;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        controller = new Controller(columns, rows, bomb);
        controller.start();
        paintPicture();
        primaryStage.getIcons().add(getPicture("icon"));
        primaryStage.setTitle("Sapper");
        final GridPane root = new GridPane();
        for (Coord coord : Field.getListOfAllCoords()) {
            ImageView image = new ImageView((Image) controller.getPictureFromImageStorage(coord).picture);
            root.add(image, coord.y, coord.x);
        }
        Scene scene = new Scene(root, columns * sizeOfPicture, rows * sizeOfPicture+ 26);
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Coord coord = new Coord((int) event.getSceneY() / sizeOfPicture, (int) event.getSceneX() / sizeOfPicture);
                if (event.getButton().equals(MouseButton.PRIMARY)) controller.presButton1(coord);
                if (event.getButton().equals(MouseButton.SECONDARY)) controller.presButton3(coord);
                initScene(root);

            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initScene(GridPane pane) {
        for (Coord coord : Field.getListOfAllCoords()) {
            ImageView image = new ImageView((Image) controller.getPictureFromImageStorage(coord).picture);
            pane.add(image, coord.y, coord.x);
        }
       }

    private Image getPicture(String name) {
        InputStream it = getClass().getResourceAsStream(name + ".png");
        Image picture = new Image(it);
        return picture;
    }

    private void paintPicture() {
        for (ImageStorage picture : ImageStorage.values()) {
            picture.picture = getPicture(picture.name()); //кажлый элемент "заполняем" картинкой
        }
    }


}
