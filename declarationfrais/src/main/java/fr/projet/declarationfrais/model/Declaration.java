package fr.projet.declarationfrais.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "declaration")
public class Declaration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String refDossier;

    @Column(name = "type_transport", nullable = false)
    private String typeTransport;

    @Column(name = "lieu_depart", nullable = false)
    private String lieuDepart;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String lieu;

    @Column(nullable = false)
    private String intitule;

    @Column(nullable = false)
    private String type_hebergement;

    @Column(nullable = false)
    private String montant_hebergement;

    @Column(nullable = false)
    private String montant_transport;

    @Column(nullable = false)
    private String coordonneesbancaires;

    @Column(nullable = false)
    private String statut;

    @Column(name = "nom_fichier_transport", nullable = false)
    private String nom_fichier_transport;

    @Column(name = "nom_fichier_hebergement", nullable = false)
    private String nom_fichier_hebergement;


    @Column(name = "user", nullable = false)
    private String user;

    @OneToMany(mappedBy = "declaration", cascade = CascadeType.ALL)
    private List<Restauration> restaurationList;
}
