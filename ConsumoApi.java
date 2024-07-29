import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;

public class ConsumoApi {
    private static final String baseUrl = "http://api.olhovivo.sptrans.com.br/v2.1";
    private static final String token = "266ec9459cc9bfd91e246ccc29e2223d6aac5da12cff6e6e16cfd85ceaa25df6";
    private static Optional<String> sessionCookie = Optional.empty();

    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();

        // Autenticação
        boolean isAuthenticated = authenticate(client, token);
        if (isAuthenticated) {
            System.out.println("Autenticação bem-sucedida!");

            // Busca pelas linhas
            String termosBusca = "8000";
            buscarLinhas(client, termosBusca);

            // Busca pelas paradas
            String termosBuscaParadas = "Afonso";
            buscarParadas(client, termosBuscaParadas);

            // Previsão de chegada
            int codigoParada = 340015329;  // Exemplo de código de parada
            int codigoLinha = 8000;  // Exemplo de código de linha
            buscarPrevisaoChegada(client, codigoParada, codigoLinha);

        } else {
            System.out.println("Falha na autenticação!");
        }
    }

    private static boolean authenticate(HttpClient client, String token) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/Login/Autenticar?token=" + token))
                    .POST(BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if (Boolean.parseBoolean(response.body())) {
                HttpHeaders headers = response.headers();
                sessionCookie = headers.firstValue("Set-Cookie");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void buscarLinhas(HttpClient client, String termosBusca) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/Linha/Buscar?termosBusca=" + termosBusca))
                    .GET();

            sessionCookie.ifPresent(cookie -> requestBuilder.header("Cookie", cookie));

            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Linhas encontradas:");
                System.out.println(response.body());
            } else {
                System.out.println("Erro ao buscar linhas: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void buscarParadas (HttpClient client, String termosBusca){
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/Parada/Buscar?termosBusca=" + termosBusca))
                    .GET();

            sessionCookie.ifPresent(cookie -> requestBuilder.header("Cookie", cookie));

            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Paradas encontradas:");
                System.out.println(response.body());
            } else {
                System.out.println("Erro ao buscar paradas: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void buscarPrevisaoChegada(HttpClient client, int codigoParada, int codigoLinha) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/Previsao?codigoParada=" + codigoParada + "&codigoLinha=" + codigoLinha))
                    .GET();

            sessionCookie.ifPresent(cookie -> requestBuilder.header("Cookie", cookie));

            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Previsão de chegada:");
                System.out.println(response.body());
            } else {
                System.out.println("Erro ao buscar previsão de chegada: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



