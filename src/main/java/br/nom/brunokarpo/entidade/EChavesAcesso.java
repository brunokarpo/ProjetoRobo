package br.nom.brunokarpo.entidade;

import br.ufg.pw.abstractEntity.AbstractEntity;

public class EChavesAcesso extends AbstractEntity {

	private String chaveAcesso;

	public EChavesAcesso(String chaveAcesso) {
		this.chaveAcesso = chaveAcesso;
	}

	public String getChaveAcesso() {
		return chaveAcesso;
	}

	public void setChaveAcesso(String chaveAcesso) {
		this.chaveAcesso = chaveAcesso;
	}


}
