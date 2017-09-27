package business;

import java.util.List;

import bean.Campanha;
import bean.Cliente;
import dao.ClienteDAO;

public class ClienteBusiness {
	
	private static final String erroEmailCadastrado = "E-mail já existe";

	public Cliente getClienteByEmail(String email) {
		//Variavel de retorno
		Cliente cliente;
		
		//Pegar cliente pelo email
		ClienteDAO clienteDAO = new ClienteDAO();
		cliente = clienteDAO.getClienteByEmail(email);
		
		//Retornar cliente
		return cliente;
	}

	public String cadastrarCliente(Cliente cliente) {
		//Variavel de retorno
		String resultado;
		
		//Verifica se email já está cadastrado
		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente clienteCadastrado = clienteDAO.getClienteByEmail(cliente.getEmail());
		
		//Caso e-mail não exista, cadastrar cliente
		if (clienteCadastrado == null) {
			resultado = clienteDAO.cadastrarCliente(cliente);			
		} else {
			resultado = erroEmailCadastrado;
		}
		
		//Retornar resultado
		return resultado;
	}

	public static String getErroEmailCadastrado() {
		return erroEmailCadastrado;
	}

	public void associarCampanhas(Integer idCliente, List<Campanha> campanhas) {
		//Associar campanhas disponiveis ao cliente
		ClienteDAO clienteDAO = new ClienteDAO();
		for (int i = 0; i < campanhas.size(); i++) {
			clienteDAO.associarCampanha(idCliente, campanhas.get(i));
		}		
	}	

}
