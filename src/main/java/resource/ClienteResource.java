package resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import application.CampanhaService;
import bean.Campanha;
import bean.Cliente;
import business.ClienteBusiness;

@Path("/")
public class ClienteResource {

	private ClienteBusiness clienteBusiness;

	@POST
	@Path("/cadastrarCliente")
	@Consumes("application/json")
	@Produces("application/json")
	public Response cadastrarCliente(Cliente cliente) {
		// verifica se e-mail já foi cadastrado
		clienteBusiness = new ClienteBusiness();
		String retornoCadastroCliente = clienteBusiness.cadastrarCliente(cliente);

		// manipulador JSON
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		// se e-mail já está cadastrado, verifica se ha campanhas disponiveis
		// para associacao
		CampanhaService campanhaService = new CampanhaService();
		List<Campanha> campanhas = null;
		if (retornoCadastroCliente == ClienteBusiness.getErroEmailCadastrado()) {
			cliente = clienteBusiness.getClienteByEmail(cliente.getEmail());
			try {
				campanhas = campanhaService.getCampanhasDisponiveis(cliente.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			// se nao houver campanhas disponiveis, informar que o email ja esta
			// cadastrado
			if (campanhas == null || campanhas.isEmpty()) {
				return Response.ok(gson.toJson("E-mail já cadastrado")).build();
				// se nao, informar campanhas disponiveis
			} else {
				return Response.ok(gson.toJson(campanhas)).build();
			}
			// caso o cadastro tenha sido efetuado, informar que o mesmo foi
			// realizado com sucesso
		} else if (retornoCadastroCliente == "cadastrado") {
			return Response.ok(gson.toJson("Cadastro Realizado com Sucesso!")).build();
		}

		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@POST
	@Path("/associarCampanhas/{idCliente}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response associarCampanhas(@PathParam("idCliente") final Integer idCliente, List<Campanha> campanhas) {

		clienteBusiness = new ClienteBusiness();

		// manipulador JSON
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		// verifica se ha campanhas disponiveis para associacao
		CampanhaService campanhaService = new CampanhaService();
		List<Campanha> campanhasDisponiveis = null;
		try {
			campanhasDisponiveis = campanhaService.getCampanhasDisponiveis(idCliente);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// verifica se existem campanhas para associar, caso exista, associar
		// campanhas
		if (campanhasDisponiveis.isEmpty() || campanhas.isEmpty()) {
			Response.ok(gson.toJson("Não existem campanhas disponíveis para associar")).build();
		} else {
			clienteBusiness.associarCampanhas(idCliente, campanhas);
			Response.ok(gson.toJson("Campanhas associadas com sucesso!")).build();
		}

		return Response.status(Response.Status.NO_CONTENT).build();
	}

}
