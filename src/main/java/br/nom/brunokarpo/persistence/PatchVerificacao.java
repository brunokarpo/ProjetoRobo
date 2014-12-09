package br.nom.brunokarpo.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.oobj.util.Arquivo;
import br.ufg.pw.utilitarios.JdbcUtil;

public class PatchVerificacao {

	private Connection conn;
	private PreparedStatement pstmt;

	private static boolean RECURSIVO = false;

	private static final String PROPERTIESFILE = "/bd-config.properties"; // arquivo de configuracao
	private static final Logger LOGGER = LogManager.getLogger(PatchVerificacao.class);

	public boolean verificarExistenciaColuna() {

		String url = Arquivo.getURLRelativaClasspath(PROPERTIESFILE).toString().replace("file:/", "");

		conn = JdbcUtil.createConnection( url );

		// valida se ja existe a tabela para insercao de colunas
		try {
			LOGGER.info("Verificando a existencia da tabela chaves_acesso");
			pstmt = conn.prepareStatement("select * from chaves_acesso limit 5");
			pstmt.execute();
			LOGGER.info("Tabela existe");
		} catch(SQLException e) {
			LOGGER.info("Tabela nao existe. Vou providenciar a criacao");
			aplicarPatch();
			if(!RECURSIVO) { //verifica se essa e a chamada recursiva
				RECURSIVO = true; // informa que a proxima chamada sera recursiva
				if(!verificarExistenciaColuna())
					return false;
			}
		} finally {
			JdbcUtil.close(conn, pstmt);
		}
		return true;
	}

	/* Cria a tabela nova se ela nao existir */
	private void aplicarPatch() {
		try {
			LOGGER.info("Criando tabela");
			pstmt = conn.prepareStatement("create table chaves_acesso ( chave_acesso varchar(44) primary key)");
			pstmt.execute();
			LOGGER.info("Tabela criada");
		} catch(Exception e) {
			LOGGER.error("Tabela nao criada" + e);
		}
	}
}
