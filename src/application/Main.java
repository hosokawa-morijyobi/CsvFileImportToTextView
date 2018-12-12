package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	public static Main singleton;
	private Stage stage;
	private BorderPane root ;

	@Override
    public void start(Stage Stage) throws Exception{

		singleton = this;
		stage = Stage;

		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        Stage.setScene(scene);
        Stage.show();

    }
	public static Main getInstance() {
		return singleton;
	}

	public  Stage getStage() {
		return stage;
	}

    public static void main(String[] args) {
		launch(args);
	}
}

/*
  try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
*/