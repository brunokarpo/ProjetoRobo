package br.nom.brunokarpo.Interface;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.nom.brunokarpo.InteligenceInterface.ViewController;

@SuppressWarnings("serial")
public class UserInterface extends JFrame {

	private JLabel label1; // Texto para informar o que o usuario deve fazer
	private JTextField textField1; // Campo de texto que ficar� a string com o local do arquivo.
	private JFileChooser fileChooser; // O cara respons�vel pela escolha do arquivo;
	private JButton buttonOpen; // O cara responsavel pela escolha do arquivo;
	private JButton buttonOk; // O botão responsável por iniciar a aplicação;
	private JButton buttonClear;

	/* Construtor que ira criar a interface do usuario */
	public UserInterface() {

		// Esse carinha aqui cria o texto na parte superior da aplica��o
		super( "Inseridor Robo" );
		setLayout( new FlowLayout() ); // Configura o layout do frame

		criarLabel(); //Esse cara cria o texto que informa o que o usuario dever� fazer;
		criarTextField(); //Esse cara cria o Campo de texto onde estar� o endere�o do arquivo
		criarJButtonOpen(); // Esse cara cria o butao para abrir arquivo;
		criarJButtonOk(); // Esse cara cria o butao ok;
		criarJButtonClear(); //Esse cara cria o butao limpar;

	} // fim do construtor


	private void criarLabel() {
		label1 = new JLabel( "Selecione o arquivo com as chaves para importacao:" ); //Esse cara cria o texto na aplica��o
		label1.setHorizontalAlignment( JLabel.LEFT ); //Fala para o bagulho ficar a esquerda
		add( label1 ); // esse cara adiciona o texto no aplicativo do usuario;
	}

	private void criarTextField() {
		textField1 = new JTextField( 40 ); //Esse cara cria o JTextField
		textField1.setEditable( false ); //Esse trecho impede que o usuario edite o JTextField diretamente;
		textField1.setHorizontalAlignment( JTextField.LEFT ); //Fala para o componente ficar � esquerda;
		add( textField1 ); //Aqui a gente adiciona o TextField na Gui

		/* Quando o usuario clicar na caixa de texto, ele deve selecionar um arquivo TXT na estrutura de diret�rios da m�quina
		 * Vamos implementar isso aqui; */

		TextFieldHandler handler = new TextFieldHandler(); //Cria o objeto de evento;
		textField1.addActionListener( handler ); //Adiciona a chamada do evento;
	}

	private void criarJButtonOk() {
		buttonOk = new JButton("Ok"); // Cria o botão com o rótulo Ok;
		add(buttonOk); //Adiciona o botão ok

		JButtonHandlerOk handler = new JButtonHandlerOk(); // Cria o evento de clicar no botão;
		buttonOk.addActionListener( handler ); // Atrela o evento ao clique do botao;
	}

	private void criarJButtonOpen() {
		Icon icon = new ImageIcon( getClass().getResource("folder.png") ); //seleciona o icone da imagem
		buttonOpen = new JButton("Open File", icon);  // Cria o botao com o icone setado
		add(buttonOpen);

		TextFieldHandler handler = new TextFieldHandler(); // Cria o evento de confirmar
		buttonOpen.addActionListener( handler ); //Efetua o mesmo evento de clicar em ok no JTextField
	}

	private void criarJButtonClear() {
		buttonClear = new JButton("Limpar");
		add(buttonClear);

		JButtonHandlerClear handler = new JButtonHandlerClear(); //Cria o evento de limpar
		buttonClear.addActionListener( handler ); // Executa o evento de limpar quando clicar no botao
	}


	/* Classes interna para tratar eventos feitos */
	private class TextFieldHandler implements ActionListener {

		public void actionPerformed( ActionEvent event ) {

			String pathFile = null;
			// Se o cara clicou em textField1
			if( event.getSource() == textField1 || event.getSource() == buttonOpen) {

				fileChooser = new JFileChooser(); //primeiro seta o objeto de escolha do arquivo;
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("TXT Files", "txt"); //Informa quais os tipos de arquivos vamos trabalhar
				fileChooser.setFileFilter(filtro); // coloca o filtro no File Chooser

				int valorRetorno = fileChooser.showOpenDialog( getParent() );

				if( valorRetorno == JFileChooser.APPROVE_OPTION) {
					pathFile = fileChooser.getSelectedFile().getPath();
				}

				textField1.setText(pathFile); //Aqui coloca o caminho da path no textField
			}

		}

	}

	private class JButtonHandlerOk implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			String path = textField1.getText();

			// Quando o usuario clicar no botao, verificamos se o textField1 tem o caminho do arquivo e disparamos as acoes
			if(path != null) {

				ViewController view = new ViewController(); //Cria o Objeto Controlador

				view.lerArquivo( path ); // Passa o arquivo TXT para o controlador iniciar a inserção das chaves de acesso;


			} else {
				JOptionPane.showMessageDialog(null, "Selecione um arquivo TXT com as chaves de acesso!");
			}
		}

	}

	private class JButtonHandlerClear implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			//Verifica se o texto de textField e null. Se for, limpa ele
			if(textField1.getText() != null)
				textField1.setText(null);

		}

	}



} // fim da classe

