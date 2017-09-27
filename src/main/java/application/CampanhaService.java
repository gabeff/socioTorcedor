package application;

import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;

import bean.Campanha;

public class CampanhaService {

	public List<Campanha> getCampanhasDisponiveis(Integer idCliente) throws Exception {
		List<Campanha> campanhas = null;
		try {
			campanhas = Client.create().resource("http://localhost:8080/Campanha/getCampanhasDisponiveis/"+idCliente)
					.get(new GenericType<List<Campanha>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Campanha is down! or no campaign...");
		}
		return campanhas;
	}
}
