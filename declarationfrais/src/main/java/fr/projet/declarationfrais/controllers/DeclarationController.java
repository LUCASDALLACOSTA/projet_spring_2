package fr.projet.declarationfrais.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.webflow.execution.RequestContext;

import fr.projet.declarationfrais.model.Declaration;
import fr.projet.declarationfrais.services.DeclarationService;

@RestController
public class DeclarationController {

    @Autowired
    private DeclarationService declarationService;

    @PostMapping("/submitDetails")
    public String submitDetails(@RequestParam("intitule") String intitule,
            @RequestParam("date") Date date,
            @RequestParam("lieu") String lieu,
            HttpServletRequest request,
            RequestContext context) {
        Declaration declaration = (Declaration) context.getFlowScope().get("declaration");
        if (declaration != null) {
            declaration.setIntitule(intitule);
            declaration.setDate(date);
            declaration.setLieu(lieu);
            context.getFlowScope().put("declaration", declaration);

            String executionKey = request.getParameter("execution");
            if (executionKey != null) {
                return "redirect:/declaration?execution=" + executionKey;
            } else {
                return "Erreur lors de la soumission des détails";
            }
        } else {
            return "Erreur lors de la soumission des détails";
        }
    }
}
