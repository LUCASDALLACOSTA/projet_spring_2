package fr.projet.declarationfrais.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.webflow.execution.RequestContext;

import fr.projet.declarationfrais.model.Declaration;
import fr.projet.declarationfrais.services.DeclarationService;

@Controller
public class DeclarationController {

    private final DeclarationService declarationService;

    @Autowired
    public DeclarationController(DeclarationService declarationService) {
        this.declarationService = declarationService;
    }

    @PostMapping("/recapitulatif")
    public String saveDeclaration(RequestContext requestContext) {
        Declaration declaration = (Declaration) requestContext.getFlowScope().get("declaration");

        declarationService.sauvegarderDeclaration(declaration);

        return "redirect:/confirmation";
    }

    @GetMapping("/ListeDeclaration")
    public String listeDeclaration(Model model) {
        List<Declaration> listOfDeclarations = declarationService.getAllDeclarations();

        model.addAttribute("listOfDeclarations", listOfDeclarations);

        return "ListeDeclaration";
    }
}
