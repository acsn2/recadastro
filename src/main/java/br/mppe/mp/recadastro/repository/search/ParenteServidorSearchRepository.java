package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.ParenteServidor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParenteServidor entity.
 */
public interface ParenteServidorSearchRepository extends ElasticsearchRepository<ParenteServidor, Long> {
}
