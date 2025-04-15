package br.com.seuapp.service;

import br.com.seuapp.client.ProdutoApiClient;
import br.com.seuapp.dto.DadosProduto;
import br.com.seuapp.model.PautaUpdate;
import br.com.seuapp.repository.PautaUpdateRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PautaService {

    private final PautaUpdateRepository repository;
    private final ProdutoApiClient apiClient;
    private final Logger logger = LoggerFactory.getLogger(PautaService.class);

    public PautaService(PautaUpdateRepository repository, ProdutoApiClient apiClient) {
        this.repository = repository;
        this.apiClient = apiClient;
    }

    // Método que pode ser chamado manualmente pelo controller
    @Transactional
    public void atualizarProdutos() {
        List<PautaUpdate> produtos = repository.findTop10ByGtinIsNullOrderByIdAsc();

        if (produtos.isEmpty()) {
            logger.info("Nenhum produto pendente encontrado.");
            return;  // Evita loop infinito
        }

        for (PautaUpdate produto : produtos) {
            try {
                logger.info("Buscando produto: {}", produto.getDescricao());

                DadosProduto dados = apiClient.buscarProdutoPorDescricao(produto.getDescricao());

                // Verificando se os dados do produto não são nulos
                if (dados != null) {
                    // Atualiza apenas se houver diferença no GTIN, NCM ou CEST
                    boolean updated = false;

                    // Verificando se o GTIN foi retornado e atribuindo ao GTIN
                    if (dados.getGtin() != null && (produto.getGtin() == null || !produto.getGtin().equals(dados.getGtin()))) {
                        produto.setGtin(dados.getGtin().toString()); // Convertendo o GTIN para String e atribuindo
                        updated = true;
                    }

                    // Verificando se o NCM não é null e se há diferença
                    if (produto.getNcm() == null || !produto.getNcm().equals(dados.getNcm())) {
                        produto.setNcm(dados.getNcm());
                        updated = true;
                    }

                    // Verificando se o CEST não é null e se há diferença
                    if (produto.getCest() == null || !produto.getCest().equals(dados.getCest())) {
                        produto.setCest(dados.getCest());
                        updated = true;
                    }

                    if (updated) {
                        repository.save(produto);
                        logger.info("Produto atualizado: ID {} | GTIN: {} | NCM: {} | CEST: {}", produto.getId(), dados.getGtin(), dados.getNcm(), dados.getCest());
                    } else {
                        logger.info("Nenhuma atualização necessária para o produto ID {}", produto.getId());
                    }

                } else {
                    logger.warn("Produto não encontrado: {}", produto.getDescricao());
                }

            } catch (Exception e) {
                logger.error("Erro ao atualizar produto ID {}: {}", produto.getId(), e.getMessage());
            }

            try {
                Thread.sleep(20000); // espera 20 segundos antes de buscar o próximo
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                logger.warn("Processo interrompido.");
                return;
            }
        }
    }


    // Atualização automática a cada 5 minutos
    @Scheduled(fixedDelay = 5 * 60 * 1000) // 5 minutos
    public void atualizarProdutosAutomaticamente() {
        logger.info("Iniciando atualização automática de produtos.");
        atualizarProdutos();
    }
}
