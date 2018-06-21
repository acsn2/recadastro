package br.mppe.mp.recadastro.repository.search;

import br.mppe.mp.recadastro.domain.CategoriaESocial;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CategoriaESocial entity.
 */
public interface CategoriaESocialSearchRepository extends ElasticsearchRepository<CategoriaESocial, Long> {
}
