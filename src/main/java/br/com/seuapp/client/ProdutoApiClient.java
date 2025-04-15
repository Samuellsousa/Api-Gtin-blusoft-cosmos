package br.com.seuapp.client;

import br.com.seuapp.dto.DadosProduto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProdutoApiClient {

    private static final String TOKEN = "rkZD6ObNSTCu3EZG_i4wvw";
    private static final String USER_AGENT = "Cosmos-API-Request";
    private static final String BASE_URL = "https://api.cosmos.bluesoft.com.br/products?query=";
    private static final Logger logger = LoggerFactory.getLogger(ProdutoApiClient.class);

    public DadosProduto buscarProdutoPorDescricao(String descricao) {
        logger.info("Iniciando a busca do produto com descrição: {}", descricao);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Cosmos-Token", TOKEN);
        headers.set("User-Agent", USER_AGENT);
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = BASE_URL + descricao;

        try {
            logger.info("Fazendo a requisição para a API: {}", url);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            logger.info("Status da resposta da API: {}", response.getStatusCode());

            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                logger.info("Resposta completa recebida da API: {}", responseBody);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseBody);
                logger.info("Estrutura JSON da resposta: {}", rootNode.toString());

                if (rootNode.has("products") && rootNode.get("products").isArray()) {
                    JsonNode productsNode = rootNode.get("products");

                    if (productsNode.size() > 0) {
                        JsonNode productNode = productsNode.get(0);

                        // Alteração: 'ean' foi substituído por 'gtin'
                        String gtin = productNode.has("gtin") ? productNode.get("gtin").asText() : null;
                        String cest = productNode.has("cest") ? productNode.get("cest").asText() : null;
                        String ncm = productNode.has("ncm") ? productNode.get("ncm").asText() : null;

                        // Verifica se pelo menos um dos campos foi preenchido
                        if (gtin != null || cest != null || ncm != null) {
                            DadosProduto produto = new DadosProduto(gtin, cest, ncm);
                            logger.info("Produto encontrado (parcial ou completo): {}", produto);
                            return produto;
                        } else {
                            logger.warn("Produto encontrado, mas nenhum dos campos gtin, cest ou ncm está presente.");
                        }
                    } else {
                        logger.warn("Nenhum produto encontrado na resposta da API para a descrição: {}", descricao);
                    }
                } else {
                    logger.warn("O campo 'products' está ausente ou não é uma lista.");
                }
            } else {
                logger.warn("Resposta da API não foi bem-sucedida para o produto: {}", descricao);
            }
        } catch (Exception e) {
            logger.error("Erro ao buscar produto: {}", descricao, e);
        }

        logger.warn("Produto não encontrado: {}", descricao);
        return null;
    }
}
