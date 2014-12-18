package br.nom.brunokarpo.Interface;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.swing.JOptionPane;

import br.nom.brunokarpo.InteligenceInterface.ViewController;

public class InterfaceController implements Initializable {
	@FXML
	private TextField txEmailDestinatario, txChavesAcesso;
	@FXML
	private Button btProcurar, btOk, btLimpar;
	File file;


	public void initialize(URL url, ResourceBundle bundle) {
		iconButtonProcurar();
		btProcurar.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				abrirArquivo();
			}
		});
		btOk.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				ok();
			}
		});
		btLimpar.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				limpar();
			}
		});
	}

	private void iconButtonProcurar() {
		Image imagemProcurar = new Image(getClass().getResourceAsStream("folder.png"));
		btProcurar.setGraphic(new ImageView(imagemProcurar));
	}

	private void abrirArquivo() {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);

		file = fileChooser.showOpenDialog(null);

		txChavesAcesso.setText(file.getPath());
	}

	private void ok() {
		if(txChavesAcesso.getText() != null && txEmailDestinatario.getText() != null) {
			ViewController view = new ViewController();
			view.lerArquivo(txChavesAcesso.getText());
		} else if (txChavesAcesso.getText() == null) {
			JOptionPane.showMessageDialog(null, "Selecione um arquivo TXT com as chaves de acesso!");
		} else {
			JOptionPane.showMessageDialog(null, "Informe o email do destinatário!");
		}
	}

	private void limpar() {
		if(txChavesAcesso.getText() != null) {
			txChavesAcesso.setText(null);
		}
	}
}