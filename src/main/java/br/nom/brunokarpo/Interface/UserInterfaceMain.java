package br.nom.brunokarpo.Interface;

import javax.swing.JFrame;

public class UserInterfaceMain {

	public static void main(String[] args) {

		UserInterface userInterface = new UserInterface(); //Esse cara cria o objeto de interface
		userInterface.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); // Esse cara fala que, se apertar o X é para fechar a aplicação
		userInterface.setSize( 512, 256 ); //Informa o tamanho da janela que vai abrir
		userInterface.setVisible( true ); //Fala para o bagulho aparecer na tela;

	}

}
