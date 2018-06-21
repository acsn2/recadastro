package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.EstadoCivil;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EstadoCivil entity.
 */
public interface EstadoCivilSearchRepository extends ElasticsearchRepository<EstadoCivil, Long> {
}
