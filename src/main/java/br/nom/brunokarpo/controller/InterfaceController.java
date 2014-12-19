package br.nom.brunokarpo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.nom.brunokarpo.services.Services;

public class InterfaceController implements Initializable {
    @FXML
    private TextField txEmailDestinatario, txChavesAcesso;
    @FXML
    private Button btProcurar, btOk, btLimpar;

    private static final Logger LOGGER = LogManager.getLogger(InterfaceController.class);

    private Services service;
    private BufferedReader lerArq;

    public InterfaceController() {
        service = new Services();
    }

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
        if (txChavesAcesso.getText() == null) {
             JOptionPane.showMessageDialog(null, "Selecione um arquivo TXT com as chaves de acesso!");
        } else if (txEmailDestinatario.getText() == null) {
            JOptionPane.showMessageDialog(null, "Informe o email do destinatário!");
        } else {
            lerArquivo(txChavesAcesso.getText());
        }
    }

    private void limpar() {
        if(txChavesAcesso.getText() != null) {
            txChavesAcesso.setText(null);
        }
    }

    public void lerArquivo(String enderecoArquivo) {
        try {
            FileReader arq = new FileReader(enderecoArquivo);
            lerArq = new BufferedReader(arq);
            LOGGER.info("Abrindo o arquivo " + enderecoArquivo);

            String linha = lerArq.readLine(); //Le a primeira linha do arquivos

            while(linha != null) {

                linha.replace(" ", ""); // pega a chave e tira todos os espacos que tem nela
                if (linha.length() == 44 && linha.substring(20, 22).equals("55") ) { //verifica se a chave esta completa e se pertence a uma NFe
                    LOGGER.info("Salvando chave de acesso " + linha);
                    service.salvar(linha);
                }

                linha = lerArq.readLine();
            }

            LOGGER.info("Fechando o arquivo");
            lerArq.close();

        } catch(FileNotFoundException e) {
            LOGGER.log(Level.ERROR, "Arquivo nao encontrado", e);
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Linha nao encontrada", e);
        }
    }
}
