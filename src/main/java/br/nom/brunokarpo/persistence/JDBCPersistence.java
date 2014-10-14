package br.nom.brunokarpo.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;

import br.ufg.pw.utilitarios.JdbcUtil;



public class JDBCPersistence {

	private Connection conn; // Conex�o com o Banco de Dados
	private PreparedStatement pstmt; // respons�vel pelas strings de consulta

	private static final String propertiesFile = "./src/main/resources/configs/bd-config.properties"; // arquivo de configura��o

	public void salvar(String chave_acesso) {
		// implementa a salvura da chave de acesso no banco de dados

		try {
			conn = JdbcUtil.createConnection( propertiesFile ); // abre uma conex�o com o Banco
												// de Dados

			conn.setAutoCommit(false); // Cria uma transa��o no Banco de Dados

			pstmt = conn
					.prepareStatement("insert into chaves_acesso values (?)");

			pstmt.setString(1, chave_acesso);

			pstmt.executeUpdate();

			conn.commit();

			System.out.println("Chave de acesso: " + chave_acesso);

		} catch (Exception e) {
			try {
				conn.rollback();
				e.printStackTrace();
			} catch (Exception e1) {
				// ai n�o da para fazer nada
				e1.printStackTrace();
			}
		} finally {
			JdbcUtil.close(conn, pstmt);
		}
	}
}
