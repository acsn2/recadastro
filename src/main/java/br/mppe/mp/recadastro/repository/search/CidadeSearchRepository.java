package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.Cidade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Cidade entity.
 */
public interface CidadeSearchRepository extends ElasticsearchRepository<Cidade, Long> {
}
