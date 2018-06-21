package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.RacaCor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RacaCor entity.
 */
public interface RacaCorSearchRepository extends ElasticsearchRepository<RacaCor, Long> {
}
