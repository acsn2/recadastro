package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.RegimeTrabalho;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the RegimeTrabalho entity.
 */
public interface RegimeTrabalhoSearchRepository extends ElasticsearchRepository<RegimeTrabalho, Long> {
}
