package br.nom.brunokarpo.services;

import br.nom.brunokarpo.entidade.EChavesAcesso;
import br.nom.brunokarpo.persistence.Persistence;

/** Essa classe implementa os servi�os que ser�o disponibilizados pela aplica��o
 * @author Bruno Nogueira de Oliveira
 * */
public class Services {

	private Persistence persistencia;

	public Services() {
		persistencia = new Persistence();
	}

	public void salvar(String chave_acesso) {
		// Cria um objeto entidade ChaveAcesso
		EChavesAcesso chaveAcesso = new EChavesAcesso(chave_acesso);
		// Manda persistir
		persistencia.salvar( chaveAcesso );
	}

}
