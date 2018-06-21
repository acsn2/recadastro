package br.mppe.mp.recadastro.repository;

import br.mppe.mp.recadastro.domain.CategoriaESocial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CategoriaESocial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaESocialRepository extends JpaRepository<CategoriaESocial, Long> {

}
