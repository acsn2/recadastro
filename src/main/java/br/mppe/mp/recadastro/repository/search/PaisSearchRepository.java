package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.Pais;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pais entity.
 */
public interface PaisSearchRepository extends ElasticsearchRepository<Pais, Long> {
}
