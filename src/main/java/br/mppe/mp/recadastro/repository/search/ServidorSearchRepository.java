package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.Servidor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Servidor entity.
 */
public interface ServidorSearchRepository extends ElasticsearchRepository<Servidor, Long> {
}
