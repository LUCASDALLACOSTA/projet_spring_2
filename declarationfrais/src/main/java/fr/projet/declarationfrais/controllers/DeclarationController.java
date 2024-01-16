package fr.projet.declarationfrais.controllers;

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
        return "listeDeclarations";
    }

    public float calculerMontantTotal(Declaration declaration) {
        float montantTotal = 0;

        if (declaration.getMontant_transport() != null) {
            montantTotal += declaration.getMontant_transport();
        }

        if (declaration.getMontant_hebergement() != null) {
            montantTotal += declaration.getMontant_hebergement();
        }

        if (declaration.getRestaurationList() != null) {
            for (Restauration restauration : declaration.getRestaurationList()) {
                if (restauration.getMontant_resto() != null) {
                    montantTotal += restauration.getMontant_resto();
                }
            }
        }

        return montantTotal;
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

    public Declaration sauvegarderDeclaration(RequestContext RequestContext) {
        String refDossier = (String) RequestContext.getFlowScope().get("refDossier");
        String date = (String) RequestContext.getFlowScope().get("date");
        String lieu = (String) RequestContext.getFlowScope().get("lieu");
        String intitule = (String) RequestContext.getFlowScope().get("intitule");
        String type_transport = (String) RequestContext.getFlowScope().get("type_transport");
        String lieu_depart = (String) RequestContext.getFlowScope().get("lieu_depart");
        String facture_transport = (String) RequestContext.getFlowScope().get("facture_transport");
        String montant_transport = (String) RequestContext.getFlowScope().get("montant_transport");
        String type_hebergement = (String) RequestContext.getFlowScope().get("type_hebergement");
        String facture_hebergement = (String) RequestContext.getFlowScope().get("facture_hebergement");
        String montant_hebergement = (String) RequestContext.getFlowScope().get("montant_hebergement");
        String fraisRestoInfos = (String) RequestContext.getFlowScope().get("fraisRestoInfos");
        String coordonneesbancaires = (String) RequestContext.getFlowScope().get("coordonneesbancaires");

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String connectedUserEmail = userDetails.getUsername();
        String connectedUserId = connectedUserEmail;

        Declaration declaration = new Declaration();
        declaration.setUser(connectedUserId);
        declaration.setStatut("en attente");
        declaration.setRefDossier(refDossier);
        declaration.setDate(date);
        declaration.setLieu(lieu);
        declaration.setIntitule(intitule);
        declaration.setTypeTransport(type_transport);
        declaration.setLieuDepart(lieu_depart);
        declaration.setNom_fichier_transport(facture_transport);
        declaration.setMontant_transport(montant_transport);
        declaration.setType_hebergement(type_hebergement);
        declaration.setNom_fichier_hebergement(facture_hebergement);
        declaration.setMontant_hebergement(montant_hebergement);
        declaration.setCoordonneesbancaires(coordonneesbancaires);

        return DeclarationRepository.save(declaration);
    }
}
