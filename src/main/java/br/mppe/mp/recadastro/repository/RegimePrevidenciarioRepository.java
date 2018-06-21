package br.mppe.mp.recadastro.repository;

import br.mppe.mp.recadastro.domain.RegimePrevidenciario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RegimePrevidenciario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegimePrevidenciarioRepository extends JpaRepository<RegimePrevidenciario, Long> {

}
