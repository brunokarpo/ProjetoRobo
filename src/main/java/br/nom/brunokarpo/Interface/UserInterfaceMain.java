package br.nom.brunokarpo.Interface;

import javax.swing.JFrame;

import br.nom.brunokarpo.persistence.PatchVerificacao;

public class UserInterfaceMain {

	public static void main(String[] args) {
		aplicarPatch();
		iniciarInterface();
	}

	private static void iniciarInterface() {
		UserInterface userInterface = new UserInterface(); //Esse cara cria o objeto de interface
		userInterface.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); // Esse cara fala que, se apertar o X é para fechar a aplicação
		userInterface.setSize( 512, 256 ); //Informa o tamanho da janela que vai abrir
		userInterface.setVisible( true ); //Fala para o bagulho aparecer na tela;
	}

	private static void aplicarPatch() {
		PatchVerificacao aplicacaoPatch = new PatchVerificacao();
		if(!aplicacaoPatch.verificarExistenciaColuna())
			throw new RuntimeException("Não existe a coluna para trabalharmos");
	}

}
