package br.mppe.mp.recadastro.repository;

import br.mppe.mp.recadastro.domain.Escolaridade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Escolaridade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EscolaridadeRepository extends JpaRepository<Escolaridade, Long> {

}
