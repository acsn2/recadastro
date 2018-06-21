package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.Orgao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Orgao entity.
 */
public interface OrgaoSearchRepository extends ElasticsearchRepository<Orgao, Long> {
}
