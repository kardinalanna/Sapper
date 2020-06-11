import Logik.Controller;
import Logik.Coord;
import Logik.ImageStorage;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class ViewClascc extends Application {
    private Controller controller;
    private int sizeOfPicture = 50;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        int bomb = 10;
        int columns = 9;
        int rows = 9;
        controller = new Controller(columns, rows, bomb);
        controller.start();
        paintPicture();
        primaryStage.getIcons().add(getPicture("icon"));
        primaryStage.setTitle("Sapper");
        final GridPane root = new GridPane();
        for (Coord coord : controller.getListOfCoord()) {
            ImageView image = new ImageView(controller.getPictureFromImageStorage(coord).picture);
            root.add(image, coord.y, coord.x);
        }
        root.add(new javafx.scene.control.Label(getState()), 10, 10);
        Scene scene = new Scene(root, columns * sizeOfPicture, rows * sizeOfPicture + 26);
        scene.setOnMouseClicked(event -> {
            Coord coord = new Coord((int) event.getSceneY() / sizeOfPicture, (int) event.getSceneX() / sizeOfPicture);
            if (event.getButton().equals(MouseButton.PRIMARY)) controller.presButton1(coord);
            if (event.getButton().equals(MouseButton.SECONDARY)) controller.presButton3(coord);
            initScene(root);

        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getState() {
        switch (controller.getState()){
            case playing: return "Будь осторожен";
            case bombed: return "Шальная бомба взорвалась. Вы проиграли :(";
            case winner: return "Вы победили. Ура!!!";
            default: return "";
        }
    }

    private void initScene(GridPane pane) {
        for (Coord coord : controller.getListOfCoord()) {
            ImageView image = new ImageView(controller.getPictureFromImageStorage(coord).picture);
            pane.add(image, coord.y, coord.x);
        }
       }

    private Image getPicture(String name) throws IOException {
       try ( InputStream it = getClass().getResourceAsStream(name + ".png")) {
           return new Image(it);
       }
    }

    private void paintPicture() throws IOException {
        for (ImageStorage picture : ImageStorage.values()) {
            picture.picture = getPicture(picture.name()); //кажлый элемент "заполняем" картинкой
        }
    }
}
