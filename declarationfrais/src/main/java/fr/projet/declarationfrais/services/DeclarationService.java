package fr.projet.declarationfrais.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.projet.declarationfrais.config.RandomRefGenerator;
import fr.projet.declarationfrais.model.Declaration;
import fr.projet.declarationfrais.repository.DeclarationRepository;

@Service
public class DeclarationService {

    @Autowired
    private DeclarationRepository declarationRepository;

    @Autowired
    private RandomRefGenerator randomRefGenerator;

    public Declaration createNewDeclaration(Declaration declaration) {
        String randomRef = randomRefGenerator.generateRef();
        declaration.setRefDossier(randomRef);
        
        return declarationRepository.save(declaration);
    }
}
