package br.nom.brunokarpo.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.ufg.pw.utilitarios.JdbcUtil;

public class PatchVerificacao {

	private Connection conn;
	private PreparedStatement pstmt;

	private static final String propertiesFile = "./src/main/resources/configs/bd-config.properties"; // arquivo de configuração

	public boolean verificarExistenciaColuna() {
		conn = JdbcUtil.createConnection(propertiesFile);

		// valida se ja existe a tabela para insercao de colunas
		try {
			pstmt = conn.prepareStatement("select * from chaves_acesso limit 5");
			pstmt.execute();
		} catch(SQLException e) {
			aplicarPatch();
			return false;
		} finally {
			JdbcUtil.close(conn, pstmt);
		}
		return true;
	}

	/* Cria a tabela nova se ela nao existir */
	private void aplicarPatch() {
		try {
			pstmt = conn.prepareStatement("create table chaves_acesso ( chave_acesso varchar(44) primary key)");
			pstmt.execute();
		} catch(Exception e) {
			System.out.println("Deu merda");
			System.out.println(e);
		}
	}
}
