package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.AnelViario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AnelViario entity.
 */
public interface AnelViarioSearchRepository extends ElasticsearchRepository<AnelViario, Long> {
}
