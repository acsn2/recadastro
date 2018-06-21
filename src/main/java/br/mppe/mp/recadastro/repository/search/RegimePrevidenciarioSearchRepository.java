package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.RegimePrevidenciario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RegimePrevidenciario entity.
 */
public interface RegimePrevidenciarioSearchRepository extends ElasticsearchRepository<RegimePrevidenciario, Long> {
}
