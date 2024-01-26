package fr.projet.declarationfrais.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.execution.RequestContext;
import fr.projet.declarationfrais.model.Restauration;
import fr.projet.declarationfrais.repository.RestaurationRepository;
import fr.projet.declarationfrais.repository.DeclarationRepository;

@Controller
public class RestaurationController {

    @Autowired
    private RestaurationRepository restaurationRepository;

    @Autowired
    private DeclarationRepository declarationRepository;

    public Restauration sauvegarderRestaurationPetitDejeuner(RequestContext flowRequestContext, Long declarationId) {
        String montant_petit_dejeuner = (String) flowRequestContext.getFlowScope().get("montant_petit_dejeuner");
        String facture_petit_dejeuner = (String) flowRequestContext.getFlowScope().get("facture_petit_dejeuner");

        if (montant_petit_dejeuner != null && !montant_petit_dejeuner.isEmpty()) {
            Restauration restauration = new Restauration();
            restauration.setMontant_resto(montant_petit_dejeuner);
            restauration.setRepas("Petit-dejeuner");
            restauration.setNom_fichier_resto(facture_petit_dejeuner);

            restauration.setDeclaration(declarationRepository.getById(declarationId));

            return restaurationRepository.save(restauration);
        }
        return null;
    }

    public Restauration sauvegarderRestaurationDejeuner(RequestContext flowRequestContext, Long declarationId) {
        String montant_dejeuner = (String) flowRequestContext.getFlowScope().get("montant_dejeuner");
        String facture_dejeuner = (String) flowRequestContext.getFlowScope().get("facture_dejeuner");
        if (montant_dejeuner != null && !montant_dejeuner.isEmpty()) {
            Restauration restauration = new Restauration();
            restauration.setMontant_resto(montant_dejeuner);
            restauration.setRepas("Déjeuner");
            restauration.setNom_fichier_resto(facture_dejeuner);

            restauration.setDeclaration(declarationRepository.getById(declarationId));

            return restaurationRepository.save(restauration);
        }
        return null;
    }

    public Restauration sauvegarderRestaurationDiner(RequestContext flowRequestContext, Long declarationId) {
        String montant_diner = (String) flowRequestContext.getFlowScope().get("montant_diner");
        String facture_diner = (String) flowRequestContext.getFlowScope().get("facture_diner");

        if (montant_diner != null && !montant_diner.isEmpty()) {
            Restauration restauration = new Restauration();
            restauration.setMontant_resto(montant_diner);
            restauration.setRepas("Diner");
            restauration.setNom_fichier_resto(facture_diner);

            restauration.setDeclaration(declarationRepository.getById(declarationId));

            return restaurationRepository.save(restauration);
        }

        return null;
    }

}
