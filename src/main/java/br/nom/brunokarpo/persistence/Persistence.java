package br.nom.brunokarpo.persistence;

import br.nom.brunokarpo.entidade.EChavesAcesso;
import br.ufg.pw.main.Utilitario;

public class Persistence {

	private Utilitario utilitario = Utilitario.getUtilitario("ProjetoRoboInterface");

	public void salvar(EChavesAcesso chaveAcesso) {
		utilitario.Persistir(chaveAcesso);
	}

}