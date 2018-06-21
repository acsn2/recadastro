package br.mppe.mp.recadastro.repository;

import br.mppe.mp.recadastro.domain.ParenteServidor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParenteServidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParenteServidorRepository extends JpaRepository<ParenteServidor, Long> {

}
