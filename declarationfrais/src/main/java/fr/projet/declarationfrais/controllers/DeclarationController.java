package fr.projet.declarationfrais.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.declarationfrais.model.Declaration;
import fr.projet.declarationfrais.services.DeclarationService;

@RestController
public class DeclarationController {

    @Autowired
    private DeclarationService declarationService;

    @PostMapping("/MenuApplication")
    public ResponseEntity<String> createDeclaration() {
        Declaration newDeclaration = new Declaration(); // Vous pouvez initialiser la déclaration comme vous le souhaitez
        Declaration savedDeclaration = declarationService.createNewDeclaration(newDeclaration);
        if (savedDeclaration != null) {
            return ResponseEntity.ok("Déclaration créée avec succès !");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de la déclaration");
        }
    }
}
