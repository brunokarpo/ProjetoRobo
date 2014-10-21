package br.nom.brunokarpo.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufg.pw.utilitarios.JdbcUtil;



public class JDBCPersistence {

	private Connection conn; // Conex�o com o Banco de Dados
	private PreparedStatement pstmt; // respons�vel pelas strings de consulta

	private static final String propertiesFile = "./src/main/resources/configs/bd-config.properties"; // arquivo de configura��o
	private static final Logger LOGGER = Logger.getLogger(JDBCPersistence.class);

	public void salvar(String chave_acesso) {
		// implementa a salvura da chave de acesso no banco de dados

		try {
			conn = JdbcUtil.createConnection( propertiesFile ); // abre uma conex�o com o Banco
												// de Dados

			conn.setAutoCommit(false); // Cria uma transa��o no Banco de Dados

			pstmt = conn
					.prepareStatement("insert into chaves_acesso values (?)");

			pstmt.setString(1, chave_acesso);
			
			LOGGER.info( "Query executada\n " + pstmt.toString() );

			pstmt.executeUpdate();

			conn.commit();

			LOGGER.info("Chave de acesso " + chave_acesso + " inserida com sucesso.");

		} catch (Exception e) {
			try {
				conn.rollback();
				LOGGER.log(Level.ALL , "Problema ao inserir a chave de acesso", e);
				
			} catch (Exception e1) {
				LOGGER.log(Level.SEVERE, "Problema geral!", e);
			}
		} finally {
			JdbcUtil.close(conn, pstmt);
		}
	}
}
