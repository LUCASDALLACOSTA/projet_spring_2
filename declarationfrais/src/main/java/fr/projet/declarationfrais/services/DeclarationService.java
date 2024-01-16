package fr.projet.declarationfrais.services;

import java.util.ArrayList;
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

    public Declaration getDeclarationById(Long id) {
        return declarationRepository.findById(id).orElse(null);
    }

    public List<Declaration> getDeclarationsByStatut(String statut) {
        List<Declaration> allDeclarations = declarationRepository.findAll();

        List<Declaration> declarationsByStatut = new ArrayList<>();
        for (Declaration declaration : allDeclarations) {
            if (declaration.getStatut().equalsIgnoreCase(statut)) {
                declarationsByStatut.add(declaration);
            }
        }

        return declarationsByStatut;
    }
}
