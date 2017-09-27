package dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.Campanha;
import bean.Cliente;

public class ClienteDAO extends Utils {

	public Cliente getClienteByEmail(String email) {
		// Variável de retorno
		Cliente cliente = null;

		// Conexão com o banco
		Connection conn = null;

		try {
			// Pegar conexão
			conn = getConnection();

			// Query para consultar o cliente pelo e-mail
			String sql = "select * from teste.cliente where email = '" + email + "';";

			// Criar statement e resultset para manipulacao dos dados retornados
			// pela query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			// Atribuir cliente com resultado da query
			while (rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setEmail(rs.getString("email"));
				cliente.setNome(rs.getString("nome"));
				cliente.setDataNascimento(rs.getDate("data_nasc"));
				cliente.setIdTime(rs.getInt("id_time"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				// Fechar Conexão
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Retornar cliente do e-mail
		return cliente;
	}

	public String cadastrarCliente(Cliente cliente) {
		// variavel de retorno
		String retorno = null;

		// conexao com o banco
		Connection conn = null;

		// realizar cadastro no banco
		try {
			// pegar conexao
			conn = getConnection();

			// script insert sql
			String sql = "INSERT INTO teste.cliente(email, nome, data_nasc, id_time) " + "VALUES (?, ?, ?, ?);";
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);

			// atribuir variaveis
			pstmt.setString(1, cliente.getEmail());
			pstmt.setString(2, cliente.getNome());
			pstmt.setDate(3, cliente.getDataNascimento());
			pstmt.setInt(4, cliente.getIdTime());

			// executa insert sql
			pstmt.execute();
			pstmt.close();

			// atribui cadastrado a variavel de retorno em caso de sucesso
			retorno = "cadastrado";

		} catch (IOException e) {
			retorno = "Desculpe, houve algum problema no cadastro, favor tentar novamente.";
			e.printStackTrace();
		} catch (SQLException e) {
			retorno = "Desculpe, houve algum problema no cadastro, favor tentar novamente.";
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			retorno = "Desculpe, houve algum problema no cadastro, favor tentar novamente.";
			e.printStackTrace();
		} finally {
			try {
				// fechar conexao
				conn.close();
			} catch (SQLException e) {
				retorno = "Desculpe, houve algum problema no cadastro, favor tentar novamente.";
				e.printStackTrace();
			}
		}

		// retorno da funcao
		return retorno;
	}

	public void associarCampanha(Integer idCliente, Campanha campanha) {
		// conexao com o banco
		Connection conn = null;

		// realizar cadastro no banco
		try {
			// pegar conexao
			conn = getConnection();

			// script insert sql
			String sql = "INSERT INTO teste.cliente_campanhas(id_cliente, id_campanha) "
					+ "VALUES (?, ?);";
			PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);

			// atribuir variaveis
			pstmt.setInt(1, idCliente);
			pstmt.setInt(2, campanha.getId());

			// executa insert sql
			pstmt.execute();
			pstmt.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		} finally {
			try {
				// fechar conexao
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
