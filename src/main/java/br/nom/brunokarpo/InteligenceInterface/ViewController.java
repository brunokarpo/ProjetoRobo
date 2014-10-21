package br.nom.brunokarpo.InteligenceInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.nom.brunokarpo.services.Services;

public class ViewController {

	private static final Logger LOGGER = Logger.getLogger(ViewController.class);
	
	private Services service;

	private BufferedReader lerArq;

	public ViewController() {
		service = new Services();
	}

	public void lerArquivo(String enderecoArquivo) {
		try {
			FileReader arq = new FileReader(enderecoArquivo);
			lerArq = new BufferedReader(arq);
			LOGGER.info("Abrindo o arquivo " + enderecoArquivo);

			String linha = lerArq.readLine(); //Le a primeira linha do arquivos
			
			while(linha != null) {

				linha.replace(" ", ""); // pega a chave e tira todos os espa�os que tem nela
				if (linha.length() == 44)
					service.salvar(linha);

				linha = lerArq.readLine();
			}

			LOGGER.info("Fechando o arquivo");
			lerArq.close();

		} catch(FileNotFoundException e) {
			LOGGER.log(Level.ALL, "Arquivo nao encontrado", e);

		} catch (IOException e) {
			LOGGER.log(Level.ALL, "Linha nao encontrada", e);
			
		}

	}

}
