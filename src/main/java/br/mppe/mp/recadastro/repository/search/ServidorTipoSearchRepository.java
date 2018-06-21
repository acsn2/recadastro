package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.ServidorTipo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ServidorTipo entity.
 */
public interface ServidorTipoSearchRepository extends ElasticsearchRepository<ServidorTipo, Long> {
}
