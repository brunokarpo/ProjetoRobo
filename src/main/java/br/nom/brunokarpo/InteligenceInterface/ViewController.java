package br.nom.brunokarpo.InteligenceInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.nom.brunokarpo.services.Services;

public class ViewController {

	private static final Logger LOGGER = LogManager.getLogger(ViewController.class);

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
