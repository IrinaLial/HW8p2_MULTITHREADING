import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Task3 extends Application {
    private ArrayList<String> imageList = loadPicturesUrls();
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        int numProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService threadPool = Executors.newFixedThreadPool(numProcessors);

        Pane root = new Pane();

        primaryStage.setScene(new Scene(root));
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);

        Button button = new Button("Update");
        button.setTranslateX(270);
        button.setTranslateY(10);
        root.getChildren().add(button);
        primaryStage.show();

        button.setOnMouseClicked(event -> {
            clearField(button, root);
            imageList = getRandomPictures(imageList);
            new Thread(() -> Platform.runLater(() -> {
                try {
                    showPicture(root, imageList);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            })).start();
        });
    }

    public void stop() throws Exception {
        super.stop();
        System.exit(0);

    }
    public void showPicture(Pane root, ArrayList<String> imageList) throws IOException {
        double incX = 50;
        double incY = 50;
        int imageCount = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j< 5; j++) {
                URL url = new URL(imageList.get(imageCount));
                Image image = new Image(url.openStream());
                ImageView imageView = new ImageView(image);
                imageView.setX(incX);
                imageView.setY(incY);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                root.getChildren().add(imageView);
                incX = incX + 100;
                imageCount++;
            }
            incY = incY + 100.0;
            incX = 50.0;
        }
    }
    public ArrayList<String> getRandomPictures(ArrayList<String> imageList){
        Random random = new Random();
        ArrayList<String> randomImageList = new ArrayList<>(25);
        for (int i=0; i<25; i++) {
            int iElement = random.nextInt(imageList.size());
            randomImageList.add(imageList.get(iElement));
            imageList.remove(iElement);
        }
        return randomImageList;
    }
    public ArrayList<String> loadPicturesUrls(){
        ArrayList<String> imageList = new ArrayList<>();
        String thisLine;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("resources/images.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while ((thisLine = br.readLine()) != null) {
                imageList.add(thisLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageList;
    }
    public void clearField(Button button, Pane root){
        root.getChildren().removeAll(button);
        root.getChildren().add(button);
    }
}
