package fr.projet.declarationfrais.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.projet.declarationfrais.model.Declaration;
import fr.projet.declarationfrais.repository.DeclarationRepository;

@Service
public class DeclarationService {

    private final DeclarationRepository declarationRepository;

    @Autowired
    public DeclarationService(DeclarationRepository declarationRepository) {
        this.declarationRepository = declarationRepository;
    }

    public void sauvegarderDeclaration(Declaration declaration) {
        declarationRepository.save(declaration);
    }

    public List<Declaration> getAllDeclarations() {
        return declarationRepository.findAll();
    }
}
