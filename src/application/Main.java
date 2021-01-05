package application;
import javafx.application.Application;
import javafx.stage.Stage;
import presentation.MainScene;

public class Main extends Application {
	private MainScene mainScene;        // La presentation / la vue
	
	public void start(Stage stage) throws Exception {
		stage.setTitle("ShingShang");
		this.mainScene = new MainScene();
		stage.setScene(this.mainScene);
		stage.setMinHeight(740);
		stage.setMinWidth(1000);
		stage.setHeight(740);
		stage.setWidth(1000);
		stage.setResizable(false);
		stage.show();
		
	}
	public static void main(String[] args) {
		launch();
	}
}