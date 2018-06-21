package br.mppe.mp.recadastro.repository;

import br.mppe.mp.recadastro.domain.AnelViario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnelViario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnelViarioRepository extends JpaRepository<AnelViario, Long> {

}
