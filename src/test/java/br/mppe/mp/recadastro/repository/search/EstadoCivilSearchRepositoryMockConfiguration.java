package br.mppe.mp.recadastro.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of EstadoCivilSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class EstadoCivilSearchRepositoryMockConfiguration {

    @MockBean
    private EstadoCivilSearchRepository mockEstadoCivilSearchRepository;

}
