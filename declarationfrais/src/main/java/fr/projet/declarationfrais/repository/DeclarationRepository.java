package fr.projet.declarationfrais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.projet.declarationfrais.model.Declaration;

@Repository
public interface DeclarationRepository extends JpaRepository<Declaration, Long> {

}
