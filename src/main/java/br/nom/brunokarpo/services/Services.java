package br.nom.brunokarpo.services;

import br.nom.brunokarpo.persistence.JDBCPersistence;

/** Essa classe implementa os serviços que serão disponibilizados pela aplicação
 * @author Bruno Nogueira de Oliveira
 * */
public class Services {

	private JDBCPersistence persistencia;

	public Services() {
		persistencia = new JDBCPersistence();
	}

	public void salvar(String chave_acesso) {
		persistencia.salvar( chave_acesso );
	}

}
