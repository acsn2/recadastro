package br.mppe.mp.recadastro.repository;

import br.mppe.mp.recadastro.domain.EstadoCivil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EstadoCivil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoCivilRepository extends JpaRepository<EstadoCivil, Long> {

}
