package testes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import application.CampanhaService;
import bean.Campanha;
import bean.Cliente;
import business.ClienteBusiness;
import junit.framework.Assert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteTest {

	@Test
	public void testA_deveCadastrarCliente() throws ParseException {

		// date format
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dt;

		// Criar cliente para cadastro
		Cliente cliente = new Cliente();
		cliente.setEmail("g4brielfernandes@gmail.com");
		cliente.setNome("Gabriel Ferreira Fernandes");
		String dataNascimento = "01/09/2017";
		dt = sdf.parse(dataNascimento);
		cliente.setDataNascimento(new java.sql.Date(dt.getTime()));
		cliente.setIdTime(19);

		// Verificar se e-mail já esta cadastrado
		ClienteBusiness clienteBusiness = new ClienteBusiness();
		String resultadoObtido = clienteBusiness.cadastrarCliente(cliente);

		// Se cliente for retornado, o e-mail já estava cadastrado
		String resultadoEsperado = "cadastrado";
		Assert.assertEquals(resultadoEsperado, resultadoObtido);
	}

	@Test
	public void testB_deveVerificarSeEmailJaEstaCadastrado() {

		// Atribuir variaveis de teste
		String email = "g4brielfernandes@gmail.com";

		// Verificar se e-mail já esta cadastrado
		Cliente cliente;
		ClienteBusiness clienteBusiness = new ClienteBusiness();
		cliente = clienteBusiness.getClienteByEmail(email);

		// Se cliente for retornado, o e-mail já estava cadastrado
		Assert.assertNotNull(cliente);
	}

	@Test
	public void testC_seEmailJaCadastradoRetornaCampanhasDisponiveis() throws ParseException {

		// Atribuir variaveis de teste
		List<Campanha> campanhas = null;

		// date format
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dt;

		// Criar cliente para cadastro
		Cliente cliente = new Cliente();
		cliente.setEmail("g4brielfernandes@gmail.com");
		cliente.setNome("Gabriel Ferreira Fernandes");
		String dataNascimento = "01/09/2017";
		dt = sdf.parse(dataNascimento);
		cliente.setDataNascimento(new java.sql.Date(dt.getTime()));
		cliente.setIdTime(19);

		// Verificar se e-mail já esta cadastrado
		ClienteBusiness clienteBusiness = new ClienteBusiness();
		String resultadoObtido = clienteBusiness.cadastrarCliente(cliente);
		
		//Se ja estiver cadastrado, popular campanhas disponiveis para o cliente
		CampanhaService campanhaService = new CampanhaService();
		if (resultadoObtido == ClienteBusiness.getErroEmailCadastrado()) {
			try {
				campanhas = campanhaService.getCampanhasDisponiveis(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			campanhas = null;
		}

		// Se lista campanhas nao for nula, cliente ja estava cadastrado e as campanhas disponiveis foram retornadas com sucesso
		Assert.assertNotNull(campanhas);

	}

}
