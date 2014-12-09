package br.nom.brunokarpo.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.oobj.util.Arquivo;
import br.ufg.pw.utilitarios.JdbcUtil;

public class JDBCPersistence {

	private Connection conn; // Conex�o com o Banco de Dados
	private PreparedStatement pstmt; // respons�vel pelas strings de consulta

	private static final String PROPERTIESFILE = "/bd-config.properties"; // arquivo de configura��o
	private static final Logger LOGGER = LogManager.getLogger(JDBCPersistence.class);

	public void salvar(String chave_acesso) {
		// implementa a salvura da chave de acesso no banco de dados
		String url = Arquivo.getURLRelativaClasspath(PROPERTIESFILE).toString().replace("file:/", "");

		try {
			conn = JdbcUtil.createConnection( url ); // abre uma conexao com o Banco de Dados

			conn.setAutoCommit(false); // Cria uma transacao no Banco de Dados

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
				LOGGER.log(Level.ERROR , "Problema ao inserir a chave de acesso", e);

			} catch (Exception e1) {
				LOGGER.log(Level.FATAL, "Problema geral!", e);
			}
		} finally {
			JdbcUtil.close(conn, pstmt);
		}
	}
}