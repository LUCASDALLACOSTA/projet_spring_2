package fr.projet.declarationfrais.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "restauration")
public class Restauration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String montant_resto;

    @Column(name = "repas", nullable = false)
    private String repas;

    @Column(name = "nom_fichier_resto", nullable = false)
    private String nom_fichier_resto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "declaration_id")
    private Declaration declaration;
}
