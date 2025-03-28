package Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import Model.cepModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CepAPI {

    // Método para fazer requisição GET usando HttpClient
    public static cepModel getDataFromURL(String ceep) {
    	String url = "https://viacep.com.br/ws/"+ceep+"/json/";
        cepModel cep=null;
        try {
            // Cria o HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Cria a requisição HTTP GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Envia a requisição e recebe a resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica o status da resposta
            if (response.statusCode() == 200) {
                // Captura o corpo da resposta
                //responseBody = response.body();
                ObjectMapper objectMapper = new ObjectMapper();
                cep = objectMapper.readValue(response.body(), cepModel.class);
            } else {
            	cep=null;
            }

        } catch (Exception e) {
            cep=null;
        }

        return cep;
    }
}