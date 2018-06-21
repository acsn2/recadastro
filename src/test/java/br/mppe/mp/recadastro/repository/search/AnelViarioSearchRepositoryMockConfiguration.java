package br.mppe.mp.recadastro.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of AnelViarioSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AnelViarioSearchRepositoryMockConfiguration {

    @MockBean
    private AnelViarioSearchRepository mockAnelViarioSearchRepository;

}
