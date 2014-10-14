package br.nom.brunokarpo.InteligenceInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import br.nom.brunokarpo.services.Services;

public class ViewController {

	private Services service;

	private BufferedReader lerArq;

	public ViewController() {
		service = new Services();
	}

	public void lerArquivo(String enderecoArquivo) {
		try {
			FileReader arq = new FileReader(enderecoArquivo);
			lerArq = new BufferedReader(arq);
			System.out.println("Arquivo aberto:\n" + enderecoArquivo);

			String linha = lerArq.readLine(); //Le a primeira linha do arquivos
			
			while(linha != null) {
				// Valida se a chave tem 44 caracteres e se algum deles é um caractere espaço
				if ( (linha.length() == 44) 
						&& (linha.indexOf(" ") < 0))
					service.salvar(linha);

				linha = lerArq.readLine();
			}

			System.out.println("Fechando o arquivo");
			lerArq.close();

		} catch(FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Arquivo n�o encontrado");

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Linha n�o encontrada");
		}

	}

}
