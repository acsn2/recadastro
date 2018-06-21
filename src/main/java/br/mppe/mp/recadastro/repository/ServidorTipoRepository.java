package br.mppe.mp.recadastro.repository;

import br.mppe.mp.recadastro.domain.ServidorTipo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServidorTipo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServidorTipoRepository extends JpaRepository<ServidorTipo, Long> {

}
