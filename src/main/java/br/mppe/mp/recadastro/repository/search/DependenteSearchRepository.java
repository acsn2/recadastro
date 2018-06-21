package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.Dependente;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Dependente entity.
 */
public interface DependenteSearchRepository extends ElasticsearchRepository<Dependente, Long> {
}
