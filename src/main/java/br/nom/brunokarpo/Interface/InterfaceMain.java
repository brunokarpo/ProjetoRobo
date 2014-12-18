package br.nom.brunokarpo.Interface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InterfaceMain extends Application {

	private static Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource("projeto-robo.fxml"));
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		//stage.getIcons().add(new Image(getClass().getResourceAsStream("icone.png")));
		stage.setTitle("Robô Portal Nacional");
		stage.show();
		InterfaceMain.stage = stage;
	}

	public static Stage getStage() {
		return stage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	/*public void iconButtonProcurar () {
		Image imagemProcurar = new Image(getClass().getResourceAsStream("folder.png"));
		btProcurar.setGraphic(new ImageView(imagemProcurar));
	}*/
}