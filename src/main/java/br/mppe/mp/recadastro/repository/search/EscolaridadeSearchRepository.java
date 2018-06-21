package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.Escolaridade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Escolaridade entity.
 */
public interface EscolaridadeSearchRepository extends ElasticsearchRepository<Escolaridade, Long> {
}
