package fr.projet.declarationfrais.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.webflow.execution.RequestContext;

import fr.projet.declarationfrais.model.Declaration;
import fr.projet.declarationfrais.model.Restauration;
import fr.projet.declarationfrais.repository.DeclarationRepository;
import fr.projet.declarationfrais.services.DeclarationService;

@Controller
public class DeclarationController {

    @Autowired
    private DeclarationRepository DeclarationRepository;

    private final DeclarationService declarationService;

    @Autowired
    public DeclarationController(DeclarationService declarationService) {
        this.declarationService = declarationService;
    }

    @GetMapping("/ListeDeclarations")
    public String getListeDeclarations(@RequestParam(value = "statut", required = false) String statut, Model model) {
        List<Declaration> declarations;

        if (statut != null && !statut.isEmpty()) {
            declarations = declarationService.getDeclarationsByStatut(statut);
        } else {
            declarations = declarationService.getAllDeclarations();
        }

        model.addAttribute("listeDeclarations", declarations);
        return "ListeDeclarations";
    }


    @PostMapping("/updateStatut/{id}")
    public String updateStatut(@PathVariable Long id, @RequestParam("statut") String statut) {
        Declaration declaration = declarationService.getDeclarationById(id);
        declaration.setStatut(statut);
        declarationService.sauvegarderDeclaration(declaration);

        String redirectURL = "/ListeDeclarations";

        if (statut.equalsIgnoreCase("Valide")) {
            redirectURL += "?statut=Valide";
        } else if (statut.equalsIgnoreCase("Invalide")) {
            redirectURL += "?statut=Invalide";
        } else if (statut.equalsIgnoreCase("En attente")) {
            redirectURL += "?statut=En%20attente";
        }

        return "redirect:" + redirectURL;
    }

    
}
