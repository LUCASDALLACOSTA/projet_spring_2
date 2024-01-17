package fr.projet.declarationfrais.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.webflow.execution.RequestContext;

import fr.projet.declarationfrais.model.Declaration;
import fr.projet.declarationfrais.repository.DeclarationRepository;

@Service
public class DeclarationService {

    private final DeclarationRepository declarationRepository;

    @Autowired
    public DeclarationService(DeclarationRepository declarationRepository) {
        this.declarationRepository = declarationRepository;
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public Declaration sauvegarderDeclaration(RequestContext RequestContext) {
        String refDossier = (String) RequestContext.getFlowScope().get("refDossier");
        Date date = (Date) RequestContext.getFlowScope().get("date");
        String lieu = (String) RequestContext.getFlowScope().get("lieu");
        String intitule = (String) RequestContext.getFlowScope().get("intitule");
        String type_transport = (String) RequestContext.getFlowScope().get("type_transport");
        String lieu_depart = (String) RequestContext.getFlowScope().get("lieu_depart");
        String facture_transport = (String) RequestContext.getFlowScope().get("facture_transport");
        String montant_transport = (String) RequestContext.getFlowScope().get("montant_transport");
        String type_hebergement = (String) RequestContext.getFlowScope().get("type_hebergement");
        String facture_hebergement = (String) RequestContext.getFlowScope().get("facture_hebergement");
        String montant_hebergement = (String) RequestContext.getFlowScope().get("montant_hebergement");
        String coordonneesbancaires = (String) RequestContext.getFlowScope().get("coordonneesbancaires");

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(connectedUserEmail);

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
