package br.mppe.mp.recadastro.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ParenteServidorSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ParenteServidorSearchRepositoryMockConfiguration {

    @MockBean
    private ParenteServidorSearchRepository mockParenteServidorSearchRepository;

}
