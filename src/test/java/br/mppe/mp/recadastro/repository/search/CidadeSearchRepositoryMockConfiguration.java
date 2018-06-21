package br.mppe.mp.recadastro.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of CidadeSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CidadeSearchRepositoryMockConfiguration {

    @MockBean
    private CidadeSearchRepository mockCidadeSearchRepository;

}
