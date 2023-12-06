package fr.projet.declarationfrais.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

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
    private Date date;

    @Column(nullable = false)
    private String lieu;

    @Column(nullable = false)
    private String intitule;

    @Column(nullable = false)
    private String hebergement;

    @Column(nullable = false)
    private String iban;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
