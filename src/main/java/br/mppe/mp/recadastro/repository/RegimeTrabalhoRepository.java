package br.mppe.mp.recadastro.repository;

import br.mppe.mp.recadastro.domain.RegimeTrabalho;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegimeTrabalho entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegimeTrabalhoRepository extends JpaRepository<RegimeTrabalho, Long> {

}
