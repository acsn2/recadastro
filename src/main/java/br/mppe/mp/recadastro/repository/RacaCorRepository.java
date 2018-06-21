package br.mppe.mp.recadastro.repository;

import br.mppe.mp.recadastro.domain.RacaCor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RacaCor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RacaCorRepository extends JpaRepository<RacaCor, Long> {

}
